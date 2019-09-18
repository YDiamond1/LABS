package usingcollection;


import Server.User;
import Server.db.DB;
import Server.db.DBException;
import classes.Place;
import classes.Salesman;

import java.io.BufferedOutputStream;
import java.security.InvalidParameterException;
import java.sql.*;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

public class SalesmanSet {
    protected ConcurrentSkipListSet<Salesman> set;
    protected Date date = new Date();
    protected BufferedOutputStream out;
    public boolean saving = false;
    private DB db;

    public SalesmanSet(ConcurrentSkipListSet<Salesman> set,DB db)
    {
        try{
            this.set = set;
            this.db = db;
            db.connect("postgres","selles202");
            loadCollection();
        }catch(DBException ex){
            System.out.println(ex.getMessage());
            System.exit(-1);
        }

    }

   /* public Set<User> getUsers() {
        return users;
    }*/

    /**
     * очищает коллекцию
     */
    public String clear(User user){
        set.removeAll(set.stream().filter(c -> c.getUser().equals(user.getNick())).collect(Collectors.toList()));
        Connection con = db.connection;
        try {
            PreparedStatement statement = con.prepareStatement("DELETE FROM public.\"OBJECTS\" WHERE \"user\"=?");
            statement.setString(1,user.getNick());
            statement.executeUpdate();
            statement.close();
        }catch (SQLException ex){
            return ex.getMessage();
        }
        return "Collection is cleared";
    }

    /**
     * выводит все элементы в консоль
     * @return элементы или фразу "Collection is empty"
     */
    public String show(){
        if(set.isEmpty())return "Collection is empty";
        String[] tmp ={""};
        int[] k={1};
        set.forEach(e->{tmp[0]+="elenent"+(k[0]++)+": "+e.toString()+" (User: "+e.getUser()+")\n";});
        return tmp[0];

    }

    /**
     * выводит информацию о коллекции
     * @return String
     */
    public String info(){
        String information = String.format("\ntype: %s\nobjects number: %d\nCreation date: %s\n",set.getClass().getName(),set.size(),date.toString());
        System.out.println(information);
        return information;
    }

    private PreparedStatement set(PreparedStatement statement,Salesman object,boolean a)throws SQLException{
        int c=0;
        statement.setString(++c,object.getName());
        statement.setInt(++c,object.getAge());
        statement.setInt(++c,object.getQuantityOfNewspaper());
        if(a) statement.setString(++c,object.getTimeOFcreation().toString());
        statement.setBoolean(++c,object.getIS());

        statement.setString(++c,object.getUser());
        System.out.println(object.getUser());
        Integer[] ina = {object.getPlace().getX(),object.getPlace().getY()};
        Array ar =db.connection.createArrayOf("INTEGER",ina);
        statement.setArray(++c,ar);
        return statement;
    }
    /**
     * добавляет элемент в коллекцию
     * @param object объект
     */
    public String add(Salesman object){
        boolean a=set.add(object);
        Connection con = db.connection;
        if(a){
            try {
                PreparedStatement statement = con.prepareStatement("INSERT INTO public.\"OBJECTS\" (name, age, \"QON\", \"TOC\", \"isMoveble\", \"user\", place) VALUES(?, ?, ?, ?, ?, ?, ?)");
                set(statement,object,true);
                statement.executeUpdate();
                statement.close();
            }catch (SQLException ex){
                return ex.getMessage();
            }
            return "Object added";
        }
        else return "Didn't add";
    }

    /**
     * добавляет элемент если минимален
     * @param object объект
     */
    public String add_if_min(Salesman object){
        Salesman o =(Salesman) set.stream().min(Salesman::compareTo).get();
        if(object.compareTo(o)<0)
            return add(object);
    return "Didnt add";
    }

    /**
     * добавляет элемент если минимален
     * @param object объект
     */
    public String add_if_max(Salesman object){
        Salesman o =(Salesman) set.stream().max(Salesman::compareTo).get();
        if(object.compareTo(o)>0)
            return add(object);
        return "Didnt add";
    }

    /**
     * удаляет элемент по значнию
     * @param object объкт
     * @throws InvalidParameterException если не найден объект
     */
    public String remove(Salesman object)throws InvalidParameterException{
        boolean remove = set.remove(object);
        String deleting = "DELETE FROM OBJECTS WHERE  name=? AND age=? AND QOC=? AND TOC=? AND isMoveble=? AND user=? AND place=?";
        if(remove){
            try {
                Connection con = db.connection;
                PreparedStatement statement = con.prepareStatement("DELETE FROM public.\"OBJECTS\" WHERE name=? AND age=? AND \"QON\" =? AND \"isMoveble\"=? AND \"user\"=? AND place=?");
                set(statement,object,false);
                statement.executeUpdate();
                statement.close();
            }catch (SQLException ex){
                return ex.getMessage();
            }
            return "Object was removed";
        }
        return "Object isn't in collection";
    }

    //конструктор
  /*  public SalesmanSet(ConcurrentSkipListSet<Salesman> _set,List<CSVObject> list) throws InvalidParameterException{
        set = _set;
//        importing(list);
    }*/

    public SalesmanSet(ConcurrentSkipListSet<Salesman> set){
        this.set = set;
    }



    /**
     * переводит в CSV формат
     * @return строку в CSV формате
     */
    public String toCSV(){
        String[] tmp = {""};
        set.stream().forEach(e->tmp[0]+=e.toCSV()+"\n");
        return tmp[0];
    }
    public void loadCollection(){

        Connection con = db.connection;

        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM public.\"OBJECTS\"");
            ResultSet objects = statement.executeQuery();
            while (objects.next()){
                set.add(new Salesman(
                        objects.getString("name"),
                        objects.getInt("age"),
                        new Place((Integer[]) objects.getArray("place").getArray()),
                        objects.getBoolean("isMoveble"),
                        objects.getInt("QON"),
                        objects.getString("user"),
                        objects.getString("TOC")
                ));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public DB getDb() {
        return db;
    }
    /*
    public String importing(List<CSVObject> list){
        Iterator<CSVObject> it = list.iterator();
        Set<Salesman> seto = new TreeSet<>();
        while(it.hasNext()){
            CSVObject csv = it.next();
            if(csv.length()==6) {
                final boolean add = seto.add(new Salesman(csv));
                if(!add) {
                    System.out.println("It didnt add");
                }
            }
            else{
                try {
                    if (CSVParser.fileEmpty) {
                        System.out.println("Empty file");
                    }
                    else{
                        throw new InvalidParameterException("Wrong format of information");}
                }catch (InvalidParameterException ex){
                    System.out.println(ex.getMessage());
                }

            }
        }
        set.addAll(seto);
        return "objects added";
    }*/

}
