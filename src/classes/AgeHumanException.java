package classes;
public class AgeHumanException extends RuntimeException {
    public AgeHumanException(){
        super();
        this.message = "Не вырос еще!!!";
    }

    public AgeHumanException(String message){
        super(message);
    }
    protected String message;
    public String getMessage(){
        return message;
    }
}
