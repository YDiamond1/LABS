package classes;

import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Salesman extends Human implements Sellable, Serializable, Comparable<Salesman> {

    protected int quantityOfNewspaper;
    protected LocalDateTime timeOFcreation;
    protected String user;

    public String getUser() {
        return user;
    }

    public boolean getIS(){
        return isMoveble;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private void create(){timeOFcreation = LocalDateTime.now();}

    protected String profession = "Продавец газет";
    public Salesman(String name, int age, Place place, boolean isMoveble, int _quantityOfNewspaper,String user){
        super(name, age,  place, isMoveble);
        create();
        this.thing = new Newspaper();
        this.quantityOfNewspaper = _quantityOfNewspaper;
        this.user = user;
    }
    public Salesman(String name, int age, Place place, boolean isMoveble, int _quantityOfNewspaper,String user,String time){
        super(name, age,  place, isMoveble);
        timeOFcreation = LocalDateTime.parse(time);
        this.thing = new Newspaper();
        this.quantityOfNewspaper = _quantityOfNewspaper;
        this.user = user;
    }
    public Salesman(){
        create();
        this.name = "default";
        this.age = 0;
        this.isMoveble = true;
        this.quantityOfNewspaper=1;
        this.place = new Place();
    }
/*
    public Salesman(CSVObject csv){
        this(
                csv.getField(0),
                Integer.parseInt(csv.getField(1)),
                new Place(Integer.parseInt(csv.getField(2)),Integer.parseInt(csv.getField(3))),
                Boolean.parseBoolean(csv.getField(4)),
                Integer.parseInt(csv.getField(5))
        );
    }*/

    public Salesman(JSONObject object){
            this();
            for(String str: object.keySet()){
                switch(str){
                    case "name":{
                        name = object.getString("name");
                        break;
                    }
                    case "age":{
                        age = object.getInt("age");
                        break;
                    }
                    case "isMoveble":{
                        isMoveble = object.getBoolean("isMoveble");
                        break;
                    }
                    case "quantityOfNewspaper":{
                        quantityOfNewspaper = object.getInt("quantityOfNewspaper");
                        break;
                    }
                    case "place":{
                        place = new Place(object.getJSONObject("place"));
                        break;
                    }
                }
            }
            System.out.println("Создался продавец газет\n   "+name+" c возврастом "+age);
    }
    public void sell(Human human) {
        if(human.getMoney()>=2){
            this.quantityOfNewspaper--;
            System.out.println(name + " продал газету "+human.getName());
            human.setMoney(2);
            human.setThing(thing);
        }
        else throw new NotEnoughMoneyException();
    }

    @Override
    public int compareTo(Salesman o) {
        if((place.getX()-o.place.getX())>0 || (place.getX()-o.place.getX())<0 )return place.getX()-o.place.getX();
        else if((place.getY()-o.place.getY())>0 || (place.getY()-o.place.getY())<0) return place.getY()-o.place.getY();
        else if(name.compareTo(o.getName())>0 || name.compareTo(o.getName())<0) return name.compareTo(o.getName());
        else if( (age-o.getAge())>0 || (age-o.getAge())<0) return age-o.getAge();
        else if((quantityOfNewspaper-o.quantityOfNewspaper)>0 || (quantityOfNewspaper-o.quantityOfNewspaper)<0)return quantityOfNewspaper-o.quantityOfNewspaper;
        else if(user.compareTo(o.getUser())!=0)return user.compareTo(o.getUser());
        else{
            int is1,is2;
            if(isMoveble)is1=1;
            else is1=0;
            if(o.isMoveble)is2=1;
            else is2=0;
            return is1-is2;
        }

    }

    public static class Newspaper extends Thing{
        protected String name;

        public String getName(){
            return name;
        }

        public void getKnoweledges(){
            class Event{
                private String event;

                public String getEvent() {
                    return event;
                }

                public Event(String event) {
                    this.event = event;
                }
            }
            Event tmp = new Event("общество гигантских растений распалось");
            System.out.println(tmp.getEvent());
        }

        public Newspaper(){this.name = "газета";}
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Salesman salesman = (Salesman) o;
        return quantityOfNewspaper == salesman.quantityOfNewspaper &&
                name.equals(salesman.name) && age==salesman.age && isMoveble==salesman.isMoveble&&
                place.equals(salesman.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), quantityOfNewspaper);
    }

    @Override
    public String toString() {
        return "Salesman{" +
                "quantityOfNewspaper=" + quantityOfNewspaper +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", place=" + place +
                ", isMoveble=" + isMoveble +
                '}';
    }

    public String toStringFORadd() {
        return String.format(" '%s', %d , %d , '%s' , %b, '%s' , array[%s,%s] ", name, age, quantityOfNewspaper, timeOFcreation.toString(), isMoveble,user,String.valueOf(place.getX()),String.valueOf(place.getY()));
    }

    public int getQuantityOfNewspaper() {
        return quantityOfNewspaper;
    }

    public LocalDateTime getTimeOFcreation() {
        return timeOFcreation;
    }

    public String toStringFORdelete(){
        return String.format(" name='%s' AND age=%d AND QOC=%d AND TOC='%s' AND isMoveble=%b AND user='%s' AND place=array[%s,%s] ", name, age, quantityOfNewspaper, timeOFcreation.toString(), isMoveble,user,String.valueOf(place.getX()),String.valueOf(place.getY()));
    }
    public String toCSV(){
        return "\""+name+"\";\""+age+"\";\""+place+"\";\""+isMoveble+"\";\""+quantityOfNewspaper+"\";";
    }
}
