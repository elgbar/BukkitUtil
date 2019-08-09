package no.kh498.util;

import org.apache.commons.io.IOUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * @author Elg
 */
@PrepareForTest(FileUtils.class)
@RunWith(PowerMockRunner.class)
public class FileUtilsTest {

    private Plugin plugin;

    @Rule
    public TemporaryFolder dataFolder = new TemporaryFolder();

    @BeforeClass
    public static void init() {
        FileUtils.logger = mock(Logger.class);
    }

    @Before
    public void setUp() {
        plugin = mock(Plugin.class);
        PowerMockito.when(plugin.getDataFolder()).thenReturn(dataFolder.getRoot());
    }


    ////////////////
    // meta tests //
    ////////////////


    @Test
    public void gettingDatafolderWorks() {
        assertEquals(dataFolder.getRoot(), plugin.getDataFolder());
    }

    @Test
    public void datafolderIsAFolder() {
        assertTrue(plugin.getDataFolder().isDirectory());
    }


    ////////////////////////
    // createParentFolder //
    ////////////////////////


    @Test
    public void createParentFolderDoesNotCreateFile() {
        File file = FileUtils.getDatafolderFile(plugin, "folder", "file.txt");
        File folder = FileUtils.getDatafolderFile(plugin, "folder");

        assertNotNull(folder);

        assertFalse(FileUtils.createParentFolders(file));
        assertTrue(folder.isDirectory());
        assertFalse(file.exists());
    }

    @Test
    public void createParentFolderFailsWhenParentIsFile() throws IOException {
        File file = FileUtils.getDatafolderFile(plugin, "folder", "file.txt");
        File folder = FileUtils.createDatafolderFile(plugin, "folder");

        assertNotNull(folder);

        assertTrue(FileUtils.createParentFolders(file));
        assertTrue(folder.isFile());
        assertFalse(file.isFile());
    }

    //////////////////////////
    // createDatafolderFile //
    //////////////////////////


    @Test
    public void createDataFolderCreatesCorrectPath() throws IOException {
        File file = FileUtils.createDatafolderFile(plugin, "test.json");
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertEquals(dataFolder.getRoot().getPath() + File.separator + "test.json", file.getPath());
    }

    @Test
    public void createDataFolderCreatesCorrectSubPath() throws IOException {
        File file = FileUtils.createDatafolderFile(plugin, "testii", "test.json");
        assertTrue(file.exists());
        assertTrue(file.isFile());
        String pathExpected = dataFolder.getRoot().getPath() + File.separator + "testii" + File.separator + "test.json";
        assertEquals(pathExpected, file.getPath());
    }

    @Test
    public void createDataFolderCreatesCorrectSubSubPath() throws IOException {
        File file = FileUtils.createDatafolderFile(plugin, "testii", "testii2", "test.json");
        assertTrue(file.exists());
        assertTrue(file.isFile());

        String pathExpected =
            dataFolder.getRoot().getPath() + File.separator + "testii" + File.separator + "testii2" + File.separator +
            "test.json";
        assertEquals(pathExpected, file.getPath());
    }

    @Test
    public void createDatafolderFileReturnOldFileWhenTheyExist() throws IOException {
        File file = FileUtils.createDatafolderFile(plugin, "testii", "testii2", "test.json");
        assertEquals(file, FileUtils.createDatafolderFile(plugin, "testii", "testii2", "test.json"));
        assertEquals(file, FileUtils.getDatafolderFile(plugin, "testii", "testii2", "test.json"));
    }

    @Test
    public void createDatafolderFileDoesNotOverwriteDataIfFileAlreadyExists() throws IOException {
        assertTrue(FileUtils.write("test", plugin, "file"));
        FileUtils.createDatafolderFile(plugin, "file");
        assertEquals("test", FileUtils.read(plugin, "file"));
    }


    ///////////////////
    // createFolders //
    ///////////////////


    @Test
    public void makeFolderActuallyMakesFolders() {
        File file = FileUtils.getDatafolderFile(plugin, "folder");

        Assert.assertFalse(file.isDirectory());

        assertTrue(FileUtils.createFolders(plugin, "folder"));
        Assert.assertTrue(file.isDirectory());
    }

