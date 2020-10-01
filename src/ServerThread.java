import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThread extends Thread {
    private Socket socket = null;
    private ArrayList<BookEntry> bookEntries;

    public ServerThread(String name, Socket socket, ArrayList<BookEntry> bookEntries) {
        super(name);
        this.socket = socket;
        this.bookEntries = bookEntries;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = in.readLine(), inMessage, outMessage;
            while (line != null) {
                inMessage = "";
                if (line.equals("ping")) {
                    outMessage = "pong";
                } else {
                    /* READ DATA START */
                    while (!line.contains("\\EOF")) {
                        inMessage = inMessage.concat(line + "\r\n");
                        line = in.readLine();
                    }
                    /* READ DATA END */
                    outMessage = inMessage.trim() + "\n\\EOF";
                }
                out.println(outMessage);
                line = in.readLine();
            }
            out.close();
            in.close();
            socket.close();
            System.out.println("Server thread [" + getName() + "] disconnected.");
            System.out.println("Active connections: " + (Thread.activeCount() - 2));
            this.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
