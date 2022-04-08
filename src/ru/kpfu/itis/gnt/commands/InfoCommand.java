package ru.kpfu.itis.gnt.commands;

import ru.kpfu.itis.gnt.util.CurrentPath;

import java.nio.file.Path;

public class InfoCommand implements Command {
    private static final String ANSI_BLACK = "\u001b[30m";
    private static final String ANSI_GREEN = "\u001b[32m";
    private static final String ANSI_MAGENTA = "\u001b[35m";
    private static final String ANSI_BACKGROUND_GREEN = "\u001b[42m";
    private static final String ANSI_RESET = "\u001b[0m";
    private static final String ANSI_BACKGROUND_YELLOW = "\u001b[43;1m";

    @Override
    public void execute(CurrentPath cPath, Path sourcePath, Path targetPath, String option, String[] availableOptions) {
        System.out.print(ANSI_BACKGROUND_GREEN + ANSI_BLACK + "This is a simple file manager with 4 main features: " + "\n" +ANSI_RESET);
        System.out.println(ANSI_MAGENTA + " * Changing directory.");
        System.out.println(ANSI_MAGENTA + " * Copying files and directories.");
        System.out.println(ANSI_MAGENTA + " * Deleting files. Deleting files and directories recursively.");
        System.out.println(ANSI_MAGENTA + " * Listing information about a directory.");
        System.out.print(ANSI_BACKGROUND_YELLOW +ANSI_BLACK+"Following commands are available for a user: "+ "\n" + ANSI_RESET);
        System.out.println("\t"+ ANSI_GREEN + "cd \\*desired_directory*\\      " + ANSI_MAGENTA + "Changes directory to desired.");
        System.out.println("\t"+ ANSI_GREEN + "cd ..\\..\\..                   " + ANSI_MAGENTA + "Go back in directories certain amount of times.");
        System.out.println("\t"+ ANSI_GREEN + "cp \\*source*\\ \\*destiny*\\     " + ANSI_MAGENTA + "Copying files or directories from source to destiny.");
        System.out.println("\t"+ ANSI_GREEN + "rm \\*target*\\                 " + ANSI_MAGENTA + "Removing files or directories. Notice that directories with files and subdirectories will be deleted recursively.");
        System.out.println("\t"+ ANSI_GREEN + "ls                            " + ANSI_MAGENTA + "Listing all the files of current directory.");
        System.out.println("\t"+ ANSI_GREEN + "ls -R                         " + ANSI_MAGENTA + "Listing all the files of current directory recursively.");
        System.out.println("\t"+ ANSI_GREEN + "ls -la                        " + ANSI_MAGENTA + "Listing all the files of current directory with detailed information.");
        System.out.println("\t"+ ANSI_GREEN + "ls -lh                        " + ANSI_MAGENTA + "Listing all the files of current directory with detailed information and human-readable."+ANSI_RESET);
    }
}
