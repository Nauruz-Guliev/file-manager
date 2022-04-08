package ru.kpfu.itis.gnt.commands;

import ru.kpfu.itis.gnt.util.CurrentPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class DeleteDirectoryCommand implements Command {
    public static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001b[0m";

    @Override
    public void execute(CurrentPath cPath, Path sourcePath, Path targetPath, String option, String[] availableOptions) {
        deleteDirectory(new File(targetPath.toString()));
    }

    private void deleteDirectory(File directoryToBeDeleted) {
        try {
            File[] allContents = directoryToBeDeleted.listFiles();
            if (allContents != null) {
                for (File file : allContents) {
                    deleteDirectory(file);
                }
            }
            if (directoryToBeDeleted.exists()) {
                directoryToBeDeleted.delete();
            } else {
                throw new InvalidPathException( directoryToBeDeleted.toString(), "Such a Path does not exist ");
            }
            //since I don't know what exception can be thrown here and at what circumstances I'm catching all the possible exceptions
        } catch (InvalidPathException ex) {
            System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
        } catch (Exception ex) {
            System.out.println(ANSI_RED + "Something went wrong deleting files with message: " + ex.getMessage() + ANSI_RESET);
        }
    }
}
