package exceptions;

public class BlankFieldException extends Exception {
    private  String messageException;

    public BlankFieldException(String messageException){
        super(messageException);
    }
}
