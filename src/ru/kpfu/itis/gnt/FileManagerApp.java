package ru.kpfu.itis.gnt;

import ru.kpfu.itis.gnt.commands.*;
import ru.kpfu.itis.gnt.exceptions.NonExistentCommand;
import ru.kpfu.itis.gnt.util.CurrentPath;
import ru.kpfu.itis.gnt.util.UserInputParser;

import java.util.Scanner;

public class FileManagerApp extends Application {
    private String[] commandNames;
    private String[] optionNames;
    private CurrentPath cPath;
    private Command[] commands;
    private Scanner sc;
    private UserInputParser parser;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_WHITE = "\u001B[39m";

    @Override
    public void init() {
        cPath = new CurrentPath(System.getProperty("user.dir"));
        sc = new Scanner(System.in);
        optionNames = new String[]{"", "-R", "-la", "-lh"};
        commandNames = new String[]{"cd", "cp", "ls", "rm", "info"};
        commands = new Command[]{
                new ChangeDirectoryCommand(),
                new CopyDirectoryCommand(),
                new InfoDirectoryCommand(),
                new DeleteDirectoryCommand(),
                new InfoCommand()
        };
    }

    @Override
    public void start() {
        try {
            String userCommand;
            while (true) {
                System.out.print(cPath + " >");
                sc = new Scanner(System.in).useDelimiter("\n");
                userCommand = sc.next();
                parser = new UserInputParser(userCommand);
                int commandIndex = 0;
                boolean found = false;
                for (String cmdName : commandNames) {
                    if (parser.getCommand().equals(cmdName)) {
                        commands[commandIndex].execute(cPath, parser.getSourcePath(), parser.getTargetPath(), parser.getOption(), optionNames);
                        found = true;
                        break;
                    }
                    commandIndex++;
                }
                if (!found) {
                    throw new NonExistentCommand("Command not found");
                }
            }
        } catch (NonExistentCommand ex) {
            System.out.println(ANSI_RED + ex.getMessage() + ANSI_WHITE);
        }
    }
}
