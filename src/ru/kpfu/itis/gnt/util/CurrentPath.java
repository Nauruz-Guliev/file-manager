package ru.kpfu.itis.gnt.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CurrentPath {

    private Path currPath;
    public CurrentPath(String path) {
        currPath = Paths.get(path);
    }

    public CurrentPath(Path path) {
        currPath = path;
    }

    public Path getCurrPath() {
        return currPath;
    }

    public void setCurrPath(Path currPath) {
        this.currPath = currPath;
    }

    @Override
    public String toString() {
        return currPath.toString();
    }
}
