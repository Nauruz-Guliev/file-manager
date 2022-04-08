package ru.kpfu.itis.gnt.commands;

import ru.kpfu.itis.gnt.util.CurrentPath;

import java.nio.file.Path;

public interface Command {
    void execute(CurrentPath cPath, Path sourcePath, Path targetPath, String option, String[] availableOptions) ;
}
