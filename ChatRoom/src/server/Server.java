package server;

import models.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Server {
    public static void main(String[] args) throws IOException {
        HashMap<Socket, String> nameOfClientsList = new HashMap<>();
        HashMap<String, Socket> clientsList = new HashMap<>();
        ArrayList<Socket> clients = new ArrayList<>();
        ServerSocket serversocket = new ServerSocket(8080);
        System.out.println("Waiting for users...");
        while (true) {
            Socket clientSocket = serversocket.accept();
            clients.add(clientSocket);
            showAllMessagesToNewUser(clientSocket);
            Thread newThread = new Thread(() ->
            {
                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    while (true) {
                        String outputString = input.readLine();
                        String[] messageString = outputString.split(":", 3);
                        String[] messageString2=messageString[2].split("-",3);
                        if (outputString.equals("Exit")) {
                            throw new SocketException();
                        }
                        if (Objects.equals(messageString2[0], " PV"))
                        {
                                Socket receiver=findClient(clientsList,messageString2[1]);
                                System.out.println(outputString);
                                addMessageToPVDatabase(messageString[1] , messageString2[1] , Integer.parseInt(messageString[0]), messageString[1] , messageString2 [2]);
                                showMessagePV(receiver,messageString[1]+"-"+messageString2[0]+" : "+messageString2[2]);
                        }
                        else if (messageString2[0].equals(" Ping"))
                        {
                            PrintWriter printWriter;
                            printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                            long secondTime=System.currentTimeMillis();
                            printWriter.println("Connected ("+(secondTime-Long.parseLong(messageString2[1]))+")");

                        }
                        else {
                            if (!nameOfClientsList.containsKey(clientSocket)) {
                                nameOfClientsList.put(clientSocket, messageString[1]);
                                clientsList.put(messageString[1], clientSocket);
                                System.out.println(messageString[1] + messageString[2]);
                                showMessageToAllClients(clients, clientSocket, messageString[1] + " : " + messageString[2]);
                            } else {
                                System.out.println(outputString);
                                showMessageToAllClients(clients, clientSocket, messageString[1] + " : " + messageString[2]);
                            }
                            addMessageToDatabase(Integer.parseInt(messageString[0]), messageString[1], messageString[2]);
                        }
                    }
                } catch (SocketException e) {
                    String printMessage = nameOfClientsList.get(clientSocket) + " left the chat room";
                    System.out.println(printMessage);
                    showMessageToAllClients(clients, clientSocket, printMessage);
                    clients.remove(clientSocket);
                    nameOfClientsList.remove(clientSocket);
                } catch (Exception e) {
                    System.out.println(e.getStackTrace());
                }
            });
            newThread.start();
        }
    }

    private static void showMessageToAllClients(ArrayList<Socket> clients, Socket sender, String outputString) {
        Socket socket;
        PrintWriter printWriter;
        for (int i = 0; i < clients.size(); i++) {
            socket = clients.get(i);
            try {
                if (socket != sender) {
                    printWriter = new PrintWriter(socket.getOutputStream(), true);
                    printWriter.println(outputString);
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    private static Socket findClient(HashMap<String, Socket> clientsList, String clientName) {
        return clientsList.getOrDefault(clientName, null);
    }

    private static void showMessagePV(Socket sender, String outputString) {
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(sender.getOutputStream(), true);
            printWriter.println(outputString);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private static void addMessageToDatabase(int userID, String username, String message) {
        Message newMessage = new Message(username, userID, message);
        try {
            newMessage.insertMessage();
        } catch (SQLException se) {
            System.out.println(se);
        } catch (ClassNotFoundException cfe) {
            System.out.println(cfe);
        }
    }
    private static void showAllMessagesToNewUser(Socket client){
        try {
            ResultSet messages = Message.selectMessage();
            while(messages.next()){
                PrintWriter printWriter = new PrintWriter(client.getOutputStream(), true);
                printWriter.println(messages.getString("username") + " : " + messages.getString("message"));
            }
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } catch (ClassNotFoundException cfe) {
            throw new RuntimeException(cfe);
        } catch (IOException ie) {
            throw new RuntimeException(ie);
        }
    }
    private static void addMessageToPVDatabase(String client1 , String client2 , int userID , String username, String message) throws SQLException, ClassNotFoundException {
        Message newMessage = new Message(username, userID, message);

        if((Objects.equals(client1, "Client1") && Objects.equals(client2, "Client2")) ||(Objects.equals(client1, "Client2") && Objects.equals(client2, "Client1")) ){
            newMessage.insertMessageIntoClient1_Client2();
        }
        else if((Objects.equals(client1, "Client1") && Objects.equals(client2, "Client3")) ||(Objects.equals(client1, "Client3") && Objects.equals(client2, "Client1")) ){
            newMessage.insertMessageIntoClient1_Client3();
        }
        else if((Objects.equals(client1, "Client1") && Objects.equals(client2, "Client4")) ||(Objects.equals(client1, "Client4") && Objects.equals(client2, "Client1")) ){
            newMessage.insertMessageIntoClient1_Client4();
        }
        else if((Objects.equals(client1, "Client2") && Objects.equals(client2, "Client3")) ||(Objects.equals(client1, "Client3") && Objects.equals(client2, "Client2")) ){
            newMessage.insertMessageIntoClient2_Client3();
        }
        else if((Objects.equals(client1, "Client2") && Objects.equals(client2, "Client4")) ||(Objects.equals(client1, "Client4") && Objects.equals(client2, "Client2")) ){
            newMessage.insertMessageIntoClient2_Client4();
        }
        else if((Objects.equals(client1, "Client3") && Objects.equals(client2, "Client4")) ||(Objects.equals(client1, "Client4") && Objects.equals(client2, "Client3")) ){
            newMessage.insertMessageIntoClient3_Client4();
        }
    }
}