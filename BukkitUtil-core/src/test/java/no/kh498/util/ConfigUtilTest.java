package no.kh498.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.powermock.api.mockito.PowerMockito;
import static org.powermock.api.mockito.PowerMockito.mock;
import org.slf4j.Logger;

/**
 * @author Elg
 */
public class ConfigUtilTest {

  private Plugin plugin;

  private static final String TEST_FILE_WITHOUT_DIR_PATH = "testfile.txt";
  private static final String TEST_FILE_WITH_DIR_PATH = "folde1/testfile.txt";


  private static final String NON_EXISTING_TEST_FILE_WITH_DIR_PATH = "folde2/testfile2.txt";

  @Rule
  public TemporaryFolder dataFolder = new TemporaryFolder();

  @BeforeClass
  public static void init() {
    FileUtils.logger = mock(Logger.class);
    ConfigUtil.logger = mock(Logger.class);
  }

  @Before
  public void setUp() {
    plugin = mock(Plugin.class);
    PowerMockito.when(plugin.getDataFolder()).thenReturn(dataFolder.getRoot());
  }


  //////////////////////////
  // saveDefaultResources //
  //////////////////////////


  @Test
  public void saveDefaultResourcesWithoutDir() throws IOException {
    ConfigUtil.saveDefaultResources(plugin, TEST_FILE_WITHOUT_DIR_PATH);
    File saved = FileUtils.getDatafolderFile(plugin, TEST_FILE_WITHOUT_DIR_PATH);
    assertTrue(saved.isFile());

    String content = org.apache.commons.io.FileUtils.readFileToString(saved);
    assertFalse(content.isEmpty());
    assertEquals(FileUtils.read(plugin, TEST_FILE_WITHOUT_DIR_PATH), content);
  }

  @Test
  public void saveDefaultResourcesWithDir() throws IOException {
    ConfigUtil.saveDefaultResources(plugin, TEST_FILE_WITH_DIR_PATH);
    File saved = FileUtils.getDatafolderFile(plugin, TEST_FILE_WITH_DIR_PATH);
    assertTrue(saved.isFile());

    String content = org.apache.commons.io.FileUtils.readFileToString(saved);

    assertFalse(content.isEmpty());
    assertEquals(FileUtils.read(plugin, TEST_FILE_WITH_DIR_PATH), content);
  }

  @Test
  public void saveDefaultResourcesNotExisting() throws IOException {
    ConfigUtil.saveDefaultResources(plugin, NON_EXISTING_TEST_FILE_WITH_DIR_PATH);
    File saved = FileUtils.getDatafolderFile(plugin, NON_EXISTING_TEST_FILE_WITH_DIR_PATH);
    assertTrue(saved.isFile());

    String content = org.apache.commons.io.FileUtils.readFileToString(saved);
    assertTrue(content.isEmpty());
  }

  @Test(expected = FileNotFoundException.class)
  public void saveDefaultResourcesNotExistingWhenNotCreating() throws IOException {
    ConfigUtil.saveDefaultResources(plugin, false, NON_EXISTING_TEST_FILE_WITH_DIR_PATH);
  }

  @Test
  public void saveDefaultResourcesIgnoresNull() throws IOException {
    ConfigUtil.saveDefaultResources(plugin, null, TEST_FILE_WITHOUT_DIR_PATH);
    File saved = FileUtils.getDatafolderFile(plugin, TEST_FILE_WITHOUT_DIR_PATH);
    assertTrue(saved.isFile());

    String content = org.apache.commons.io.FileUtils.readFileToString(saved);
    assertFalse(content.isEmpty());
    assertEquals(FileUtils.read(plugin, TEST_FILE_WITHOUT_DIR_PATH), content);

    assertEquals(FileUtils.getFileNames(plugin), Collections.singletonList(TEST_FILE_WITHOUT_DIR_PATH));
  }

  @Test
  public void saveDefaultResourcesIgnoresEmptyString() throws IOException {
    ConfigUtil.saveDefaultResources(plugin, "", TEST_FILE_WITHOUT_DIR_PATH);
    File saved = FileUtils.getDatafolderFile(plugin, TEST_FILE_WITHOUT_DIR_PATH);
    assertTrue(saved.isFile());

    String content = FileUtils.read(saved);
    assertNotNull(content);
    assertFalse(content.isEmpty());
    assertEquals(FileUtils.read(plugin, TEST_FILE_WITHOUT_DIR_PATH), content);

    assertEquals(FileUtils.getFileNames(plugin), Collections.singletonList(TEST_FILE_WITHOUT_DIR_PATH));
  }

