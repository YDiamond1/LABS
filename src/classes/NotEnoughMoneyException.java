package classes;
public class NotEnoughMoneyException extends RuntimeException {
     public String message;
     public NotEnoughMoneyException(){
         super();
         this.message = " Недостаточно денег";
     }
     public String getMessage(){
         return message;
     }
}
