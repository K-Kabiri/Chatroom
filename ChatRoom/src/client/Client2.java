package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    static final String username = "Client2";
    static final int ID = 2;

    public static void main(String[] args) throws IOException {

        Socket clientSocket = new Socket("127.0.0.1", 7000);
        System.out.println("Client connected to the server...");
        //-----------------------------------------------------------------
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(username+ " has join chatroom.");
        //-----------------------------------------------------------------
        Scanner sc = new Scanner(System.in);
        String message = "";
        do {
            message = sc.nextLine();
            out.println(username+" : "+message);

        } while (!message.equals("Exit"));
        //-----------------------------------------------------------------
        out.close();
        clientSocket.close();
    }
}
