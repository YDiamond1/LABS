package classes;
public enum Time {
    MORNING("утро"),
    DAY("день"),
    EVENING("вечер"),
    NIGHT("ночь");

    public String name;
    Time(String _name){
        name = _name;
    }
    public String getName(){
        return name;
    }
}