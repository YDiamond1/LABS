package Client;

import Server.Server;
import Server.User;
import classes.Salesman;
import commands.Command;
import commands.CommandHandler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Client {


    public static void main(String[] args) {
        Socket socket = null;
        int port = 0;
        Scanner scanner = new Scanner(System.in);
        boolean[] isAddShutDown ={false};
        boolean[] closeApp = {false};
        Command again = null;
        Command response=null;
        Status status = Status.NOT_LOGGING_IN;
        User user = null;

        while(true) {

            port = Server.readINTport(port, scanner);
            while (true) {
                try {
                    socket = new Socket("localhost", port);

                } catch (UnknownHostException ex) {
                    System.out.println(ex.getMessage());
                    break;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    break;
                }


                final Socket shutdown = socket;
                final User forSD=user;
                if (!isAddShutDown[0]) {
                    isAddShutDown[0]=true;
                    Runtime.getRuntime().addShutdownHook(new Thread() {
                        @Override
                        public void run() {

                            try {
                                Sends.sendANDaccept(new Command("shutdown", new Salesman()), shutdown,forSD);
                                closeApp[0] = true;
                                try {
                                    System.out.println("Closing...");
                                    shutdown.close();
                                } catch (IOException ex) {
                                    System.out.println("Error of closing socket");
                                    ex.printStackTrace();
                                }
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }catch(ClassNotFoundException ex){
                                System.out.println(ex.getMessage());
                            }
                        }
                    });
                }


                CommandHandler cmdh = new CommandHandler(new BufferedReader(new InputStreamReader(System.in)), new BufferedOutputStream(System.out));
                try {
                    socket.setSoTimeout(15000);

                    while (true) {
                        if(again!=null) {
                            if (!again.toString().equals("help")  &&
                                    (status == Status.LOGIN && (!again.toString().equals("login") && !again.toString().equals("signin")) || (status == Status.NOT_LOGGING_IN && (again.toString().equals("login") || again.toString().equals("signin"))))) {
                                response = Sends.sendANDaccept(again, socket, user);
                                if (status == Status.NOT_LOGGING_IN && again.toString().equals("login") && response.toString().equals("You are logged in")) {
                                    System.out.println(response.getUser());
                                    status = Status.LOGIN;
                                    user = response.getUser();

                                } else if (status == Status.LOGIN && again.toString().equals("logout")) {
                                    user = null;
                                    status = Status.NOT_LOGGING_IN;
                                }
                                System.out.println(response.toString());
                            } else if (status == Status.LOGIN && (again.toString().equals("login") || again.toString().equals("signin"))) {
                                System.out.println("You already have been in System");
                            } else if (again.toString().equals("help")) {
                                cmdh.help();
                            }else if(again.toString().equals("Unknown command")){
                                System.out.println("Unknown command");
                            }
                            else {
                                System.out.println("Log in for sending commands");
                            }
                        }else{
                            System.out.println("Response from Server: "+Sends.sendANDaccept(new Command("hello", new Salesman()),socket,user));
                        }
                        cmdh.readCommand();
                        again = cmdh.getCmd();
                    }
                } catch (IOException exx) {
                    System.out.println("Server isn't avaiable: " + exx.getMessage());
                    System.out.println("Trying connect");

                } catch (ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        }
    }
}
