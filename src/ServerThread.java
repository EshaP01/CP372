import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private final ArrayList<PostEntry> postEntries;


    public ServerThread(String name, Socket socket, ArrayList<PostEntry> postEntries) {
        super(name);
        this.socket = socket;
        this.postEntries = postEntries;
    }

    public synchronized void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            listen();
            disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String processData(String[] data) {
        final String request = data[0].trim();
        switch (request) {
            case "POST":
                for (String d : data){
                    System.out.println(d);
                }
                return handleSubmit(data);
            case "GET":
                return handleGet(data);
            case "UPDATE":
                return handleUpdate(data);
            case "REMOVE":
                return handleRemove(data);
            default:
                return "ERROR: Invalid request please do one of [POST, GET, UPDATE, REMOVE]";
        }
    }

    private String handleSubmit(String[] data) {
        String message;
        PostEntry postEntry = new PostEntry();
        for (String line : data) {
            line = line.trim();
            String[] words = line.split(" ");
            
            // for(String L:words){
            //     System.out.println("Line Loop" + L);
            // }
            String value;
            System.out.println(words[0]);
            switch (words[0]) {
                case "STATUS":
                    postEntry.setSTATUS(words[1]);
                    break;
                case "MESSAGE":
                    if (Util.findByMESSAGE(postEntries, words[1]) != null) {
                        message = "ERROR: Post already exists";
                        return message;
                    }
                    value = line.substring(words[0].length()).trim();
                    postEntry.setMESSAGE(value);
                    break;
                case "COLOR":
                    value = line.substring(words[0].length()).trim();
                    postEntry.setCOLOR(value);
                    break;
                case "HEIGHT":
                    postEntry.setHEIGHT(Double.parseDouble(words[1]));
                    break;
                case "WIDTH":
                    postEntry.setWIDTH(Double.parseDouble(words[1]));
                    break;
                case "CoordinateX":
                    postEntry.setCoordinateX(Double.parseDouble(words[1]));
                    break;
                case "CoordinateY":
                    postEntry.setCoordinateY(Double.parseDouble(words[1]));
                    break;
                default:
                    break;
            }
        }
        message = "-----Successfully added-----\n" + postEntry.toString();
        postEntries.add(postEntry);
        return message;
    }

    private String handleGet(String[] data) {
        StringBuilder message = new StringBuilder();
        ArrayList<ArrayList<PostEntry>> postEntriesList = new ArrayList<>();
        for (String line : data) {
            line = line.trim();
            String[] words = line.split(" ");
            String value = line.substring(words[0].length()).trim();
            switch (words[0]) {
                case "ALL":
                    if (postEntries.size() == 0)
                        return "No posts found.";
                    for (PostEntry postEntry : postEntries) {
                        message.append(postEntry.toString());
                        message.append("\r\n");
                    }
                    return message.toString();
                case "STATUS":
                    if (value.length() > 0)
                        postEntriesList.add(Util.findByAttribute(postEntries, "STATUS", value));
                    break;
                case "MESSAGE":
                    if (value.length() > 0)
                        postEntriesList.add(Util.findByAttribute(postEntries, "MESSAGE", value));
                    break;
                case "COLOR":
                    if (value.length() > 0)
                        postEntriesList.add(Util.findByAttribute(postEntries, "COLOR", value));
                    break;
                case "HEIGHT":
                    if (Double.parseDouble(value) > 0)
                        postEntriesList.add(Util.findByAttribute(postEntries, "HEIGHT", value));
                    break;
                case "WIDTH":
                    if (Double.parseDouble(value) > 0)
                        postEntriesList.add(Util.findByAttribute(postEntries, "WIDTH", value));
                    break;
                case "CoordinateX":
                    if (Double.parseDouble(value) > 0)
                        postEntriesList.add(Util.findByAttribute(postEntries, "Coordinate X", value));
                    break;
                case "CoordinateY":
                    if (Double.parseDouble(value) > 0)
                        postEntriesList.add(Util.findByAttribute(postEntries, "Coordinate Y", value));
                    break;
            }
        }
        ArrayList<PostEntry> intersection = Util.intersection(postEntriesList);
        if (intersection == null)
            return "No Posts Found.";
        for (PostEntry postEntry : intersection) {
            message.append(postEntry.toString());
            message.append("\r\n");
        }

        return message.toString();
    }

    private String handleUpdate(String[] data) {
        String message;
        PostEntry foundPost = null;
        for (String line : data) {
            line = line.trim();
            String[] words = line.split(" ");
            String value = line.substring(words[0].length()).trim();
            switch (words[0]) {
                case "STATUS":
                    foundPost = Util.findBySTATUS(postEntries, value);
                    break;
                case "MESSAGE":
                    if (foundPost != null && value.length() > 0)
                        foundPost.setMESSAGE(value);
                    break;
                case "COLOR":
                    if (foundPost != null && value.length() > 0)
                        foundPost.setCOLOR(value);

                    break;
                case "HEIGHT":
                    if (foundPost != null && value.length() > 0)
                        if (Double.parseDouble(value) != 0)
                            foundPost.setHEIGHT(Double.parseDouble(value));
                    break;
                case "WIDTH":
                    if (foundPost != null && value.length() > 0)
                        if (Double.parseDouble(value) != 0)
                            foundPost.setWIDTH(Double.parseDouble(value));
                    break;
                case "CoordinateX":
                    if (foundPost != null && value.length() > 0)
                        if (Double.parseDouble(value) != 0)
                            foundPost.setCoordinateX(Double.parseDouble(value));
                    break;
                case "CoordinateY":
                    if (foundPost != null && value.length() > 0)
                        if (Double.parseDouble(value) != 0)
                            foundPost.setCoordinateY(Double.parseDouble(value));
                    break;
            }
        }
        if (foundPost != null)
            message = "-----Successfully updated-----\n" + foundPost.toString();
        else
            message = "ERROR: The post does not exist";
        return message;
    }

    private String handleRemove(String[] data) {
        String message;
        int removedCount = 0;
        ArrayList<ArrayList<PostEntry>> bookEntriesList = new ArrayList<>();
        for (String line : data) {
            line = line.trim();
            String[] words = line.split(" ");
            String value = line.substring(words[0].length()).trim();
            switch (words[0]) {
                case "STATUS":
                    if (value.length() > 0)
                        bookEntriesList.add(Util.findByAttribute(postEntries, "STATUS", value));
                    break;
                case "MESSAGE":
                    if (value.length() > 0)
                        bookEntriesList.add(Util.findByAttribute(postEntries, "MESSAGE", value));
                    break;
                case "COLOR":
                    if (value.length() > 0)
                        bookEntriesList.add(Util.findByAttribute(postEntries, "COLOR", value));
                    break;
                case "HEIGHT":
                    if (Double.parseDouble(value) > 0)
                        bookEntriesList.add(Util.findByAttribute(postEntries, "HEIGHT", value));
                    break;
                case "WIDTH":
                    if (Double.parseDouble(value) > 0)
                        bookEntriesList.add(Util.findByAttribute(postEntries, "WIDTH", value));
                    break;
                case "CoordinateX":
                    if (Double.parseDouble(value) > 0)
                        bookEntriesList.add(Util.findByAttribute(postEntries, "Coordinate X", value));
                    break;
                case "CoordinateY":
                    if (Double.parseDouble(value) > 0)
                        bookEntriesList.add(Util.findByAttribute(postEntries, "Coordinate Y", value));
                    break;
            }
        }
        ArrayList<PostEntry> intersection = Util.intersection(bookEntriesList);
        if (intersection != null)
            for (PostEntry postEntry : intersection) {
                postEntries.remove(postEntry);
                removedCount++;
            }
        message = "Removed " + removedCount + " books";
        return message;
    }

    private void listen() {
        String line, inMessage, outMessage;
        try {
            line = in.readLine();
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

                    /* PROCESS DATA START*/
                    outMessage = processData(inMessage.split("\n")).trim() + "\r\n\\EOF";
                }
                out.println(outMessage);
                line = in.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void disconnect() throws IOException {
        out.close();
        in.close();
        socket.close();
        System.out.println("Server thread [" + getName() + "] disconnected.");
        System.out.println("Active connections: " + (Thread.activeCount() - 2));
        this.interrupt();
    }
}
