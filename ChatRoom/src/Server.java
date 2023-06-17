import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    public static void main(String[] args) throws IOException {
        HashMap<Socket, String> NameOfClientsList = new HashMap<>();
        ArrayList<Socket> clients = new ArrayList<>();
        ServerSocket serversocket = new ServerSocket(7000);
        System.out.println("Waiting for clients...");
        while (true) {
            Socket clientSocket = serversocket.accept();
            clients.add(clientSocket);
            Thread newThread = new Thread(() ->
            {
                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    while (true) {
                        String outputString = input.readLine();
                        if (outputString.equals("Exit")) {
                            throw new SocketException();
                        }
                        if (!NameOfClientsList.containsKey(clientSocket)) {
                            String[] messageString = outputString.split(":", 2);
                            NameOfClientsList.put(clientSocket, messageString[0]);
                            System.out.println(messageString[0] + messageString[1]);
                            showMessageToAllClients(clients, clientSocket, messageString[0] + messageString[1]);
                        } else {
                            System.out.println(outputString);
                            showMessageToAllClients(clients, clientSocket, outputString);
                        }
                    }
                } catch (SocketException e) {
                    String printMessage = NameOfClientsList.get(clientSocket) + " left the chat room";
                    System.out.println(printMessage);
                    showMessageToAllClients(clients, clientSocket, printMessage);
                    clients.remove(clientSocket);
                    NameOfClientsList.remove(clientSocket);
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

        for (int i=0;i < clients.size();i++) {
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
}

