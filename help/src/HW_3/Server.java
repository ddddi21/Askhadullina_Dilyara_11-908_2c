package HW_3;

import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.read();
    }
    public void read() throws IOException {
        System.out.println("Enter request please: /id/<id number>, /messages or /feed");
        String [] enter_request = new BufferedReader(new InputStreamReader(System.in)).readLine().toLowerCase().split("/");
        switch (enter_request[1]) {
            case "id":
                id(enter_request[2], new File("id.html"));
                break;
            case "messages":
                messages();
                break;
            case "feed":
                feed();
                break;
            default:
                file_not_found();
                break;
        }

    }
    public static void id(String id, File file) throws IOException {
        File web_file = new File(String.valueOf(file));
        PrintWriter writer = new PrintWriter(String.valueOf(file));
        boolean isfound = false;
        BufferedReader reader= new BufferedReader(new FileReader("src/users.txt"));
        String s = reader.readLine();
        while (s != null){
            String [] user = s.split(";");
            if(user[0].equals(id)) {
                isfound = true;
                writer.println("Name:" + user[1] + " Surname:"+ user[2]
                +" Age:"+ user[3]+ " From:"+ user[4]);
            }
            s = reader.readLine();
        }
        if(!isfound){
            System.out.println("User not found. Would you want to add him?");
//            String answer = new BufferedReader(new InputStreamReader(System.in)).readLine().toLowerCase();
//            if (answer.equals("yes")){
//                PrintWriter write_new_people = new PrintWriter(file);
//                write_new_people.println();
//                System.out.println("Enter his Name,Surname,Age, Place From from ; in right order");
//            }
            //доделать

        }
        reader.close();
        writer.close();

    }
    public static void messages() throws IOException {
        File file = new File("messages.html");
        BufferedReader reader_message= new BufferedReader(new FileReader("src/messages.txt"));
        PrintWriter writer = new PrintWriter(file);
        String message = reader_message.readLine();
        BufferedReader reader_user = null;
        while(message!=null){
            reader_user= new BufferedReader(new FileReader("src/users.txt"));
            String user = reader_user.readLine();
            String [] messages = message.split(";");
            while (user != null){
                String [] users = user.split(";");
                if(users[0].equals(messages[0])) {
                    writer.println("From:" + users[1] + " "+ users[2]);
                }
                if(users[0].equals(messages[1])) {
                    writer.println("To:" + users[1] + " "+ users[2]);
                }
                user = reader_user.readLine();
            }
            message = reader_message.readLine();
            writer.println("At:" + messages[2]);
            writer.println(messages[3]);
            writer.println("-----");
        }
        reader_message.close();
        reader_user.close();
        writer.close();
    }
    public static void feed() throws IOException {
        File file = new File("feed.html");
        BufferedReader reader_post= new BufferedReader(new FileReader("src/posts.txt"));
        PrintWriter writer = new PrintWriter(file);
        String s = reader_post.readLine();
        BufferedReader reader_user = null;
        while(s!=null) {
            String[] feed = s.split(";");
            reader_user= new BufferedReader(new FileReader("src/users.txt"));
            String user = reader_user.readLine();
            while (user != null){
                String [] users = user.split(";");
                if(users[0].equals(feed[0])) {
                    writer.println("From:" + users[1] + " "+ users[2]);
                }
                user = reader_user.readLine();
            }
            writer.println("Post's name: "+ feed[1]);
            writer.println("About: "+ feed[2]);
            writer.println("At: "+ feed[3]);
            writer.println(feed[4]);
            writer.println("-----------------");
            s = reader_post.readLine();
        }
        reader_post.close();
        reader_user.close();
        writer.close();

    }
    public static void file_not_found() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("404.html"));
        System.out.println("Incorrect request");
        writer.println("404. File not found");
        writer.close();
    }
}
