package classes;

import org.json.JSONObject;

import java.util.Objects;

public  class Place {
    protected int x;
    protected int y;



    public Place(){
        x = 0;
        y = 0;
    }

    public Place(int _x, int _y){
        x = _x;
        y = _y;
    }
    public Place(JSONObject ob) {
        this();
        for(String str : ob.keySet()){
            switch(str){
                case "x":{
                    x = ob.getInt("x");
                    break;
                }
                case "y":{y = ob.getInt(str);break;}
            }
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return x == place.x &&
                y == place.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Place{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
