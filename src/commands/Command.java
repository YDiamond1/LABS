package commands;


import Server.User;
import classes.Salesman;

import java.io.Serializable;


public class Command implements Serializable {

    protected String command;
    protected Salesman man;
    protected String firstParametr;
    protected String secondParametr;
    protected User user=null;

    public User getUser() {
        return user;
    }

    public Command setUser(User user) {
        this.user = user;
        return this;
    }

    public String getFirstParametr() {
        return firstParametr;
    }

    public String getSecondParametr() {
        return secondParametr;
    }
    public void setParameters(String[] array){
        firstParametr = array[0];
        secondParametr = array[1];
    }

    public Command(String cmd){
        this(cmd,new Salesman());
    }

    public Command(String command, String firstParametr, String secondParametr) {
       this(command,new Salesman(),firstParametr,secondParametr);
    }

    public Command(String cmd, Salesman json){
        command = cmd;
        man = json;

    }

    public Command(String command, Salesman man, String firstParametr, String secondParametr) {
        this.command = command;
        this.man = man;
        this.firstParametr = firstParametr;
        this.secondParametr = secondParametr;
    }

    public Salesman getMan() {
        return man;
    }



    @Override
    public String toString() {
        return command;
    }
}
