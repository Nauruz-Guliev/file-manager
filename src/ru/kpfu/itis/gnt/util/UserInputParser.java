package ru.kpfu.itis.gnt.util;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserInputParser {
    private Path sourcePath;
    private Path targetPath;
    private String command;
    private String userInput;
    private String option;
    private String[] splitedUserInput;
    private static final int AMOUNT_OF_FIELDS = 3;

    public UserInputParser(String userInput) {
        this.userInput = userInput;
        splitedUserInput = new String[AMOUNT_OF_FIELDS];
        parse();
    }

    private void parse() {
        String[] arrayOfUserInput = userInput.split(" ");
        System.arraycopy(arrayOfUserInput, 0, splitedUserInput, 0, arrayOfUserInput.length);
        command = splitedUserInput[0];
        if (splitedUserInput[0] != null && !InputValidator.isValidPath(splitedUserInput[1]) && splitedUserInput[1] != null && InputValidator.countParentLevels(splitedUserInput[1])== 0) {
            option = splitedUserInput[1];
        } else if (InputValidator.isValidPath(splitedUserInput[1]) && InputValidator.isValidPath(splitedUserInput[2])) {
            sourcePath = Paths.get(splitedUserInput[1]);
            targetPath = Paths.get(splitedUserInput[2]);
        } else if (!InputValidator.isValidPath(splitedUserInput[1]) && InputValidator.isValidPath(splitedUserInput[2])) {
            option = splitedUserInput[1];
            targetPath = Paths.get(splitedUserInput[2]);
        } else if (splitedUserInput[2] == null && InputValidator.isValidPath(splitedUserInput[1])) {
            targetPath = Paths.get(splitedUserInput[1]);
        } else if (InputValidator.countParentLevels(splitedUserInput[1]) != 0) {
            option = String.valueOf(InputValidator.countParentLevels(splitedUserInput[1]));
        } else if (splitedUserInput[0] != null && splitedUserInput[1] == null) {
            option = "";
        } else {
            throw new InvalidPathException(userInput, "Given input is not valid.");
        }
    }

    public Path getSourcePath() {
        return sourcePath;
    }

    public Path getTargetPath() {
        return targetPath;
    }

    public String getOption() {
        return option;
    }

    public String getCommand() {
        return command;
    }
}
