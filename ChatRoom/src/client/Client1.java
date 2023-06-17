package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    static final String username = "Client1";
    static final int ID = 1;

    public static void main(String[] args) throws IOException {

        Socket clientSocket = new Socket("127.0.0.1", 7000);
        System.out.println("Client connected to the server...");
        //-----------------------------------------------------------------
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        //-----------------------------------------------------------------
        ClientThread clientThread = new ClientThread(clientSocket);
        new Thread(clientThread).start();
        //-----------------------------------------------------------------
        out.println(username + ": has join chatroom.");

        Scanner sc = new Scanner(System.in);
        String message = "";
        while (true) {
            message = sc.nextLine();
            if (message.equals("Exit")) {
                out.println("Exit");
                break;
            }
            out.println(username + " : " + message);
        }

        //-----------------------------------------------------------------
        out.close();
        clientSocket.close();
    }
}