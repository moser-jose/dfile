/**
 * Copyright(c) 2018 Moser José, Inc. All rights reserved.
 * Created on Jan 25, 2018, at 2:34:28 AM
 * @author Moser José
 * @version 1.1.0
 * @email msommy.jose@gmail.com
 */
package dfile;

import java.io.File;

/**
 * Usage:
 *   java -jar DFile.jar <path> list [extension]
 *   java -jar DFile.jar <path> count [extension]
 *   java -jar DFile.jar <path> remove <ext1> [ext2 ...]
 *   java -jar DFile.jar <path> remove dir
 */
public class DFile {

    public static void main(String[] args) {
        if (args.length < 2) {
            printUsage();
            System.exit(1);
        }

        File path = new File(args[0]);
        if (!path.exists()) {
            System.err.println("Error: path does not exist — " + path.getAbsolutePath());
            System.exit(1);
        }

        FileManager fm = new FileManager(path);
        String operation = args[1].toLowerCase();

        switch (operation) {
            case "list":
                if (args.length >= 3) {
                    fm.listFilesByExtension(path, args[2]);
                } else {
                    fm.listAllFiles(path);
                }
                break;

            case "count":
                if (args.length >= 3) {
                    int total = fm.countFilesByExtension(path, args[2]);
                    System.out.println("Files with extension \"" + args[2] + "\": " + total);
                } else {
                    fm.showStats(path);
                }
                break;

            case "remove":
                if (args.length < 3) {
                    System.err.println("Error: specify at least one extension or \"dir\". Ex: remove .txt .doc | remove dir");
                    System.exit(1);
                }
                if (args[2].equalsIgnoreCase("dir")) {
                    fm.removeDirectory(path);
                    System.out.println("Directory removed.");
                } else if (args.length == 3) {
                    fm.removeFiles(path, args[2]);
                    System.out.println("Done.");
                } else {
                    String[] extensions = new String[args.length - 2];
                    System.arraycopy(args, 2, extensions, 0, extensions.length);
                    fm.removeFiles(path, extensions);
                    System.out.println("Done.");
                }
                break;

            default:
                System.err.println("Unknown operation: " + operation);
                printUsage();
                System.exit(1);
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  java -jar DFile.jar <path> list [extension]");
        System.out.println("  java -jar DFile.jar <path> count [extension]");
        System.out.println("  java -jar DFile.jar <path> remove <ext1> [ext2 ...]");
        System.out.println("  java -jar DFile.jar <path> remove dir");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java -jar DFile.jar /home/user/docs list");
        System.out.println("  java -jar DFile.jar /home/user/docs list .pdf");
        System.out.println("  java -jar DFile.jar C:\\Users\\user\\docs count .txt");
        System.out.println("  java -jar DFile.jar /tmp/folder remove .txt .doc .pdf .mp4");
        System.out.println("  java -jar DFile.jar /tmp/folder remove dir");
    }
}
