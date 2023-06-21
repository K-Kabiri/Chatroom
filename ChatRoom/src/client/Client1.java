package client;

import models.User;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    static final User user = new User("Client1", 1);

    public static void main(String[] args) throws IOException {

        Socket clientSocket = new Socket("127.0.0.1", 8080);
        System.out.println("You joined the chat...");
        //-----------------------------------------------------------------
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        //-----------------------------------------------------------------
        ClientController clientController = new ClientController(clientSocket);
        new Thread(clientController).start();
        //-----------------------------------------------------------------
        out.println(user.getUserID() + ":" + user.getUsername() + ": has join chatroom.");

        Scanner sc = new Scanner(System.in);
        String message;
        while (true) {
            message = sc.nextLine();
            if (message.equals("Exit")) {
                out.println("Exit");
                break;
            }
            else if (message.equals("Ping"))
            {
                long firstTime=System.currentTimeMillis();
                message="Ping-"+firstTime;
            }
            out.println(user.getUserID() + ":" + user.getUsername() + ": " + message);

        }
        //-----------------------------------------------------------------
        out.close();
        clientSocket.close();
    }
}