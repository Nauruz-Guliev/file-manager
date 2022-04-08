package ru.kpfu.itis.gnt.exceptions;

public class NonExistentCommand extends IllegalArgumentException{
    public NonExistentCommand(String msg) {
        super(msg);
    }
    public NonExistentCommand(){
        super();
    }
}
