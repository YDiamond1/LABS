package Server;

import commands.Command;
import usingcollection.SalesmanSet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class AnswerClientCommands extends AnswerClient {

    public AnswerClientCommands(SocketChannel sc, SalesmanSet set, Command command, SelectionKey key){
        super(sc,set,command,key);
    }

    public void run(){/*
        String response=Execution.execute(set,command);
        ByteBuffer bResponse = ByteBuffer.allocate(500);
        bResponse.put(response.getBytes());
        bResponse.flip();
        System.out.println("Responsing...");
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream ois = new ObjectOutputStream(bytes);
            ois.writeObject(new Command(response));
            channel.write(ByteBuffer.wrap(bytes.toByteArray()));
            System.out.println("Response was sent");

        }catch (IOException io){
            System.out.println("Error while responsing"+channel+":"+io.getMessage());
        }*/
    }
}
