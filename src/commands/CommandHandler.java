package commands;

import Server.User;
import classes.Salesman;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;


public class CommandHandler  {
    protected String command;
    protected JSONObject JSONObjectSource;
    protected BufferedReader in;
    protected BufferedOutputStream out;
    protected int tmpchar;
    protected boolean finish=false;
    protected Command cmd = null;
    protected boolean UnknownCMD = false;
    protected User user=null;

    public CommandHandler(BufferedReader in, BufferedOutputStream out) {
        this.in = in;
        this.out = out;
    }

    public Command getCmd() {
        if (!UnknownCMD)
        return cmd;
        else return new Command("Unknown command");
    }

    public void readCommand() throws IOException {
        System.out.print(">>>");
        int c = skipSpaces();
        command = "";
        while (c != '{' && c != '\n' && c != ' ' && c != '\r' && c != -1) {
            command += (char) c;
            c = in.read();
        }

        tmpchar = c;
        try {
            callFunctions();
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        if (tmpchar == -1)
            System.exit(-1);
    }

    public void readJSONObject()throws IOException,JSONException{
        int skobochiki=0,ch;
        String JSON="";
        if(tmpchar==' ' || tmpchar=='\n'||tmpchar=='\r' || tmpchar=='\t') {
            ch = skipSpaces();
        }
        else
            ch=tmpchar;
        for(;;){

            if (ch == '{') {
                skobochiki++;
            }
            else if(ch=='}')skobochiki--;
            else if(ch == -1){
                throw new JSONException("НЕВЕРНЫЙ ФОРМАТ JSONObject");
            }

            JSON+=(char)ch;
            ch = in.read();
            if(skobochiki==0){
                JSONObjectSource = new JSONObject(JSON);
                return;
            }
        }
    }

    public void help(){
        System.out.println(infohelp+clearhelp+showhelp+add_if_maxHelpInfo+add_if_minHelpInfo+addHelpInfo+removeHelpInfo+loginhelp+signinhelp+logout);
        System.out.println("ключи объекта: name,age,isMoveble, place{x , y},quantityOfNewspaper");
    }

    public int skipSpaces() throws IOException{
        int c = in.read();

        while (c==' ' || c=='\t' || c=='\n' || c=='\r')
            c = in.read();
        return c;
    }

    public void callFunctions() throws IOException, JSONException{
        if(command.equals("help")|| command.equals("info")||command.equals("show")|| command.equals("clear")||command.equals("logout")){
            cmd = new Command(command, new Salesman());
            UnknownCMD=false;
        }
        else if(command.equals("add")||command.equals("add_if_min")||command.equals("add_if_max")|| command.equals("remove")){
            readJSONObject();
            cmd = new Command(command,new Salesman(JSONObjectSource));
            UnknownCMD = false;
        }
        else if(command.equals("login")|| command.equals("signin")){
            cmd=new Command(command,new Salesman());
            String[] parameters = readTWOparametrs();
            System.out.println(parameters[0]+" "+parameters[1]);
            cmd.setParameters(parameters);
            UnknownCMD = false;
        }
        else{
            UnknownCMD =true;}
    }

    public static Command readResponse(ObjectInputStream ois)throws IOException,ClassNotFoundException{
       // ObjectInputStream ois = new ObjectInputStream(sc.getInputStream());
        Command cmd =(Command) ois.readObject();
        return cmd;
    }
    public String[] readTWOparametrs() throws IOException{
        switch (command){
            case "login":{
                String login, pass;
                System.out.print("login:");
                login = readENTER();
                System.out.print("pass:");
                pass = readENTER();
                String[] mas = {login,pass};
                return mas;
            }
            case "signin":{
                String login, email;
                System.out.print("login:");
                login = readENTER();

                ///
                System.out.print("email:");
                email = readENTER();
                String[] mas = {login, email};
                return mas;
            }
        }
        return null;
    }
    public String readENTER() throws IOException{
        String tmp="";

        /*int c=skipSpaces();
        while (c != '\n') {
            tmp += (char) c;
            c = in.read();
        }*/
        Scanner scanner = new Scanner(System.in);
        tmp = scanner.nextLine();
        tmp.replace(" ","");
        return tmp;
    }
    public static String infohelp = "info : выводит информацию о коллекции\n";
    public static String clearhelp = "clear : очищаетколлекцию\n";
    public static String showhelp = "show : выводит элементы\n";
    public static String add_if_minHelpInfo = "add_if_min: добавляют если элемент минимальный\n";
    public static String addHelpInfo ="add: добавляет объкт в коллекцию\n";
    public static String add_if_maxHelpInfo ="add_if_max: добавляет элемент если максимален\n";
    public static String removeHelpInfo ="remove: удаляет объект\n";
    public static String loginhelp = "login: вход в акк\n";
    public static String signinhelp = "signin: регистрация\n";
    public static String logout = "logout: выход c акк";
}
