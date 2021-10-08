import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    public static void main(String[] args) throws IOException {
        int port;
        if (args.length == 0) port = 5000;
        else port = Integer.parseInt(args[0]);

        ArrayList<PostEntry> bookEntries = new ArrayList<PostEntry>();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server running on port " + port + ".");
        System.out.println("IP address: " + InetAddress.getLocalHost().getHostAddress());

        while (true) {
            Socket clientSocket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(Thread.activeCount() + "", clientSocket, bookEntries);
            serverThread.start();
            System.out.println("Connection detected, starting server thread [" + (Thread.activeCount() - 1) + "]");
            System.out.println("Active connections: " + (Thread.activeCount() - 1));
        }
    }

}
