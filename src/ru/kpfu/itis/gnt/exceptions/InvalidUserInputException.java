package ru.kpfu.itis.gnt.exceptions;

public class InvalidUserInputException extends IllegalArgumentException{
    public InvalidUserInputException(String msg) {
        super(msg);
    }
    public InvalidUserInputException (){
        super();
    }
}
