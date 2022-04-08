package ru.kpfu.itis.gnt.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    private static final String regexPath = "([A-Z]?:?[\\\\]?[а-яА-Яa-zA-Z&$;*-]+[\\\\]?.?[a-zA-Z]+[\\\\]?){2,}";
    private static final String regexParentDirectory = "([\\.]{2}[\\\\]?)";

    public static boolean isValidPath(String pathGiven) {
        if (pathGiven == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regexPath);
        Matcher matcher = pattern.matcher(pathGiven);
        return matcher.find();
    }
    public static int countParentLevels(String parentDir) {
        if (parentDir == null) {
            return 0;
        }
        Pattern pattern = Pattern.compile(regexParentDirectory);
        Matcher matcher = pattern.matcher(parentDir);
        int counter = 0;
        while (matcher.find()){
            counter++;
        }
        return counter;
    }
}
