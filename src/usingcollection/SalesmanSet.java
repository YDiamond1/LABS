package usingcollection;


import classes.Place;
import classes.Salesman;
import parsers.CSV.CSVObject;
import parsers.CSV.CSVParser;
import parsers.Command;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.Comparator;

public class SalesmanSet {
    protected Set<Salesman> set;
    protected Date date = new Date();
    protected List<Salesman> sortedset;
    protected BufferedOutputStream out;



    /**
     * сортирует по умолчанию list
     */
    public void sort(){
        sortedset = new ArrayList<>(set);
        Collections.sort(sortedset, new Comparator<Salesman>() {
            @Override
            public int compare(Salesman o1, Salesman o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        set.clear();
        set.addAll(sortedset);
    }

    /**
     * очищает коллекцию
     */
    public void clear(){
        set.clear();
    }

    /**
     * выводит все элементы в консоль
     * @return элементы или фразу "Collection is empty"
     */
    public String show(){
        if(set.isEmpty())return "Collection is empty";
        String tmp = "";
        Iterator<Salesman> it = set.iterator();
        int counter=0;
        tmp+="element" + counter+ ":"+it.next().toString()+'\n';
        while (it.hasNext()){
            tmp+="element" +(++counter)+ ":"+it.next().toString()+'\n';
        }
       // tmp+="element"+counter+":"+ it.next().toString();
        return tmp;
    }

    /**
     * выводит информацию о коллекции
     * @return String
     */
    public String info(){
        String information = String.format("\ntype: %s\nobjects number: %d\nCreation date: %s\n",set.getClass().getName(),set.size(),date.toString());
        return information;
    }

    /**
     * добавляет элемент в коллекцию
     * @param object объект
     */
    public boolean add(Salesman object){
        boolean a=set.add(object);
        sort();
        if(a)System.out.println("Объект добавился");
        else System.out.println("Объект не добавился");
        return a;
    }

    /**
     * добавляет элемент если минимален
     * @param object объект
     */
    public boolean add_if_min(Salesman object){
        sort();
        if(object.getName().compareTo(sortedset.get(0).getName())<0)
            return add(object);
        System.out.println("Объект не добавился");
        return false;
    }

    /**
     * добавляет элемент если минимален
     * @param object объект
     */
    public boolean add_if_max(Salesman object){
        sort();
        if(object.getName().compareTo(sortedset.get(sortedset.size()-1).getName())>0)
            return add(object);
        System.out.println("Объект не добавился");
        return false;
    }

    /**
     * удаляет элемент по значнию
     * @param object объкт
     * @throws InvalidParameterException если не найден объект
     */
    public boolean remove(Salesman object)throws InvalidParameterException{
        Iterator<Salesman> it = set.iterator();
        while(it.hasNext()){
            Salesman tmp = it.next();
            if(tmp.equals(object)) {
                it.remove();

                System.out.println("Объект удалился");

                sort();
                return true;
            }
        }
        if(it.hasNext())
            throw new InvalidParameterException("НЕ СУЩЕСТВУЕТ ТАКОГО ОБЪЕКТА");
        System.out.println("Нет в коллекции такого объекта");
        return false;
    }

    //конструктор
    public SalesmanSet(Set<Salesman> _set,List<CSVObject> list) throws InvalidParameterException{
        set = _set;
        Iterator<CSVObject> it = list.iterator();

        while(it.hasNext()){
            CSVObject csv = it.next();
            if(csv.length()==6) {
                final boolean add = set.add(new Salesman
                        (
                            csv.getField(0),
                            Integer.parseInt(csv.getField(1)),
                            new Place(Integer.parseInt(csv.getField(2)),Integer.parseInt(csv.getField(3))),
                            Boolean.parseBoolean(csv.getField(4)),
                            Integer.parseInt(csv.getField(5))
                        )
                );
                    if(!add) {
                        System.out.println("НЕВОЗМОЖНО ДОБАВИТЬ");
                        System.exit(-1);
                    }

            }
            else{
                    try {
                        if (CSVParser.fileEmpty) {
                            System.out.println("Пустой файл");
                        }
                        else{
                        throw new InvalidParameterException("НЕВЕРНЫЙ ФОРМАТ ДАННЫХ");}
                    }catch (InvalidParameterException ex){
                        System.out.println(ex.getMessage());
                    }

                }
        }

    }



    /**
     * сохряняет коллекцию
     * @param wasEdit изменена ли коллекция
     * @param filename путь к файлу
     */
    public void save(boolean wasEdit,String filename){
        if(wasEdit) {
            try (BufferedOutputStream out =new BufferedOutputStream(new FileOutputStream(filename,false))) {
                out.write(toCSV().getBytes("CP1251"));
                Command.saving =true;
                out.close();
            } catch (IOException ex) {
                System.out.println("НЕТ ДОСТУПА К ФАЙЛУ");
                Command.saving = false;
            }
        }
    }

    /**
     * переводит в CSV формат
     * @return строку в CSV формате
     */
    public String toCSV(){
        String tmp = "";
        if(sortedset!=null && !sortedset.isEmpty()){
           Iterator<Salesman> it = sortedset.iterator();
                   if(it!= null){
                       while (it.hasNext()){
                           tmp+=it.next().toCSV()+"\n";
                       }
                   }
        }
        return tmp;
    }
}
