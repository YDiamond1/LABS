package Server;

import Server.db.DB;
import usingcollection.SalesmanSet;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static int readINTport(Integer refer, Scanner scr){
        System.out.println("Write port");

        while(true) {
            try {
                refer = Integer.valueOf(scr.nextLine());
                if(refer>1024 && refer<65535)
                    break;
                else throw new NumberFormatException(" ");
            } catch (NumberFormatException ex) {
                System.out.println("Wrong, try again");
            }catch (NoSuchElementException | NullPointerException ex){
                System.out.println("You were locked me, bye");
                System.exit(-1);
            }
        }return refer;
    }
    public static int readINTthreads(Integer refer, Scanner scr){
        System.out.println("Write amount of threads");
        while(true) {
            try {
                refer = Integer.valueOf(scr.nextLine());
                if(refer>0 && refer<=10)
                    break;
                else throw new NumberFormatException("wrong format");
            } catch (NumberFormatException ex) {
                System.out.println("Wrong, try again");
            }catch ( NoSuchElementException | NullPointerException ex){
                System.out.println("You were locked me, bye");
                System.exit(-1);
            }
        }
        return refer;
    }
    public static ExecutorService executor = null;

    public static void main(String[] args) {



        // изменение потока out
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        }
        catch (java.io.UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }






        int port=-1000, threads=-100;
        Scanner scr = new Scanner(System.in);
        ServerSocketChannel ssc =null;
        Selector sc =null;

        SalesmanSet collection = new SalesmanSet(new ConcurrentSkipListSet<>(),new DB("localhost",5432,"postgres"));
        Map<Integer,SocketChannel> readQueueSet = new ConcurrentHashMap<>();
        Set<User> users = new ConcurrentSkipListSet<>();
        Execution.set = users;
        Execution.sender = new MailSender("signinbotik@gmail.com","botikl71");
        boolean [] closeApp = {false};
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if(executor!=null) {
                    executor.shutdown();
                    try {
                        Thread.sleep(2000);
                        collection.getDb().connection.close();
                    } catch (InterruptedException | SQLException ex) {
                        System.out.println(ex.getMessage());
                    }

                    closeApp[0] = true;
                }

            }
        });
        //if(closeApp[0])System.exit(-1);

        //считывание порта и потоков
        try {
            port = readINTport(port, scr);
            threads = readINTthreads(threads, scr);
        }catch (NullPointerException ex){
            System.exit(-1);
        }


        //запуск сервера
        try{
            executor = Executors.newFixedThreadPool(threads);
            ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(port));
            sc = Selector.open();
            ssc.configureBlocking(false);
            SelectionKey key = ssc.register(sc, SelectionKey.OP_ACCEPT);
            System.out.println("server started to work");
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            System.exit(-1);
        }



        //обработка клиентов
        try {
            while (true) {

                int count = sc.select();

                if(count == 0){
                    continue;
                }
                try {
                    Iterator it = sc.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey sKey = (SelectionKey) it.next();

                        if (sKey.isAcceptable()) {
                            try {
                                SocketChannel socketClient = ssc.accept();
                                socketClient.configureBlocking(false);
                                socketClient.register(sc, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                                System.out.println("Client connected");
                            } catch (IOException ex) {
                                System.out.println("Unable to accept server");
                                ex.printStackTrace();
                                sKey.cancel();
                            }
                            it.remove();
                        }
                        if (sKey.isReadable()) {
                            if (sKey.channel() instanceof SocketChannel) {
                                SocketChannel ReadChannel = (SocketChannel) sKey.channel();
                                if (!readQueueSet.containsKey(ReadChannel.hashCode())) {
                                    readQueueSet.put(ReadChannel.hashCode(), ReadChannel);
                                    System.out.println("Readable socket");
                                    executor.submit(new ReadClient(ReadChannel, readQueueSet, executor, collection, sKey));

                                    it.remove();
                                }
                            }
                        }

                    }
                }catch (CancelledKeyException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }catch (IOException ex){
            System.out.println("check select");
            ex.printStackTrace();
        }


    }
}