    @Test
    public void makeFolderHandleFileIsFileCorrectly() throws IOException {
        File file = FileUtils.createDatafolderFile(plugin, "folder");

        Assert.assertTrue(file.isFile());
        Assert.assertFalse(FileUtils.createFolders(plugin, "folder"));
        Assert.assertTrue(file.isFile());
    }


    ///////////
    // write //
    ///////////

    @Test
    public void writeWritesCorrectlyUTF8() throws IOException {
        String filename = "testFile.txt";
        String content = "this is the content of the of file, but with åøæ and " +
                         "c̷̛̬̱̯̳͖͕̬̹̹͔̠̓̊̍ͭ̐͆ͩ͋̉̍͒̀̕ͅo̶̥̳͙̹̗͕͇̩̹̦̬̗̼̙̖ͪ̾̓̋͋̀ͬ͝r͍͈͔̱͓̫̻̱̮ͧ̆ͣ̓͗ͯ̉ͧ̓̽͘͞r" +
                         "̵̽̋ͣ͊͐ͪ̀̀͞͏͙̗̗̫̞̞̼͈̳͕u" +
                         "̴̴̧̲̫͇̥͍̻̬̘̝͙̬͂ͭ̔̆͌̅̌ͅp̵̪̥̱̣̘͎̲͇͎͔̤̪͇̒̍̇ͨ̎͒̍͂͢͝t̥̬̪͉̥͓̱̥̹̣͚͖̟͙ͪ̍ͭ̓̑͑̿̎̋̊͆͑̓ͪ̓̓̇ͧ͜͜͞͞ " +
                         "̧̛̖͍̥̯͙̼̯͚͔̦̥͍̜͕̱̓̒͆͊̃̾̌ͥ̈̑̀͑́͡͠t̨̳͖̹̺͎̘̱̗͈͚̞͖̪̹ͫ̅̄͆͋̓̓ͤ͛̒̽̚͢͝͡e" +
                         "̡̻̱̰̝͎̭̼̮͇̝͑̀̿́ͥ̓̒͐ͫ͊̈ͫ͛̽̅ͩ͢x" +
                         "̢̘̲̺̝͔͕͍̪͇̱̰͖̯͈̬̞̻͛̍̋̆ͩ̆ͮ͐̽ͧ̇̍ͫt̄̑̊͑̈ͤͤͮ̃҉̵̶̢̝͍͙͈̲̝̱̮̼̭̺̞͈̥̬̥̭͇̺̗̩";

        assertTrue(FileUtils.write(content, plugin, filename));

        File file = FileUtils.getDatafolderFile(plugin, filename);

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        String readContentNIO = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).get(0);
        String readContentFUread = FileUtils.read(plugin, filename);

