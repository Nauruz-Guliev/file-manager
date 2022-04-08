package ru.kpfu.itis.gnt.commands;
import ru.kpfu.itis.gnt.exceptions.CopyException;
import ru.kpfu.itis.gnt.util.CurrentPath;

import java.io.*;
import java.nio.file.Path;
import java.util.Objects;

public class CopyDirectoryCommand implements Command {
    public static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001b[0m";
    @Override
    public void execute(CurrentPath cPath, Path sourcePath, Path targetPath, String option, String[] availableOptions) {
        copyDirectory(new File((sourcePath == null ? cPath : sourcePath).toString()),
                new File(targetPath.toString()));
    }

    private static void copyDirectory(File sourcePath, File targetPath) {
        if (!targetPath.exists()) {
            targetPath.mkdir();
        }
        for (String f : Objects.requireNonNull(sourcePath.list())) {
            File source = new File(sourcePath, f);
            File target = new File(targetPath, f);
            if (source.isDirectory()) {
                copyDirectory(source, target);
            } else {
                copyFile(source, target);
            }
        }
    }

    private static void copyFile(File sourcePath, File targetPath) {
        try (FileInputStream input = new FileInputStream(sourcePath);
             FileOutputStream output = new FileOutputStream(targetPath)) {

            int bytesRead;
            while ((bytesRead = input.read()) > 0) {
                output.write(bytesRead);
            }
        } catch (FileNotFoundException e) {
            System.out.println(ANSI_RED + "Unable to copy file: " + sourcePath+ANSI_RESET);
        } catch (IOException e) {
            System.out.println(ANSI_RED +"Something went wrong copying file " + sourcePath + " to " + targetPath +ANSI_RESET);
        }
    }
}
