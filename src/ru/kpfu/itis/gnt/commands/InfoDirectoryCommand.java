package ru.kpfu.itis.gnt.commands;

import ru.kpfu.itis.gnt.exceptions.NonExistentOption;
import ru.kpfu.itis.gnt.util.CurrentPath;
import ru.kpfu.itis.gnt.util.Printing;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class InfoDirectoryCommand implements Command {
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_LIGHT_BLUE = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_RESET = "\u001b[0m";
    private static final long DEFAULT_SPACING = 12;
    private Printing[] printers;


    @Override
    public void execute(CurrentPath cPath, Path sourcePath, Path targetPath, String option, String[] availableOptions) {
        try {
            Path pathToPrint = targetPath == null ? cPath.getCurrPath() : targetPath;
            initPrinters(new File(pathToPrint.toString()));
            int optionIndex = 0;
            boolean found = false;
            for (String opt : availableOptions) {
                if (opt.equals(option)) {
                    printers[optionIndex].print();
                    found = true;
                    break;
                }
                optionIndex++;
            }
            if (!found) {
                throw new NonExistentOption("Option \"" + option + "\" does not exist.");
            }
        } catch (NonExistentOption | IOException ex) {
            System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
        }
    }

    private void initPrinters(File directory) {
        printers = new Printing[]{
                () -> printDirectories(directory, false),
                () -> printDirectories(directory, true),
                () -> printDirectoriesDetailed(directory.toPath(), false),
                () -> printDirectoriesDetailed(directory.toPath(), true)
        };
    }

    private void printDirectories(File directory, boolean recursively) {
        File[] directories = Objects.requireNonNull(directory.listFiles());
        for (File pathName : directories) {
            if (pathName.isDirectory() && recursively) {
                System.out.println(ANSI_PURPLE + pathName.getName() + ANSI_RESET);
                printDirectories(pathName, true);
            } else if (pathName.isFile()) {
                System.out.println(ANSI_YELLOW + pathName.getName() + ANSI_RESET);
            } else {
                System.out.println(ANSI_PURPLE + pathName.getName() + ANSI_RESET);
            }
        }
    }

    private void printDirectoriesDetailed(Path path, boolean hReadable) throws IOException {
        DirectoryStream<Path> files = Files.newDirectoryStream(path);
        long[] spaces = countSpaceNeeded(Files.newDirectoryStream(path), path);
        for (Path p : files) {
            System.out.print(Files.isDirectory(p) ? "d" : "-");
            System.out.print(Files.isReadable(p) ? "r" : "-");
            System.out.print(Files.isWritable(p) ? "w" : "-");
            System.out.print(Files.isExecutable(p) ? "x " : "- ");
            System.out.printf("%-" + spaces[0] + "s ", Files.getOwner(p).getName().substring(Files.getOwner(p).getName().lastIndexOf("\\") + 1));
            if (hReadable) {
                System.out.printf(ANSI_LIGHT_BLUE+"%-" + spaces[1] + "s ", humanReadableByteCount(Files.size(p))+ANSI_RESET);
            } else {
                System.out.printf(ANSI_LIGHT_BLUE+"%-" + spaces[3] + "s ", Files.size(p)+ANSI_RESET);
            }
            System.out.printf("%-" + spaces[2] + "s ", Files.getAttribute(p, "creationTime"));
            if (new File(p.toString()).isFile()) {
                System.out.println(ANSI_YELLOW + p.getFileName() + ANSI_RESET);
            } else {
                System.out.println(ANSI_PURPLE + p.getFileName() + ANSI_RESET);
            }
        }
    }
    private String humanReadableByteCount(long bytes) {
        String[] units = {"B", "kB", "MB", "GB", "TB"};
        int base = 1000;
        if (bytes < base) {
            return bytes + " " + units[0];
        }
        int exponent = (int) (Math.log(bytes) / Math.log(base));
        String unit = units[exponent];
        return String.format("%.1f%s", bytes / Math.pow(base, exponent), unit);
    }

    private long[] countSpaceNeeded(DirectoryStream<Path> files1, Path path) throws IOException {
        long[] spacingArray = {DEFAULT_SPACING, DEFAULT_SPACING,DEFAULT_SPACING, DEFAULT_SPACING};
        for (Path p : files1) {
            if (Files.getOwner(p).getName().substring(Files.getOwner(p).getName().lastIndexOf("\\") + 1).length() > spacingArray[0]) {
                spacingArray[0] = Files.getOwner(p).getName().substring(Files.getOwner(p).getName().lastIndexOf("\\") + 1).length();
            }
            if (String.valueOf(Files.size(p)).length() > spacingArray[1]) {
                spacingArray[1] = String.valueOf(Files.size(p)).length();
            }
            if (String.valueOf(Files.getAttribute(p, "creationTime")).length() > spacingArray[2]) {
                spacingArray[2] = String.valueOf(Files.getAttribute(p, "creationTime")).length();
            }
            if (humanReadableByteCount(Files.size(p)).length() >= spacingArray[3]) {
                spacingArray[3] = humanReadableByteCount(Files.size(p)).length();
            }
        }
        return spacingArray;
    }
}