        assertEquals(content, readContentNIO);
        assertEquals(content, readContentFUread);

    }

    @Test
    public void writeWritesCorrectlyYAMLConfig() throws IOException {

        String filename = "testFile.yml";

        YamlConfiguration conf = new YamlConfiguration();
        conf.set("test", true);
        conf.set("list of stuff", Arrays.asList(true, false, false, true, true, true));
        conf.set("set of stuff", Collections.addAll(new HashSet<>(), "fire", 2, new Object(), "hello"));
        ConfigurationSection section = conf.createSection("section");
        section.set("more stuff", false);

        String confStr = conf.saveToString();


        assertTrue(FileUtils.write(confStr, plugin, filename));

        File file = FileUtils.getDatafolderFile(plugin, filename);

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        String readContentNIO = String.join("\n", Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)) + "\n";
        String readContentFUread = FileUtils.read(plugin, filename);

        assertEquals(confStr, readContentNIO);
        assertEquals(confStr, readContentFUread);

    }

    @Test
    public void writeFailsWhenGivenFileIsFolder() throws IOException {
        assertFalse(FileUtils.write("whatever", plugin));
    }

    //////////
    // save //
    //////////

    @Test
    public void saveSavesFilesAsUTF8() throws IOException {
        String filename = "testFile.txt";
        String content = "this is the content of the of file, but with åøæ and " +
                         "c̷̛̬̱̯̳͖͕̬̹̹͔̠̓̊̍ͭ̐͆ͩ͋̉̍͒̀̕ͅo̶̥̳͙̹̗͕͇̩̹̦̬̗̼̙̖ͪ̾̓̋͋̀ͬ͝r͍͈͔̱͓̫̻̱̮ͧ̆ͣ̓͗ͯ̉ͧ̓̽͘͞r" +
                         "̵̽̋ͣ͊͐ͪ̀̀͞͏͙̗̗̫̞̞̼͈̳͕u" +
                         "̴̴̧̲̫͇̥͍̻̬̘̝͙̬͂ͭ̔̆͌̅̌ͅp̵̪̥̱̣̘͎̲͇͎͔̤̪͇̒̍̇ͨ̎͒̍͂͢͝t̥̬̪͉̥͓̱̥̹̣͚͖̟͙ͪ̍ͭ̓̑͑̿̎̋̊͆͑̓ͪ̓̓̇ͧ͜͜͞͞ " +
                         "̧̛̖͍̥̯͙̼̯͚͔̦̥͍̜͕̱̓̒͆͊̃̾̌ͥ̈̑̀͑́͡͠t̨̳͖̹̺͎̘̱̗͈͚̞͖̪̹ͫ̅̄͆͋̓̓ͤ͛̒̽̚͢͝͡e" +
                         "̡̻̱̰̝͎̭̼̮͇̝͑̀̿́ͥ̓̒͐ͫ͊̈ͫ͛̽̅ͩ͢x" +
                         "̢̘̲̺̝͔͕͍̪͇̱̰͖̯͈̬̞̻͛̍̋̆ͩ̆ͮ͐̽ͧ̇̍ͫt̄̑̊͑̈ͤͤͮ̃҉̵̶̢̝͍͙͈̲̝̱̮̼̭̺̞͈̥̬̥̭͇̺̗̩";

        InputStream is = IOUtils.toInputStream(content, StandardCharsets.UTF_8);

        assertTrue(FileUtils.save(is, plugin, filename));

        File file = FileUtils.getDatafolderFile(plugin, filename);

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        String readContentNIO = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).get(0);
        String readContentFUread = FileUtils.read(plugin, filename);

        assertEquals(content, readContentNIO);
        assertEquals(content, readContentFUread);
    }


    ///////////////////////
    // getDatafolderFile //
    ///////////////////////


    @Test
    public void getDatafolderFileDoesNotCreateAnything() {
        File file = FileUtils.getDatafolderFile(plugin, "notafile.txt");

        assertFalse(file.exists());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDatafolderFileWithNullElementReturnsNull() {
        FileUtils.getDatafolderFile(plugin, null, "notafile.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDatafolderFileWithNullElementReturnsNull2() {
        FileUtils.getDatafolderFile(plugin, "folder", null);
    }


    //////////
    // read //
    //////////


    //see write and save test methods above


    //////////////
    // getFiles //
    //////////////


    @Test
    public void getFilesReturnAllFilesInFolder() throws IOException {
        Random random = new Random();
        int nrOfFiles = random.nextInt(5) + 10;
        List<File> createdFiles = new ArrayList<>();

        for (int i = 0; i < nrOfFiles; i++) {
            File file = new File(plugin.getDataFolder(), "file" + i);
            createdFiles.add(file);
            assertTrue(file.createNewFile());
        }

        List<File> files = FileUtils.getFiles(plugin);
        assertNotNull(files);
        assertEquals(nrOfFiles, files.size());

        Collections.sort(createdFiles);
        Collections.sort(files);
        assertEquals(createdFiles, files);
    }

    @Test
    public void getFilesInEmptyFolderReturnEmptyList() {
        List<File> files = FileUtils.getFiles(plugin);
        assertNotNull(files);
        assertTrue(files.isEmpty());
    }

    @Test
    public void getFilesFromInvalidLocationFails() throws IOException {
        FileUtils.createDatafolderFile(plugin, "file");
        assertNull(FileUtils.getFiles(plugin, "folder"));
        assertNull(FileUtils.getFiles(plugin, "non-existence"));
    }


    //////////////////
    // getFileNames //
    //////////////////

    @Test
    public void getFileNamesIgnoresFolders() throws IOException {
        Random random = new Random();
        int nrOfFiles = random.nextInt(5) + 40;
        List<String> createdFiles = new ArrayList<>();

        for (int i = 0; i < nrOfFiles; i++) {
            File file = new File(plugin.getDataFolder(), "file" + i);

            if (random.nextBoolean()) {
                createdFiles.add(file.getName());
                assertTrue(file.createNewFile());
            }
            else {
                assertTrue(file.mkdir());
            }
        }

        List<String> files = FileUtils.getFileNames(plugin);
        assertNotNull(files);

        Collections.sort(createdFiles);
        Collections.sort(files);
        assertEquals(createdFiles, files);

    }


    ///////////////////////
    // getRecursiveFiles //
    ///////////////////////


    @Test
    public void getRecursiveFilesIsSameAsGetFilesWhenNoSubfolders() throws IOException {
        Random random = new Random();
        int nrOfFiles = random.nextInt(5) + 40;
        List<File> createdFiles = new ArrayList<>();

        for (int i = 0; i < nrOfFiles; i++) {
            File file = new File(plugin.getDataFolder(), "recfile" + i);
            createdFiles.add(file);
            assertTrue(file.createNewFile());
        }

        List<File> files = FileUtils.getRecursiveFiles(false, plugin);
        assertEquals(nrOfFiles, files.size());

        Collections.sort(createdFiles);
        Collections.sort(files);
        assertEquals(createdFiles, files);
    }

    //inclusion of files (not recursive)

    @Test
    public void getRecursiveFilesIgnoresFilesWithHyphenWhenTrue() throws IOException {
        Random random = new Random();
        int nrOfFiles = random.nextInt(5) + 40;
        List<File> createdFiles = new ArrayList<>();

        for (int i = 0; i < nrOfFiles; i++) {
            File file = new File(plugin.getDataFolder(), "recfile" + i);
            createdFiles.add(file);
            assertTrue(file.createNewFile());
        }

        for (int i = 0; i < nrOfFiles; i++) {
            File file = new File(plugin.getDataFolder(), "-recfile" + i);
            assertTrue(file.createNewFile());
        }

        List<File> files = FileUtils.getRecursiveFiles(true, plugin);

        Collections.sort(createdFiles);
        Collections.sort(files);
        assertEquals(createdFiles, files);
    }

    @Test
    public void getRecursiveFilesIncludesFilesWithHyphenWhenFalse() throws IOException {
        Random random = new Random();
        int nrOfFiles = random.nextInt(5) + 40;
        List<File> createdFiles = new ArrayList<>();

        for (int i = 0; i < nrOfFiles; i++) {
            File file = new File(plugin.getDataFolder(), "recfile" + i);
            createdFiles.add(file);
            assertTrue(file.createNewFile());
        }

        for (int i = 0; i < nrOfFiles; i++) {
            File file = new File(plugin.getDataFolder(), "-recfile" + i);
            createdFiles.add(file);
            assertTrue(file.createNewFile());
        }

        List<File> files = FileUtils.getRecursiveFiles(false, plugin);


        Collections.sort(createdFiles);
        Collections.sort(files);
        assertEquals(createdFiles, files);
    }

    //inclusion of folders (only slightly recursive)

    @Test
    public void getRecursiveFilesIgnoresFoldersWithHyphenWhenTrue() throws IOException {
        getRecursiveFilesIgnoresFoldersWithHyphen(true);
    }

    @Test
    public void getRecursiveFilesIgnoresFoldersWithHyphenWhenFalse() throws IOException {
        getRecursiveFilesIgnoresFoldersWithHyphen(false);
    }

    private void getRecursiveFilesIgnoresFoldersWithHyphen(boolean excludeHyphenPrefix) throws IOException {
        Random random = new Random();
        List<File> createdFiles = new ArrayList<>();
        int nrOfFiles = random.nextInt(5) + 40;
        createdFiles.clear();

        for (int i = 0; i < nrOfFiles; i++) {

            File file = FileUtils.createDatafolderFile(plugin, excludeHyphenPrefix + "recfile" + i);
            assertTrue(file.isFile());
            createdFiles.add(file);

            File file2 = FileUtils.createDatafolderFile(plugin, "-hyphenFolder", "recfile2" + i);
            if (!excludeHyphenPrefix) { createdFiles.add(file2); }


            File file3 = FileUtils.createDatafolderFile(plugin, "alwaysIncluded", "recfile33" + i);
            createdFiles.add(file3);
        }

        List<File> files = FileUtils.getRecursiveFiles(excludeHyphenPrefix, plugin);


        Collections.sort(createdFiles);
        Collections.sort(files);
        assertEquals("excludeHyphenPrefix: " + excludeHyphenPrefix, createdFiles, files);
    }

    @Test
    public void getRecursiveFilesIncludesFoldersWithHyphenWhenFalse() throws IOException {
        Random random = new Random();
        int nrOfFiles = random.nextInt(5) + 40;
        List<File> createdFiles = new ArrayList<>();

        for (int i = 0; i < nrOfFiles; i++) {
            File file = new File(plugin.getDataFolder(), "recfile" + i);
            createdFiles.add(file);
            assertTrue(file.createNewFile());
        }

        for (int i = 0; i < nrOfFiles; i++) {
            File file = new File(plugin.getDataFolder(), "-recfile" + i);
            createdFiles.add(file);
            assertTrue(file.createNewFile());
        }

        List<File> files = FileUtils.getRecursiveFiles(false, plugin);

        Collections.sort(createdFiles);
        Collections.sort(files);
        assertEquals(createdFiles, files);
    }

    @Test
    public void getRecursiveFilesOnlyIgnoresHyphenWhenFirstChar() throws IOException {
        File a = FileUtils.createDatafolderFile(plugin, "-shouldBeIgnore");
        File b = FileUtils.createDatafolderFile(plugin, "s-houldNotBeIgnore");
        File c = FileUtils.createDatafolderFile(plugin, "should-Not-BeIgnore");
        File d = FileUtils.createDatafolderFile(plugin, "ShouldReallyNotBeIgnored");

        List<File> files = FileUtils.getRecursiveFiles(true, plugin);
        List<File> createdFiles = Arrays.asList(b, c, d);

        assertEquals(createdFiles.size(), files.size());

        assertFalse(files.contains(a));

        Collections.sort(createdFiles);
        Collections.sort(files);
        assertEquals(files, createdFiles);
    }


    ///////////////////////////
    // getInternalFileStream //
    ///////////////////////////


    private static final String[] TEST_BIN_FILE_WITH_DIR_PATH = {"folde1", "bro.png"};

    @Test
    public void getInternalFileStreamBinFileEquals() throws IOException {
        String s = "/" + String.join("/", TEST_BIN_FILE_WITH_DIR_PATH);
        InputStream isE = FileUtilsTest.class.getResourceAsStream(s);
        assertNotNull(isE);

        InputStream isA = FileUtils.getInternalFileStream(TEST_BIN_FILE_WITH_DIR_PATH);
        assertNotNull(isA);

        assertArrayEquals(IOUtils.toByteArray(isE), IOUtils.toByteArray(isA));
    }


    //////////////////////
    // readInternalFile //
    //////////////////////

    private static final String[] TEST_FILE_WITHOUT_DIR_PATH = {"testfile.txt"};
    private static final String[] TEST_FILE_WITH_DIR_PATH = {"folde1", "testfile.txt"};

    @Test
    public void readInternalFileReadsTestFileCorrectly() throws IOException {
        String s = "/" + String.join("/", TEST_FILE_WITH_DIR_PATH);
        InputStream is = FileUtilsTest.class.getResourceAsStream(s);
        assertNotNull(is);
        String fileContent = IOUtils.toString(is);

        assertEquals(fileContent, FileUtils.readInternalFile(TEST_FILE_WITH_DIR_PATH));
    }

    @Test
    public void readInternalFileReadsTestFileCorrectly2() throws IOException {
        String s = "/" + String.join("/", TEST_FILE_WITHOUT_DIR_PATH);
        InputStream is = FileUtilsTest.class.getResourceAsStream(s);
        assertNotNull(is);
        String fileContent = IOUtils.toString(is);

        assertEquals(fileContent, FileUtils.readInternalFile(TEST_FILE_WITHOUT_DIR_PATH));
    }

    @Test
    public void readInternalFileHandleNonExistingFileCorrectly() throws IOException {
        assertNull(FileUtils.readInternalFile("non-existing"));
    }
}
