/**
 * Copyright(c) 2018 Moser José, Inc. All rights reserved.
 * Created on Jan 25, 2018, at 2:34:28 AM
 * @author Moser José
 * @version 1.1.0
 * @email msommy.jose@gmail.com
 */
package dfile;

import java.io.File;
import java.util.List;
import java.util.Scanner;

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
            case "ls":
                if (args.length >= 3) {
                    List<String> filtered = fm.listFilesByExtension(path, args[2]);
                    filtered.forEach(System.out::println);
                    System.out.println();
                    System.out.println("Files with extension \"" + args[2] + "\": " + filtered.size());
                } else {
                    List<String> all = fm.listAllFiles(path);
                    all.forEach(System.out::println);
                    System.out.println();
                    fm.showStats(path);
                }
                break;

            case "count":
            case "ct":
                if (args.length >= 3) {
                    int total = fm.countFilesByExtension(path, args[2]);
                    System.out.println("Files with extension \"" + args[2] + "\": " + total);
                } else {
                    fm.showStats(path);
                }
                break;

            case "remove":
            case "rm":
                if (args.length < 3) {
                    System.err.println("Error: specify at least one extension or \"dir\". Ex: remove .txt .doc | remove dir");
                    System.exit(1);
                }
                if (args[2].equalsIgnoreCase("dir") || args[2].equals("-d")) {
                    System.out.print("Delete directory \"" + path.getAbsolutePath() + "\"? [y/N]: ");
                    String answer;
                    try (Scanner scanner = new Scanner(System.in)) {
                        answer = scanner.nextLine().trim();
                    }
                    if (!answer.equalsIgnoreCase("y")) {
                        System.out.println("Cancelled.");
                        break;
                    }
                    fm.removeDirectory(path);
                    System.out.println("Directory removed.");
                } else if (args.length == 3) {
                    System.out.print("Delete all \"" + args[2] + "\" files in \"" + path.getAbsolutePath() + "\"? [y/N]: ");
                    String answerExt;
                    try (Scanner scanner = new Scanner(System.in)) {
                        answerExt = scanner.nextLine().trim();
                    }
                    if (!answerExt.equalsIgnoreCase("y")) {
                        System.out.println("Cancelled.");
                        break;
                    }
                    fm.removeFiles(path, args[2]);
                    System.out.println("Done.");
                } else {
                    String[] extensions = new String[args.length - 2];
                    System.arraycopy(args, 2, extensions, 0, extensions.length);
                    System.out.print("Delete all " + String.join(", ", extensions) + " files in \"" + path.getAbsolutePath() + "\"? [y/N]: ");
                    String answerExts;
                    try (Scanner scanner = new Scanner(System.in)) {
                        answerExts = scanner.nextLine().trim();
                    }
                    if (!answerExts.equalsIgnoreCase("y")) {
                        System.out.println("Cancelled.");
                        break;
                    }
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
        System.out.println("  java -jar DFile.jar <path> list|ls [extension]");
        System.out.println("  java -jar DFile.jar <path> count|ct [extension]");
        System.out.println("  java -jar DFile.jar <path> remove|rm <ext1> [ext2 ...]");
        System.out.println("  java -jar DFile.jar <path> remove|rm dir|-d");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java -jar DFile.jar /home/user/docs list");
        System.out.println("  java -jar DFile.jar /home/user/docs list .pdf");
        System.out.println("  java -jar DFile.jar C:\\Users\\user\\docs count .txt");
        System.out.println("  java -jar DFile.jar /tmp/folder remove .txt .doc .pdf .mp4");
        System.out.println("  java -jar DFile.jar /tmp/folder remove dir");
    }
}
