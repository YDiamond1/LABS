package parsers.CSV;

import parsers.Command;
import parsers.Parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;




public class CSVParser implements Parser, CSVAware {
    public InputStream in;
    public char separator;
    public final static int EOF = -1;
    public List<CSVObject> objects = new ArrayList<CSVObject>(16);
    public static boolean fileEmpty = true;
    {
        in = null;
        separator = ';';
    }
    public CSVParser(){
        in = null;
        separator = ';';
    }
    public CSVParser(InputStream _in)throws CSVException{in =_in;parse();}
    public CSVParser(InputStream _in, char _separator){
        in = _in;
        separator = _separator;
    }
    public List parse() throws CSVException{
        if(in == null){
            throw new CSVException("НЕ ОТКУДА СЧИТЫВАТЬ");
        }
        return parse(in,"CP1251");
    }

    public List parse(InputStream in,String charsetName){
        int tmpint;

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try {

            while ((tmpint = in.read()) != EOF ) {
                buf.write(tmpint);
                fileEmpty = false;
            }

        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        String tmp = "";
        try {
             tmp = new String(buf.toByteArray(), charsetName);
        }catch (UnsupportedEncodingException ex){
            System.out.println(ex.getMessage());
        }
        String[] array = tmp.split("\n");
        tmp = "";
        for(String c : array){
            CSVObject csv = new CSVObject();
            for(int i=0;i<c.length();i++){
                int k = c.charAt(i);
                if(k == '\"'){
                    i++;
                    int begin = i,end;
                    try {
                        while (c.charAt(i) != '\"') {
                            i++;
                            if (i >= c.length()) throw new CSVException("НЕ НАЙДЕН СИМВОЛ ' \" ' В СТРОЧКЕ " + c);
                        }
                    }catch (CSVException ex) {
                        System.out.println(ex.getMessage());
                        Command.wasEdit=true;
                    }
                    end = i;
                    tmp=c.substring(begin,end);
                }
                else if(k == separator){
                    csv.setField(tmp);
                    tmp = "";
                }
                else if((k>47 && k<58) || (k>64 &&k<91) || (k>96 && k<123) || (k>127 && k<176) || (k>223 && k<242) ){
                    tmp+=c.charAt(i);
                }

            }
            objects.add(csv);
        }
    return objects;
    }
    public void printObjects(){
        for(Object a: objects.toArray()){
            ((CSVObject)a).printList();
            System.out.println();
        }
    }
    public List<CSVObject> getObjects(){
        return objects;
    }
    public String toCSVString(){
        String str = "";
        for(int i=0;i<objects.size();i++){
            CSVObject csvObject = objects.get(i);
            for(int j=0;j<csvObject.length();j++){
                str+="\""+csvObject.getField(j)+"\";";
            }
            str+='\n';
        }
        return str;
    }
}
