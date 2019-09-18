
import parsers.CSV.CSVException;
import parsers.CSV.CSVParser;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;


import commands.Command;
import usingcollection.SalesmanSet;

public class Main {

   /* public static void LABA$(String[] args) {

        // создание всех необходимых объектов для работы программы
        House house = new House("Солнечный","Кузнечная","Незнайка");
        Time time =  Time.valueOf("MORNING");
        Human neznayka = new Human("Незнайка",13,house,true);
        Human kozlik = new Human("",12,house,true){
            @Override
            public String getName(){
                return "Козлик";
            }
        };
        //Corner corner = new Corner("Кузнечная","Солнечный");
        Salesman salesman = new Salesman("Незнайка",13,house,true, 50);

        // сама программа
        neznayka.wakeUp();
        System.out.print(" и ");
        kozlik.wakeUp(house);
        System.out.print("\n");

        if(neznayka.isHungry()) {
            neznayka.eat();
        }
        System.out.println(" и ");
        if(kozlik.isHungry()) {
            kozlik.eat();
        }


        System.out.print("\nПосле ");
        System.out.print(neznayka.getName()+" и ");
        kozlik.exit();

        try{
            System.out.print("\n");
            neznayka.move(corner);
            System.out.print(" и ");
            kozlik.move(corner);

        }
        catch(MoveException ex){
            System.out.print(ex.getMessage());
        }

        System.out.print("\n");
        neznayka.see(salesman);
        System.out.println();
        salesman.yell("Свежие новости только сегодня за 2 сантика");

        try {
            salesman.sell(neznayka);
            System.out.println();
        }
        catch (NotEnoughMoneyException ex){
            System.out.println(ex.getMessage());
        }

        System.out.print(kozlik.getName()+" и ");
        neznayka.findInformation();
    }
*/
   // laba5
   /*
    public static void main(String[] args) {

        //получение файлов
        Map<String, String> env = System.getenv();
        String fileName = env.get("laba5");
        String charset = "UTF-8";//env.get("CHARSET5") == null ? "UTF-8" : env.get("CHARSET5");
        System.out.println(charset);
        if (fileName == null) {
            System.out.println("Set an environment variable named \"laba5\"");
            System.exit(-1);
        }



        // изменение потока out
        try {
            System.setOut(new PrintStream(System.out, true, charset));
        }
        catch (java.io.UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }





        // обработка файла
        CSVParser parser = new CSVParser();
        try(InputStream in  = new BufferedInputStream(new FileInputStream(fileName)) ){
            parser = new CSVParser(in);
        }catch (FileNotFoundException ex){
            System.out.println(ex.getMessage());
            System.exit(-1);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
            System.exit(-1);
        }catch (CSVException ex){
            System.out.println(ex.getMessage());
            System.exit(-1);
        }



        //считывание комманд из строки
        SalesmanSet setSL;
        BufferedOutputStream out;
        final boolean [] closeApp = {false};
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in, charset))){

            setSL = new SalesmanSet(new ConcurrentSkipListSet<>(),parser.getObjects());
            Command cmd = new Command(in,setSL,charset);
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    if(Command.wasEdit){setSL.save(Command.wasEdit,fileName);if(Command.saving)System.out.println("КОЛЛЕКЦИЯ СОХРАНЕНА");}
                    closeApp[0]=true;

                }
            });
            if(closeApp[0])System.exit(-1);
            cmd.help();
            cmd.readCommand();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        catch (NullPointerException ex){
            System.out.println("НЕЧЕГО СОХРАНЯТЬ");
        }
        catch (NumberFormatException ex){
            System.out.println("Неверный формат переданых данных");
        }

    }
*/
}
