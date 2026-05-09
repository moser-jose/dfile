package dfile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @TempDir
    Path tempDir;

    private File createFile(String name) throws IOException {
        Path file = tempDir.resolve(name);
        Files.createFile(file);
        return file.toFile();
    }

    private File createSubDir(String name) throws IOException {
        Path dir = tempDir.resolve(name);
        Files.createDirectory(dir);
        return dir.toFile();
    }

    private File createFileIn(File dir, String name) throws IOException {
        Path file = dir.toPath().resolve(name);
        Files.createFile(file);
        return file.toFile();
    }

    // listAllFiles

    @Test
    void listAllFiles_returnsAllFilesAndDirectories() throws IOException {
        createFile("a.txt");
        createFile("b.mp3");
        File sub = createSubDir("sub");
        createFileIn(sub, "c.pdf");

        FileManager fm = new FileManager();
        List<String> results = fm.listAllFiles(tempDir.toFile());

        assertEquals(4, results.size()); // 1 dir + 3 files
        assertTrue(results.stream().anyMatch(r -> r.endsWith("a.txt")));
        assertTrue(results.stream().anyMatch(r -> r.endsWith("b.mp3")));
        assertTrue(results.stream().anyMatch(r -> r.contains("sub")));
        assertTrue(results.stream().anyMatch(r -> r.endsWith("c.pdf")));
    }

    @Test
    void listAllFiles_emptyDirectory_returnsEmptyList() {
        FileManager fm = new FileManager();
        List<String> results = fm.listAllFiles(tempDir.toFile());
        assertTrue(results.isEmpty());
    }

    @Test
    void listAllFiles_pathDoesNotExist_returnsEmptyList() {
        FileManager fm = new FileManager();
        List<String> results = fm.listAllFiles(new File("/nonexistent/path"));
        assertTrue(results.isEmpty());
    }

    // listFilesByExtension

    @Test
    void listFilesByExtension_returnsOnlyMatchingFiles() throws IOException {
        createFile("movie.mp3");
        createFile("notes.txt");
        createFile("ipdf.pdf");
        File sub = createSubDir("sub");
        createFileIn(sub, "episode.mp3");

        FileManager fm = new FileManager();
        List<String> results = fm.listFilesByExtension(tempDir.toFile(), ".mp3");

        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(r -> r.endsWith(".mp3")));
    }

    @Test
    void listFilesByExtension_noMatch_returnsEmptyList() throws IOException {
        createFile("notes.txt");
        createFile("ipdf.pdf");

        FileManager fm = new FileManager();
        List<String> results = fm.listFilesByExtension(tempDir.toFile(), ".mp3");

        assertTrue(results.isEmpty());
    }

    // countFilesByExtension

    @Test
    void countFilesByExtension_countsCorrectly() throws IOException {
        createFile("a.txt");
        createFile("b.txt");
        createFile("c.pdf");
        File sub = createSubDir("sub");
        createFileIn(sub, "d.txt");

        FileManager fm = new FileManager();
        assertEquals(3, fm.countFilesByExtension(tempDir.toFile(), ".txt"));
        assertEquals(1, fm.countFilesByExtension(tempDir.toFile(), ".pdf"));
        assertEquals(0, fm.countFilesByExtension(tempDir.toFile(), ".mp3"));
    }

    @Test
    void countFilesByExtension_pathDoesNotExist_returnsZero() {
        FileManager fm = new FileManager();
        assertEquals(0, fm.countFilesByExtension(new File("/nonexistent"), ".txt"));
    }

    // removeFiles (single extension)

    @Test
    void removeFiles_removesMatchingFiles() throws IOException {
        File txt1 = createFile("a.txt");
        File txt2 = createFile("b.txt");
        File pdf  = createFile("c.pdf");

        FileManager fm = new FileManager();
        fm.removeFiles(tempDir.toFile(), ".txt");

        assertFalse(txt1.exists());
        assertFalse(txt2.exists());
        assertTrue(pdf.exists());
    }

    @Test
    void removeFiles_removesRecursively() throws IOException {
        File sub = createSubDir("sub");
        File txt = createFileIn(sub, "deep.txt");
        File pdf = createFile("keep.pdf");

        FileManager fm = new FileManager();
        fm.removeFiles(tempDir.toFile(), ".txt");

        assertFalse(txt.exists());
        assertTrue(pdf.exists());
    }

    // removeFiles (multiple extensions)

    @Test
    void removeFiles_multipleExtensions_removesAllMatching() throws IOException {
        File txt = createFile("a.txt");
        File pdf = createFile("b.pdf");
        File mp3 = createFile("c.mp3");

        FileManager fm = new FileManager();
        fm.removeFiles(tempDir.toFile(), new String[]{".txt", ".pdf"});

        assertFalse(txt.exists());
        assertFalse(pdf.exists());
        assertTrue(mp3.exists());
    }

    // removeDirectory

    @Test
    void removeDirectory_removesDirectoryAndContents() throws IOException {
        File sub = createSubDir("toDelete");
        createFileIn(sub, "file.txt");

        FileManager fm = new FileManager();
        fm.removeDirectory(sub);

        assertFalse(sub.exists());
    }

    @Test
    void removeDirectory_pathDoesNotExist_doesNotThrow() {
        FileManager fm = new FileManager();
        assertDoesNotThrow(() -> fm.removeDirectory(new File("/nonexistent/path")));
    }
}
