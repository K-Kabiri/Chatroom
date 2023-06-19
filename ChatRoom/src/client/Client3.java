package client;

import models.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client3 {
    static final User user = new User("Client3" , 3);

    public static void main(String[] args) throws IOException {

        Socket clientSocket = new Socket("127.0.0.1", 7000);
        System.out.println("Client connected to the server...");
        //-----------------------------------------------------------------
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        //-----------------------------------------------------------------
        ClientController clientController = new ClientController(clientSocket);
        new Thread(clientController).start();
        //-----------------------------------------------------------------
        out.println(user.getUserID() + ":" + user.getUsername() + ": has join chatroom.");

        Scanner sc = new Scanner(System.in);
        String message = "";
        while (true) {
            message = sc.nextLine();
            if (message.equals("Exit")) {
                out.println("Exit");
                break;
            }
            out.println(user.getUserID() + ":" + user.getUsername() + ":" + message);
        }
        //-----------------------------------------------------------------
        out.close();
        clientSocket.close();
    }
}
