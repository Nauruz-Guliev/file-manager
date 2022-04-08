package ru.kpfu.itis.gnt.exceptions;

public class NonExistentOption extends IllegalArgumentException{
    public NonExistentOption(String msg) {
        super(msg);
    }
    public NonExistentOption(){
        super();
    }
}
