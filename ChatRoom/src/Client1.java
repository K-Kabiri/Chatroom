import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    static final String username="Client1";
    static final int ID=1;
    public static void main(String[] args) throws IOException {

        Socket clientSocket = new Socket("127.0.0.1", 7000);
        System.out.println("Client connected to the server...");
        //-----------------------------------------------------------------
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        //-----------------------------------------------------------------
        InputStreamReader reader = new InputStreamReader(((Socket) clientSocket).getInputStream());
        BufferedReader in = new BufferedReader(reader);
        //-----------------------------------------------------------------
        out.println(username);
        out.println(ID);
        Scanner sc = new Scanner(System.in);
        String message = "";
        do
        {
            message = sc.nextLine();
            out.println(message);
            String question = in.readLine();
            String answer = in.readLine();
            System.out.println(question);
            System.out.println(answer);

        } while (!message.equals("END"));
        //-----------------------------------------------------------------
        System.out.println("The connection was interrupted");
        out.close();
        clientSocket.close();
    }
}