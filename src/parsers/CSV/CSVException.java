package parsers.CSV;

public class CSVException extends Exception{
    public String message;
    public CSVException(String _message){ message = _message;}
    public String getMessage(){
        return message;
    }
}
