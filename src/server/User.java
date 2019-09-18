package Server;

import java.io.Serializable;
import java.util.Random;

public class User implements Serializable, Comparable<User> {
    private String nick;
    private String email;
    private String pass;

    @Override
    public int compareTo(User o) {
        return nick.compareTo(o.getNick());
    }

    public User(String nick, String email, String pass) {
        this.nick = nick;
        this.email = email;
        this.pass = pass;
        this.pass = generate_pas();
    }

    public User(String nick, String pass) {
        if(nick!=null && pass!=null){
        this.nick = nick;
        this.pass = pass;}
        else System.out.println("poshol naxuy");
    }

    public String getNick() {
        return nick;
    }

    public String getEmail() {
        return email;
    }

    public String getPass(){
        return pass;
    }

    public String generate_pas(){
        String CHARACTERS = "?!#$%&'()*+,-.0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        Random rand = new Random();
        StringBuilder pass = new StringBuilder();
        int sizeOFpass = rand.nextInt(4)+6;
        for(int i = 0; i<sizeOFpass;i++){
            pass.append(CHARACTERS.charAt(rand.nextInt(CHARACTERS.length())));
        }
        return (this.pass=pass.toString());
    }

    @Override
    public String toString() {
        return "User{" +
                "nick='" + nick + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
