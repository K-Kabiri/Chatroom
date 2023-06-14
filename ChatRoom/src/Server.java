import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("Waiting for clients...");
        ServerSocket serverSocket = new ServerSocket(7000);
        Socket clientSocket1 = serverSocket.accept();
        InputStreamReader reader = new InputStreamReader(((Socket) clientSocket1).getInputStream());
        BufferedReader in = new BufferedReader(reader);
        String username1=in.readLine();
        System.out.println(username1);
        String id=in.readLine();
        System.out.println(id);
        //System.out.println("Server accepted a client1...");
        //------------------------------------------------------------------------------------------
        Socket clientSocket2 = serverSocket.accept();
        InputStreamReader reader2 = new InputStreamReader(((Socket) clientSocket2).getInputStream());
        BufferedReader in2 = new BufferedReader(reader2);
        String username2=in2.readLine();
        System.out.println(username2);
        String id2=in2.readLine();
        System.out.println(id2);
        //System.out.println("Server accepted a client2...");

        //-----------------------------------------------------------------
        PrintWriter out = new PrintWriter(clientSocket1.getOutputStream(), true);
        PrintWriter out2 = new PrintWriter(clientSocket2.getOutputStream(), true);
        //-----------------------------------------------------------------
        String question;
        String question2;
        while (((question = in.readLine()) != null) &&((question2 = in2.readLine()) != null)) {
            if (question.equals("End") || question2.equals("End")) {
                break;
            } else {
                System.out.println(username1+" : " + question);
                System.out.println(username2+" : " + question2);
                out.println("Me: " + question);
                out.println("You: "+question2);
                out2.println("Me: " + question2);
                out2.println("You: "+question);

            }
        }
        //-----------------------------------------------------------------
        in.close();
        clientSocket1.close();
        clientSocket2.close();
        serverSocket.close();
    }
}

