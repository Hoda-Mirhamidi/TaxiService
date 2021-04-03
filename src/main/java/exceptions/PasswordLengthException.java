package exceptions;

public class PasswordLengthException extends Exception{
    public PasswordLengthException(String msg){
        super(msg);
    }
}