  @Test
  public void saveDefaultResourcesDoesNotOverwrite() throws IOException {
    String content = "yaegstfiuaeifyefgsaiefgipsaefåøæ";
    FileUtils.write(content, plugin, TEST_FILE_WITH_DIR_PATH);
    ConfigUtil.saveDefaultResources(plugin, TEST_FILE_WITH_DIR_PATH);
    assertEquals(content, FileUtils.read(plugin, TEST_FILE_WITH_DIR_PATH));
  }

  @Test
  public void saveDefaultResourcesSavesMultipleFiles() throws IOException {
    String[] filesPaths = {TEST_FILE_WITH_DIR_PATH, TEST_FILE_WITHOUT_DIR_PATH, NON_EXISTING_TEST_FILE_WITH_DIR_PATH};

    List<File> expectedFiles = new ArrayList<>();
    for (String path : filesPaths) {
      expectedFiles.add(FileUtils.getDatafolderFile(plugin, path.split("[/\\\\]")));
    }

    ConfigUtil.saveDefaultResources(plugin, filesPaths);
    List<File> savedFiles = FileUtils.getRecursiveFiles(false, plugin);

    Collections.sort(expectedFiles);
    Collections.sort(savedFiles);
    assertEquals(expectedFiles, savedFiles);

    for (File file : savedFiles) {
      assertTrue("File " + file + " does not exist", file.exists());
    }
  }


  //////////////
  // flatKeys //
  //////////////


  @Test
  public void flatKeys__javadoc_example_works() {
    String yaml = //
      "parent:\n" + //
      "   key: true\n" + //
      "   child:\n" + //
      "     value: 1\n" + //
      "     sibling: 2\n" + //
      "   list:\n" + //
      "     - hello\n" + //
      "     - world\n" + //
      "     - another_list:\n" + //
      "       - wow: true\n" + //
      "   empty_list: []";

    ConfigurationSection conf = ConfigUtil.loadFromStringOrNull(yaml);
    assertNotNull(conf);
    Set<String> expected = new HashSet<>(Arrays.asList("parent.key", "parent.child.value", "parent.child.sibling",
                                                       "parent.list.0", "parent.list.1",
                                                       "parent.list.2.another_list.0.wow", "parent.empty_list"));
    assertEquals(expected.size(), ConfigUtil.flatKeys(conf).size());
    assertEquals(expected, ConfigUtil.flatKeys(conf));
  }


  /////////////////
  // getImproved //
  /////////////////


  @Test
  public void get__as_capable_as_normal_get() {
    String yaml = //
      "parent:\n" + //
      "   key: true\n" + //
      "   child:\n" + //
      "     value: 1\n" + //
      "     sibling: 2\n" + //
      "   list:\n" + //
      "     - hello\n" + //
      "     - world\n" + //
      "     - another_list:\n" + //
      "       - wow: true\n" + //
      "   empty_list: []";

    ConfigurationSection conf = ConfigUtil.loadFromStringOrNull(yaml);
    assertNotNull(conf);

    assertEquals(conf.get("parent"), ConfigUtil.get(conf, "parent"));
    assertEquals(conf.get("parent.key"), ConfigUtil.get(conf, "parent.key"));
    assertEquals(conf.get("parent.child"), ConfigUtil.get(conf, "parent.child"));
    assertEquals(conf.get("parent.child.value"), ConfigUtil.get(conf, "parent.child.value"));

  }

  @Test
  public void get__can_get_simple_values_from_lists() {
    String yaml = //
      "parent:\n" + //
      "   list:\n" + //
      "     - hello\n" + //
      "     - world\n" + //
      "   empty_list: []";

    ConfigurationSection conf = ConfigUtil.loadFromStringOrNull(yaml);
    assertNotNull(conf);


    assertEquals(conf.get("parent.list"), ConfigUtil.get(conf, "parent.list"));
    assertEquals(conf.get("parent.empty_list"), ConfigUtil.get(conf, "parent.empty_list"));
    assertNotNull(ConfigUtil.get(conf, "parent.list"));
    assertNotNull(ConfigUtil.get(conf, "parent.empty_list"));

    assertEquals(Arrays.asList("hello", "world"), ConfigUtil.get(conf, "parent.list"));
    assertEquals(conf.getList("parent.list").get(0), ConfigUtil.get(conf, "parent.list.0"));
    assertEquals("hello", ConfigUtil.get(conf, "parent.list.0"));
  }


