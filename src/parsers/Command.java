package parsers;




import classes.Salesman;
import org.json.JSONException;
import org.json.JSONObject;
import usingcollection.SalesmanSet;

import java.io.*;
import java.util.Scanner;


public class Command {
    protected JSONObject object;
    protected String command;
    protected JSONObject JSONObjectSource;
    protected SalesmanSet setBySale;
    protected BufferedReader in;
    protected BufferedOutputStream out;
    protected String charset;
    protected int tmpchar;
    protected boolean finish=false;
    public static  boolean saving=false;
    public static boolean wasEdit=false;

    public Command (BufferedReader _in, SalesmanSet _set,String charsetName)
    {in = _in;setBySale = _set;charset = charsetName;}

    public void readCommand() throws IOException {
        if(!finish)System.out.print(">>>");
        int c =skipSpaces();
        command = "";
        while(c!='{' && c!='\n' && c!=' ' && c!='\r' && c!=-1 ) {
            command+=(char)c;
            c = in.read();
        }
        if(c==-1){
            if(!saving){
                finish=true;;
                saving = true;
            }

            return;
        }
        tmpchar=c;
        try {
            callFunctions();
        }catch(JSONException ex){
            System.out.println(ex.getMessage());
        }

        readCommand();
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
        System.out.println(infohelp+clearhelp+showhelp+add_if_maxHelpInfo+add_if_minHelpInfo+addHelpInfo+removeHelpInfo);
        System.out.println("ключи объекта: name,age,isMoveble, place{name,street,city},quantityOfNewspaper");
    }
    public int skipSpaces() throws IOException{
        int c = in.read();
        while (c==' ' || c=='\t' || c=='\n')
            c = (char)in.read();
        return c;
    }

    public void callFunctions() throws IOException, JSONException{
        switch (command){
            case "show": {
                System.out.println(setBySale.show());
                break;
            }
            case "info":{
                System.out.println(setBySale.info());
                break;
            }
            case "clear":{
                setBySale.clear();

                break;
            }
            case "help":{
                help();
                break;
            }
            case "add": {
                readJSONObject();
                wasEdit=setBySale.add(new Salesman(JSONObjectSource));

                break;
            }
            case "add_if_min": {
                readJSONObject();
                wasEdit=setBySale.add_if_min(new Salesman(JSONObjectSource));

                break;
            }
            case "add_if_max": {
                readJSONObject();
                wasEdit=setBySale.add_if_max(new Salesman(JSONObjectSource));

                break;
            }
            case "remove": {
                readJSONObject();
                wasEdit = setBySale.remove(new Salesman(JSONObjectSource));

                break;
            }
            default: {
                System.out.println("НЕИЗВЕСТНАЯ КОМАНДА");
            }
        }
    }






    public static String infohelp = "info : выводит информацию о коллекции\n";
    public static String clearhelp = "clear : очищаетколлекцию\n";
    public static String showhelp = "show : выводит элементы\n";
    public static String add_if_minHelpInfo = "add_if_min: добавляют если элемент минимальный\n";
    public static String addHelpInfo ="add: добавляет объкт в коллекцию\n";
    public static String add_if_maxHelpInfo ="add_if_max: добавляет элемент если максимален\n";
    public static String removeHelpInfo ="remove: удаляет объект\n";
}
