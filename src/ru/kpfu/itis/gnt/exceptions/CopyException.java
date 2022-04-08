package ru.kpfu.itis.gnt.exceptions;

import java.io.IOException;

public class CopyException extends IOException {
    public CopyException(String msg) {
        super(msg);
    }
    public CopyException (){
        super();
    }
}
