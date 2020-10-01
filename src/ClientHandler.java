import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String address, int port) throws IOException {
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(address, port), 5000);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Connected!");
    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        System.out.println("Disconnected!");
    }

    public String sendMessage(String message) throws IOException {
        out.println(message + "\n\\EOF");
        String response = "";
        String line = in.readLine();
        while (line != null && !line.contains("\\EOF")) {
            response = response.concat(line + "\r\n");
            line = in.readLine();
        }
        System.out.println("Received message: " + response);
        return response;
    }

    public boolean isConnected() {
        try {
            out.println("ping");
            return in.readLine().equals("pong");
        } catch (NullPointerException | IOException e) {
            return false;
        }
    }

}
