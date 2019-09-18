package Client;

import Server.User;
import commands.Command;
import commands.CommandHandler;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sends {
    public static Command sendANDaccept(Command cmd, Socket socket, User _user)throws IOException,ClassNotFoundException{
        if(socket!= null) {
            ObjectOutputStream ous = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            cmd.setUser(_user);
            if(_user!=null) {
                cmd.getMan().setUser(_user.getNick());
                System.out.println(_user.getNick());}
            ous.writeObject(cmd);
            ous.flush();
            System.out.println("Command was sent");
            if(!cmd.toString().equals("shutdown")){
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            return CommandHandler.readResponse(ois);
            }

        }else {System.out.println("Socket is Null");System.exit(-1);}

        return null;
    }

}
