import java.io.*;
import java.net.*;

public class ClientHandler {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String address, int port) throws IOException {
        clientSocket = new Socket();
        try {
            clientSocket.connect(new InetSocketAddress(address, port), 5000);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new IOException("ERROR: Connection refused please check IP Address and Port and try again");
        }
    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    private String processRequest(Request request, String STATUS, String MESSAGE, String COLOR, Double HEIGHT, Double WIDTH, Double CoordinateX, Double CoordinateY, boolean all) {
        String requestData = request.name() + "\r\n";
        if (all) {
            requestData += "ALL";
        } else {
            requestData += "STATUS " + STATUS + "\r\n" + "MESSAGE " + MESSAGE + "\r\n" + "COLOR " + COLOR + "\r\n" + "HEIGHT " + HEIGHT + "\r\n" + "WIDTH " + WIDTH + "\r\n" + "CoordinateX " + CoordinateX + "\r\n" + "CoordinateY " + CoordinateY;
        }
        return requestData;
    }

    public String sendMessage(Request request, String STATUS, String MESSAGE, String COLOR, Double HEIGHT, Double WIDTH, Double CoordinateX, Double CoordinateY,
    boolean all) throws IOException {
        String requestData = processRequest(request, STATUS, MESSAGE, COLOR, HEIGHT, WIDTH, CoordinateX, CoordinateY, all);
        out.println(requestData + "\r\n\\EOF");
        String response = "";
        String line = in.readLine();
        while (line != null && !line.contains("\\EOF")) {
            response = response.concat(line + "\r\n");
            line = in.readLine();
        }

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