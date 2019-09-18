package Server;

import commands.Command;
import usingcollection.SalesmanSet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public  class AnswerClient implements Runnable,Answerable{
    protected SocketChannel channel;
    protected SalesmanSet set;
    protected Command command;
    public SelectionKey key;

    public AnswerClient(SocketChannel channel, SalesmanSet set, Command command,SelectionKey key) {
        this.channel = channel;
        this.set = set;
        this.command = command;
        this.key = key;
    }

    public AnswerClient(SocketChannel sc, SalesmanSet setl){
        channel =sc;
        set = setl;
    }

    public void response(){
        if (command==null) {
            try {
                ByteBuffer bytes = ByteBuffer.allocate(100);
                bytes.put("Object is NUll".getBytes());
                bytes.flip();
                channel.write(bytes);
            }catch (IOException ex){
                System.out.println("Error writing");
            }
        }

    }
    public  void run(){
        Command response=Execution.execute(set,command,key);
        if(!command.toString().equals("shutdown")) {
            System.out.println("Responsing...");
            try {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                ObjectOutputStream ois = new ObjectOutputStream(bytes);
                ois.writeObject(response);
                channel.write(ByteBuffer.wrap(bytes.toByteArray()));
                System.out.println("Response was sent");

            } catch (IOException io) {
                System.out.println("Error while responsing" + channel + ":" + io.getMessage());
            }
        }
    }

}
