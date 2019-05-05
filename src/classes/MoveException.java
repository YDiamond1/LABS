package classes;
public class MoveException extends Exception {
    public MoveException(String message){
        this.message = message;
    }
    protected String message;
    public String getMessage(){

        return message;
    }
}
