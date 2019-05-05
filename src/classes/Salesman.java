package classes;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.*;
import java.util.Objects;

public class Salesman extends Human implements Sellable, Serializable {

    protected int quantityOfNewspaper;
    protected String profession = "Продавец газет";
    public Salesman(String name, int age, Place place, boolean isMoveble, int _quantityOfNewspaper){
        super(name, age,  place, isMoveble);
        this.thing = new Newspaper();
        this.quantityOfNewspaper = _quantityOfNewspaper;
    }
    public Salesman(){
        this.name = "default";
        this.age = 0;
        this.isMoveble = true;
        this.quantityOfNewspaper=1;
        this.place = new Place();
    }

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
        return Objects.hash(super.hashCode(), quantityOfNewspaper, profession);
    }

    @Override
    public String toString() {
        return "{" +
                "quantityOfNewspaper=" + quantityOfNewspaper +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", place=\"" + place +'\"'+
                ", isMoveble=" + isMoveble +
                '}';
    }
    public String toCSV(){
        return "\""+name+"\";\""+age+"\";\""+place.getX()+"\";\""+place.getY()+"\";\""+isMoveble+"\";\""+quantityOfNewspaper+"\";";
    }
}
