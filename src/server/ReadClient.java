package Server;

import commands.Command;
import usingcollection.SalesmanSet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ExecutorService;


public class ReadClient implements Runnable,Readable {
    protected SocketChannel sc;
    protected Command cmd;
    protected Map<Integer,SocketChannel> set;
    protected ExecutorService service;
    private SalesmanSet setl;
    public SelectionKey key;
    protected boolean error=false;
    public ReadClient(SocketChannel sc, Map<Integer,SocketChannel> set, ExecutorService _service, SalesmanSet salesmanSet,SelectionKey key) {
        this.sc = sc;
        this.set = set;
        service = _service;
        setl = salesmanSet;
        this.key = key;
    }

    public Command read(){
        Command obj = null;
        try {
            System.out.println("Reading from "+sc);
            ByteBuffer buffer = ByteBuffer.allocate(10000);

            while(sc.read(buffer)>0){
            }
            buffer.rewind();

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            obj =(Command) ois.readObject();

            System.out.println("Object was read. It's command:\""+obj+"\"");
        }catch (IOException ex){
            System.out.println("Error reading: "+ex.getMessage());
            key.cancel();
            error = true;
        }catch (ClassCastException ex){
            System.out.println("Wrong Object"+ sc);
        }catch (ClassNotFoundException ex){
            System.out.println(ex.getMessage()+sc);
        }
        set.remove(sc.hashCode());

        return obj;
    }
    public void run(){
        cmd = read();
        if(!error) {
            service.execute(new AnswerClient(sc, setl, cmd, key));
        }

    }
}