  @Test
  public void get__can_get_config_sections_from_lists() throws InvalidConfigurationException {
    String yaml = //
      "parent:\n" + //
      "   list:\n" + //
      "     - another_list:\n" + //
      "       - wow: true";

    ConfigurationSection conf = ConfigUtil.loadFromStringOrNull(yaml);
    assertNotNull(conf);

    String listYaml = //
      "another_list:\n" + //
      "  - wow: true";
    YamlConfiguration listConf = ConfigUtil.loadFromStringOrNull(listYaml);
    assertFalse(ConfigUtil.isEmpty(listConf));

    assertEquals(listConf.saveToString(),
                 ConfigUtil.get(conf, "parent.list.0", new YamlConfiguration()).saveToString());

    List<?> list = listConf.getList("another_list");
    assertNotNull(list);

    assertEquals(list.toString(), ConfigUtil.get(conf, "parent.list.0.another_list").toString());

    assertEquals(true, ConfigUtil.get(conf, "parent.list.0.another_list.0.wow"));
  }


  //////////
  // diff //
  //////////


  @Test
  public void diff__one_empty_one_not_empty__returns_not_empty_A() throws InvalidConfigurationException {
    String yamlA = //
      "parent:\n" + //
      "   key: true\n" + //
      "   child:\n" + //
      "     value: 1";

    ConfigurationSection confA = ConfigUtil.loadFromString(yamlA);
    ConfigurationSection actual = ConfigUtil.diff(confA, new YamlConfiguration());
    assertEquals(ConfigUtil.flatKeys(confA), ConfigUtil.flatKeys(actual));
  }


  @Test
  public void diff__one_empty_one_not_empty__returns_not_empty_B() throws InvalidConfigurationException {
    String yamlA = //
      "parent:\n" + //
      "   key: true\n" + //
      "   child:\n" + //
      "     value: 1";

    ConfigurationSection confB = ConfigUtil.loadFromString(yamlA);
    ConfigurationSection actual = ConfigUtil.diff(new YamlConfiguration(), confB);
    assertEquals(ConfigUtil.flatKeys(confB), ConfigUtil.flatKeys(actual));
  }

  @Test
  public void diff__unique_and_common_keys___returns_set_difference() throws InvalidConfigurationException {
    String yamlA = //
      "parent:\n" + //
      "   key: true\n" + //
      "   child:\n" + //
      "     value: 1";

    String yamlB = //
      "parent:\n" + //
      "   key: false\n" + //
      "   child:\n" + //
      "     sibling: 2";


    String yamlResult = //
      "parent:\n" + //
      "   child:\n" + //
      "     sibling: 2\n" +//
      "     value: 1";

    ConfigurationSection confA = ConfigUtil.loadFromString(yamlA);
    ConfigurationSection confB = ConfigUtil.loadFromString(yamlB);
    YamlConfiguration expected = ConfigUtil.loadFromString(yamlResult);
    ConfigurationSection actual = ConfigUtil.diff(confA, confB);
    assertEquals(ConfigUtil.flatKeys(expected), ConfigUtil.flatKeys(actual));
  }


  @Test
  public void diff__works_with_lists() throws InvalidConfigurationException {
    String yamlA = //
      "parent:\n" + //
      "   key: true\n" + //
      "   child:\n" + //
      "    - common: 2\n" +//
      "    - value: 1";

    String yamlB = //
      "parent:\n" + //
      "   key: false\n" + //
      "   child:\n" + //
      "    - common: 2\n" +//
      "    - sibling: 2";


    String yamlResult = //
      "parent:\n" + //
      "   child:\n" + //
      "    1:\n" + //
      "      sibling: 2\n" +//
      "      value: 1";

    ConfigurationSection confA = ConfigUtil.loadFromString(yamlA);
    ConfigurationSection confB = ConfigUtil.loadFromString(yamlB);

    YamlConfiguration expected = ConfigUtil.loadFromString(yamlResult);
    ConfigurationSection actual = ConfigUtil.diff(confA, confB);
    assertEquals(ConfigUtil.flatKeys(expected), ConfigUtil.flatKeys(actual));
  }
}
