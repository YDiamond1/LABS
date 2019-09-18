package Server;


import commands.Command;
import usingcollection.SalesmanSet;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.SelectionKey;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import java.security.MessageDigest;

public class Execution {
    public static Set<User> set;
    public static MailSender sender;
    public static Command execute(SalesmanSet setl, Command cmd, SelectionKey key){

            switch (cmd.toString()) {
                case "add": {
                    return (new Command(setl.add(cmd.getMan()))).setUser(cmd.getUser());

                }
                case "add_if_min": {
                    return (new Command(setl.add_if_min(cmd.getMan()))).setUser(cmd.getUser());

                }
                case "added_if_max": {
                    return (new Command( setl.add_if_max(cmd.getMan()))).setUser(cmd.getUser());
                }
                case "remove": {
                    return (new Command(setl.remove(cmd.getMan()))).setUser(cmd.getUser());
                }
                case "show": {
                    return (new Command(setl.show())).setUser(cmd.getUser());
                }
                case "info": {
                    return (new Command(setl.info())).setUser(cmd.getUser());
                }
                case "clear": {
                    return (new Command(setl.clear(cmd.getUser()))).setUser(cmd.getUser());
                }
                case "shutdown": {
                    key.cancel();
                    if(cmd.getUser()!=null)set.remove(cmd.getUser());
                    return new Command("");
                }
                case "hello": {
                    return new Command("hello");
                }
                case "login":{
                    User user;
                    try {
                        System.out.print("User:"+cmd.getFirstParametr());
                        System.out.print(" pass:"+ cmd.getSecondParametr());
                        user = new User(cmd.getFirstParametr(),cmd.getSecondParametr());
                        Connection con = setl.getDb().connection;
                        PreparedStatement statement = con.prepareStatement("SELECT login, pass FROM public.\"USERS\" WHERE login=? AND pass=? ");
                        statement.setString(1, cmd.getFirstParametr());
                        MessageDigest mes = MessageDigest.getInstance("SHA-256");
                        try {
                            String hashpass = new String(mes.digest(user.getPass().getBytes("UTF-8")));
                            statement.setString(2,hashpass);
                        }catch (UnsupportedEncodingException ex){}

                        if(statement.executeQuery().next()){

                            set.add(user);
                            statement.close();
                            Command response = new Command("You are logged in");
                            response.setUser(user);
                            return response;

                        }
                        else{

                            return new Command("Wrong login or pass");
                        }
                    }catch (SQLException ex){
                        return new Command(ex.getMessage());
                    }catch (NoSuchAlgorithmException ex){
                        return new Command(ex.getMessage());
                    }
                }
                case "signin":{
                    PreparedStatement statement=null;
                    try {
                        MessageDigest mes = MessageDigest.getInstance("SHA-256");
                        Connection con = setl.getDb().connection;
                        statement= con.prepareStatement("SELECT login FROM public.\"USERS\" WHERE login=?");
                        statement.setString(1,cmd.getFirstParametr());
                        if(!statement.executeQuery().next()){
                            statement.close();
                            User user = new User(cmd.getFirstParametr(),cmd.getSecondParametr(),"default");

                            String hashpass = new String(mes.digest(user.getPass().getBytes()));
                            sender.send(user.getPass(),user.getEmail());
                            PreparedStatement statement1 = con.prepareStatement("INSERT INTO public.\"USERS\"(login,pass,email) VALUES(?,?,?)");
                            statement1.setString(1,user.getNick());
                            statement1.setString(2,hashpass);
                            statement1.setString(3,user.getEmail());
                            statement1.executeUpdate();
                            statement1.close();
                            return new Command("Password was send to your email");
                        }else{
                            statement.close();
                            return new Command("This account is used");
                        }
                    }catch (SQLException ex){
                        return new Command(ex.getMessage()).setUser(null);
                    }catch (NoSuchAlgorithmException ex){
                        return new Command(ex.getMessage());
                    }catch (MessagingException ex){
                        return new Command("Can't send the letter: "+ ex.getMessage());
                    }

                }
                case "logout":{
                    if(cmd.getUser()!=null)
                    set.remove(cmd.getUser());
                    return new Command("You are logged out");
                }
                default: {
                    return new Command("Unknown command");
                }
            }


    }


}
