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
            requestData += "STATUS " + STATUS + "\r\n" + "MESSAGE " + MESSAGE + "\r\n" + "COLOR " + COLOR + "\r\n" + "HEIGHT " + HEIGHT + "\r\n" + "Coordinate X " + CoordinateX + "\r\n" + "Coordinate Y " + CoordinateY;
        }
        return requestData;
    }

    public String sendMessage(Request request, String STATUS, String MESSAGE, String COLOR, Double HEIGHT, Double WIDTH, Double CoordinateX, Double CoordinateY,
                              boolean all, boolean bibtex) throws IOException {
        String requestData = processRequest(request, STATUS, MESSAGE, COLOR, HEIGHT, WIDTH, CoordinateX, CoordinateY, all);
        out.println(requestData + "\r\n\\EOF");
        String response = "";
        String line = in.readLine();
        while (line != null && !line.contains("\\EOF")) {
            response = response.concat(line + "\r\n");
            line = in.readLine();
        }
        if (bibtex) {
            String[] splitResponse = response.split("\r\n");
            if (splitResponse.length > 2) {
                response = "";
                for (String s : splitResponse) {
                    String[] splitLine = s.split(" ");
                    if (splitLine[0].contains("STATUS:")) {
                        response = response.concat("@BookEntry{\r\n\tSTATUS\t= \"" + s.substring(splitLine[0].length()).trim() + "\",\r\n");
                    }
                    if (splitLine[0].contains("MESSAGE:")) {
                        response = response.concat("\tMESSAGE\t= \"" + s.substring(splitLine[0].length()).trim() + "\",\r\n");
                    }
                    if (splitLine[0].contains("COLOR:")) {
                        response = response.concat("\tCOLOR\t= \"" + s.substring(splitLine[0].length()).trim() + "\",\r\n");
                    }
                    if (splitLine[0].contains("HEIGHT:")) {
                        if (s.substring(splitLine[0].length()).trim().equals("0"))
                            response = response.concat("\tHEIGHT\t= \"No Value\",\r\n}\r\n");
                        else
                            response = response.concat("\tHEIGHT\t= \"" + s.substring(splitLine[0].length()).trim() + "\",\r\n}\r\n");
                    }
                    if (splitLine[0].contains("WIDTH:")) {
                        if (s.substring(splitLine[0].length()).trim().equals("0"))
                            response = response.concat("\tWIDTH\t= \"No Value\",\r\n}\r\n");
                        else
                            response = response.concat("\tWIDTH\t= \"" + s.substring(splitLine[0].length()).trim() + "\",\r\n}\r\n");
                    }
                    if (splitLine[0].contains("CoordinateX:")) {
                        if (s.substring(splitLine[0].length()).trim().equals("0"))
                            response = response.concat("\tCoordinateX\t= \"No Value\",\r\n}\r\n");
                        else
                            response = response.concat("\tCoordinateX\t= \"" + s.substring(splitLine[0].length()).trim() + "\",\r\n}\r\n");
                    }
                    if (splitLine[0].contains("CoordinateY:")) {
                        if (s.substring(splitLine[0].length()).trim().equals("0"))
                            response = response.concat("\tCoordinateY/\t= \"No Value\",\r\n}\r\n");
                        else
                            response = response.concat("\tCoordinateY\t= \"" + s.substring(splitLine[0].length()).trim() + "\",\r\n}\r\n");
                    }
                }
            }
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
