package ru.kpfu.itis.gnt.commands;

import ru.kpfu.itis.gnt.util.CurrentPath;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChangeDirectoryCommand implements Command {
    public static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001b[0m";

    @Override
    public void execute(CurrentPath cPath, Path sourcePath, Path targetPath, String option, String[] availableOptions) {
        if (option != null && !option.equals("")) {
            goToPreviousDirectories(cPath, option);
        }
        if (targetPath != null) {
            changeCurrentDirectory(cPath, targetPath);
        }
    }

    private void goToPreviousDirectories(CurrentPath cPath, String option) {
        try {
            int amountOfLevels = Integer.parseInt(option);
            int finalPathLength = cPath.toString().length();
            int counter = 0;
            for (int i = cPath.toString().length() - 1; i >= 0; i--) {
                if (counter == amountOfLevels) {
                    break;
                }
                if (cPath.toString().charAt(i) == '\\') {
                    counter++;
                }
                finalPathLength--;

            }
            cPath.setCurrPath(Paths.get(cPath.toString().substring(0, finalPathLength)));
            if (finalPathLength <= 3) {
                throw new InvalidPathException(cPath.toString(), "Unable to go that far. Try using an absolute path.");
            }
        } catch (InvalidPathException ex) {
            cPath.setCurrPath(Paths.get("C:\\"));
            System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
        } catch (NumberFormatException ex) {
            System.out.println(ANSI_RED +"Wrong directory "+ ex.getMessage() + ANSI_RESET);
        }
    }

    private void changeCurrentDirectory(CurrentPath cPath, Path targetPath) {
        try {
            File directory;
            if (!targetPath.isAbsolute()) {
                directory = (targetPath.toString().charAt(0) != '\\') ? new File(cPath.toString() + '\\' + targetPath) : new File(cPath.toString() + targetPath);
            } else {
                directory = new File(targetPath.toString());
            }
            if (directory.exists()) {
                cPath.setCurrPath(directory.toPath());
            }

            if (!directory.exists()) {
                throw new InvalidPathException(targetPath.toString(), "Such a directory does not exist");
            }
        } catch (InvalidPathException ex) {
            System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
        }
    }
}
