import models.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Server {
    public static void main(String[] args) throws IOException {
        HashMap<Socket, String> nameOfClientsList = new HashMap<>();
        HashMap<String, Socket> clientsList = new HashMap<>();
        ArrayList<Socket> clients = new ArrayList<>();
        ServerSocket serversocket = new ServerSocket(7000);
        System.out.println("Waiting for users...");
        while (true) {
            Socket clientSocket = serversocket.accept();
            clients.add(clientSocket);
            Thread newThread = new Thread(() ->
            {
                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    while (true) {
                        String outputString = input.readLine();
                        String[] messageString = outputString.split(":", 3);
                        String[] messageString2=messageString[2].split("-",3);
                        addMessageToDatabase(Integer.parseInt(messageString[0]), messageString[1], messageString[2]);
                        if (outputString.equals("Exit")) {
                            throw new SocketException();
                        }
                        if (Objects.equals(messageString2[0], "PV"))
                        {
                                Socket receiver=findClient(clientsList,messageString2[1]);
                                System.out.println(outputString);
                                showMessagePV(receiver,messageString[1]+"-"+messageString2[0]+" : "+messageString2[2]);

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
}

