/**
 * Copyright(c) 2018 Moser José, Inc. All rights reserved.
 * Created on Jan 25, 2018, at 2:34:28 AM
 * @author Moser José
 * @version 1.1.0
 * @email msommy.jose@gmail.com
 */
package dfile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FileManager {

    private File inFile;

    public File getInFile() {
        return inFile;
    }

    public void setInFile(File inFile) {
        this.inFile = inFile;
    }

    public FileManager() {}

    public FileManager(File inFile) {
        this.inFile = inFile;
    }

    public void removeDirectory(File dir) {
        if (!dir.exists()) {
            System.err.println("Path does not exist: " + dir.getAbsolutePath());
            return;
        }
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    removeDirectory(file);
                }
            }
        }
        if (!dir.delete()) {
            System.err.println("Could not delete: " + dir.getAbsolutePath());
        }
    }

    public List<String> listAllFiles(File path) {
        List<String> results = new ArrayList<>();
        if (!path.exists()) {
            System.err.println("Path does not exist: " + path.getAbsolutePath());
            return results;
        }
        File[] files = path.listFiles();
        if (files == null) {
            System.err.println("Permission denied: " + path.getAbsolutePath());
            return results;
        }
        collectAllFiles(path, results);
        return results;
    }

    private void collectAllFiles(File path, List<String> results) {
        File[] files = path.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) {
                results.add("** Directory: " + file.getAbsolutePath() + " **");
                collectAllFiles(file, results);
            } else {
                results.add(file.getAbsolutePath());
            }
        }
    }

    public void removeFiles(File path, String extension) {
        if (!path.exists()) {
            System.err.println("Path does not exist: " + path.getAbsolutePath());
            return;
        }
        File[] files = path.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) {
                removeFiles(file, extension);
            } else if (file.getName().endsWith(extension)) {
                try {
                    Files.delete(file.toPath());
                } catch (IOException e) {
                    System.err.println("Error deleting " + file.getAbsolutePath() + ": " + e.getMessage());
                }
            }
        }
    }

    public void removeFiles(File path, String[] extensions) {
        if (!path.exists()) {
            System.err.println("Path does not exist: " + path.getAbsolutePath());
            return;
        }
        List<String> extList = Arrays.asList(extensions);
        removeFilesRecursive(path, extList);
    }

    private void removeFilesRecursive(File path, List<String> extensions) {
        File[] files = path.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) {
                removeFilesRecursive(file, extensions);
            } else {
                String name = file.getName();
                for (String ext : extensions) {
                    if (name.endsWith(ext)) {
                        try {
                            Files.delete(file.toPath());
                        } catch (IOException e) {
                            System.err.println("Error deleting " + file.getAbsolutePath() + ": " + e.getMessage());
                        }
                        break;
                    }
                }
            }
        }
    }

    public List<String> listFilesByExtension(File path, String extension) {
        List<String> results = new ArrayList<>();
        if (!path.exists()) {
            System.err.println("Path does not exist: " + path.getAbsolutePath());
            return results;
        }
        collectByExtension(path, extension, results);
        return results;
    }

    private void collectByExtension(File path, String extension, List<String> results) {
        File[] files = path.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) {
                collectByExtension(file, extension, results);
            } else if (file.getName().endsWith(extension)) {
                results.add(file.getAbsolutePath());
            }
        }
    }

    public void showStats(File path) {
        if (!path.exists()) {
            System.err.println("Path does not exist: " + path.getAbsolutePath());
            return;
        }
        Map<String, Integer> byExtension = new TreeMap<>();
        int[] totals = new int[2]; // [0] files, [1] directories
        collectStats(path, byExtension, totals);

        System.out.println("Directories: " + totals[1]);
        System.out.println("Files      : " + totals[0]);
        System.out.println();
        byExtension.forEach((ext, count) ->
            System.out.printf("  %-15s %d%n", ext, count));
    }

    private void collectStats(File path, Map<String, Integer> byExtension, int[] totals) {
        File[] files = path.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) {
                totals[1]++;
                collectStats(file, byExtension, totals);
            } else {
                totals[0]++;
                String name = file.getName();
                int dot = name.lastIndexOf('.');
                String ext = dot >= 0 ? name.substring(dot) : "(no extension)";
                byExtension.merge(ext, 1, Integer::sum);
            }
        }
    }

    public int countFilesByExtension(File path, String extension) {
        if (!path.exists()) {
            System.err.println("Path does not exist: " + path.getAbsolutePath());
            return 0;
        }
        return countFiles(path, extension);
    }

    private int countFiles(File path, String extension) {
        File[] files = path.listFiles();
        if (files == null) return 0;
        int count = 0;
        for (File file : files) {
            if (file.isDirectory()) {
                count += countFiles(file, extension);
            } else if (file.getName().endsWith(extension)) {
                count++;
            }
        }
        return count;
    }
}
