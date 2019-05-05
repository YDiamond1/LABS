package parsers.CSV;

import java.util.List;
import java.util.ArrayList;

public class CSVObject {
    protected List<String> list;

    public CSVObject(){
        list = new ArrayList<String>(16);
    }
    public CSVObject(int size){
        list = new ArrayList<>(size);
    }


    public void setField(String str){
        list.add(str);
    }
    public void printList(){
        int i=0;
        for( Object c:list.toArray()){
            System.out.print((i++) +") " + (String)c+" ");
        }
    }
    public String getField(int i){
        return list.get(i);
    }
    public Object[] toArray(){
        return list.toArray();
    }
    public int length(){
        return list.size();
    }
}
