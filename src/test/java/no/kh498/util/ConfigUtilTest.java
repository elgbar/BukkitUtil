package no.kh498.util;

import org.bukkit.plugin.Plugin;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.powermock.api.mockito.PowerMockito;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;

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
        Assert.assertTrue(saved.isFile());

        assertEquals(FileUtils.read(plugin, TEST_FILE_WITHOUT_DIR_PATH),
                     org.apache.commons.io.FileUtils.readFileToString(saved));
    }

    @Test
    public void saveDefaultResourcesWithDir() throws IOException {
        ConfigUtil.saveDefaultResources(plugin, TEST_FILE_WITH_DIR_PATH);
        File saved = FileUtils.getDatafolderFile(plugin, TEST_FILE_WITH_DIR_PATH);
        Assert.assertTrue(saved.isFile());

        String content = org.apache.commons.io.FileUtils.readFileToString(saved);

        assertFalse(content.isEmpty());
        assertEquals(FileUtils.read(plugin, TEST_FILE_WITH_DIR_PATH), content);
    }

    @Test
    public void saveDefaultResourcesNotExisting() throws IOException {
        ConfigUtil.saveDefaultResources(plugin, NON_EXISTING_TEST_FILE_WITH_DIR_PATH);
        File saved = FileUtils.getDatafolderFile(plugin, NON_EXISTING_TEST_FILE_WITH_DIR_PATH);
        Assert.assertTrue(saved.isFile());

        String content = org.apache.commons.io.FileUtils.readFileToString(saved);
        Assert.assertTrue(content.isEmpty());
    }

    @Test
    public void saveDefaultResourcesIgnoresNull() throws IOException {
        ConfigUtil.saveDefaultResources(plugin, null, TEST_FILE_WITHOUT_DIR_PATH);
        File saved = FileUtils.getDatafolderFile(plugin, TEST_FILE_WITHOUT_DIR_PATH);
        Assert.assertTrue(saved.isFile());

        String content = org.apache.commons.io.FileUtils.readFileToString(saved);
        assertFalse(content.isEmpty());
        assertEquals(FileUtils.read(plugin, TEST_FILE_WITHOUT_DIR_PATH), content);

        assertEquals(FileUtils.getFileNames(plugin), Collections.singletonList(TEST_FILE_WITHOUT_DIR_PATH));
    }

    @Test
    public void saveDefaultResourcesIgnoresEmptyString() throws IOException {
        ConfigUtil.saveDefaultResources(plugin, "", TEST_FILE_WITHOUT_DIR_PATH);
        File saved = FileUtils.getDatafolderFile(plugin, TEST_FILE_WITHOUT_DIR_PATH);
        Assert.assertTrue(saved.isFile());

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
        String[] filesPaths =
            {TEST_FILE_WITH_DIR_PATH, TEST_FILE_WITHOUT_DIR_PATH, NON_EXISTING_TEST_FILE_WITH_DIR_PATH};

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
}
