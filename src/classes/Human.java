package classes;
import java.util.Objects;

public class Human implements Moveable, Eating,Sleepable{

    public class Mood{
        protected boolean isGood;
        public Mood(){
            if (time == Time.MORNING) isGood = false;
            else isGood = true;
        }

        public void setGood(boolean good) {
            isGood = good;
        }
        public void getMood(){
            if (isGood)System.out.println(" с хорошим нвстроением ");
            else System.out.print(" без настроения ");
        }
    }


    //поля
    protected String name;
    protected int age;
    protected boolean isHungry = true;
    protected boolean isSleep = true;
    protected Place place;
    protected Time time;
    protected boolean isMoveble;
    protected Thing thing;
    protected Santiks money;
    protected Mood mood;

    public int getMoney() {
        return money.quality;
    }
    public void setMoney(int _quality){
        money.quality-= _quality;
    }

    //getters
    public boolean isHungry() {
        System.out.print(name+((isHungry)?" голодный" : " не голодный"));
        return isHungry;
    }

    public boolean isSleep() {
        return isSleep;
    }

    public Place getPlace() {
        return place;
    }

    public Time getTime() {
        return time;
    }

    public String getName(){return name;}

    public int getAge(){return age;}

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    public Thing getThing() {
        return thing;
    }

    //Object methods
    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public boolean equals(Object object){
        if(this == object)return true;
        else if(object instanceof Human){
            if(name.equals(((Human)object).getName()) && age==((Human)object).getAge())return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isHungry, isSleep, place, time);
    }





    //methods
    public void eat(){
        mood.getMood();
        this.isHungry = false;
        System.out.println(this.name+" поел");
    }

    public void see(Object object){
        System.out.print("увидел " + object.toString());
    }

    public void move(Place place)throws MoveException {
        if(isMoveble) {
            this.place = place;
            System.out.print(name + " пошел в " + place.toString());
        }
        else throw  new MoveException("Невозможно пройтись");

    }
    public void say(String message)throws AgeHumanException {
        if(age < 3)throw new AgeHumanException("Маленький для разговора");
        System.out.print(this.toString()+" сказал: "+ message);
    }

    public void yell(String message) throws AgeHumanException{
        if(age < 3)throw new AgeHumanException("Маленький для крика");
        System.out.println(this.toString()+" крикчал: "+ message.toUpperCase()+"!");
    }

    public void wakeUp(Place place){
        this.isSleep = false;
        System.out.println(name + " проснулся в " + place);
    }
    public void wakeUp(){
        this.isSleep = false;
        System.out.print(name + " проснулся");
    }
    public void fellAsleep(){
        this.isSleep = true;
        System.out.println(name+" уснул");
    }
    public void see(Human human){
        System.out.print(name+" увидел "+human.getName()+"\n");
    }
    public void exit(){
        System.out.print(name + " вышел из "+place+'\n');
        place = null;
    }
    public void findInformation(){
        System.out.print(name+" узнал, что ");
        thing.getKnoweledges();
    }





    //конструкторы
    public Human(){System.out.println("");}
    public Human(String _name){name = _name; System.out.println("Создался человечек\n "+ name+"\n");}

    public Human(String name, int age, Place place, boolean isMoveble) {
        this.name = name;
        this.age = age;
        this.place = place;
        this.time = time;
        this.isMoveble = isMoveble;
        money = new Santiks(10);
        mood  = new Mood();
        System.out.println("Создался человечек\n "+ name);
    }
}
