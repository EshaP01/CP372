import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private final ArrayList<BookEntry> bookEntries;

    public ServerThread(String name, Socket socket, ArrayList<BookEntry> bookEntries) {
        super(name);
        this.socket = socket;
        this.bookEntries = bookEntries;
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
            case "SUBMIT":
                return handleSubmit(data);
            case "GET":
                return handleGet(data);
            case "UPDATE":
                return handleUpdate(data);
            case "REMOVE":
                return handleRemove(data);
            default:
                return "ERROR: Invalid request please do one of [SUBMIT, GET, UPDATE, REMOVE]";
        }
    }

    private String handleSubmit(String[] data) {
        String message;
        BookEntry bookEntry = new BookEntry();
        for (String line : data) {
            line = line.trim();
            String[] words = line.split(" ");
            String value;
            switch (words[0]) {
                case "STATUS":
                    if (Util.findBySTATUS(bookEntries, words[1]) != null) {
                        message = "ERROR: Book already exists";
                        return message;
                    }
                    bookEntry.setSTATUS(words[1]);
                    break;
                case "MESSAGE":
                    value = line.substring(words[0].length()).trim();
                    bookEntry.setMESSAGE(value);
                    break;
                case "COLOR":
                    value = line.substring(words[0].length()).trim();
                    bookEntry.setCOLOR(value);
                    break;
                case "HEIGHT":
                    bookEntry.setHEIGHT(Double.parseDouble(words[1]));
                    break;
                case "WIDTH":
                    bookEntry.setWIDTH(Double.parseDouble(words[1]));
                    break;
                case "Coordinate X":
                    bookEntry.setCoordinateX(Double.parseDouble(words[1]));
                    break;
                case "Coordinate Y":
                    bookEntry.setCoordinateY(Double.parseDouble(words[1]));
                    break;
                default:
                    break;
            }
        }
        message = "-----Successfully added-----\n" + bookEntry.toString();
        bookEntries.add(bookEntry);
        return message;
    }

    private String handleGet(String[] data) {
        StringBuilder message = new StringBuilder();
        ArrayList<ArrayList<BookEntry>> bookEntriesList = new ArrayList<>();
        for (String line : data) {
            line = line.trim();
            String[] words = line.split(" ");
            String value = line.substring(words[0].length()).trim();
            switch (words[0]) {
                case "ALL":
                    if (bookEntries.size() == 0)
                        return "No books found.";
                    for (BookEntry bookEntry : bookEntries) {
                        message.append(bookEntry.toString());
                        message.append("\r\n");
                    }
                    return message.toString();
                case "STATUS":
                    if (value.length() > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "ISBN", value));
                    break;
                case "MESSAGE":
                    if (value.length() > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "TITLE", value));
                    break;
                case "COLOR":
                    if (value.length() > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "AUTHOR", value));
                    break;
                case "HEIGHT":
                    if (Double.parseDouble(value) > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "HEIGHT", value));
                    break;
                case "WIDTH":
                    if (Double.parseDouble(value) > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "WIDTH", value));
                    break;
                case "Coordinate X":
                    if (Double.parseDouble(value) > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "Coordinate X", value));
                    break;
                case "Coordinate Y":
                    if (Double.parseDouble(value) > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "Coordinate Y", value));
                    break;
            }
        }
        ArrayList<BookEntry> intersection = Util.intersection(bookEntriesList);
        if (intersection == null)
            return "No books found.";
        for (BookEntry bookEntry : intersection) {
            message.append(bookEntry.toString());
            message.append("\r\n");
        }

        return message.toString();
    }

    private String handleUpdate(String[] data) {
        String message;
        BookEntry foundBook = null;
        for (String line : data) {
            line = line.trim();
            String[] words = line.split(" ");
            String value = line.substring(words[0].length()).trim();
            switch (words[0]) {
                case "STATUS":
                    foundBook = Util.findBySTATUS(bookEntries, value);
                    break;
                case "MESSAGE":
                    if (foundBook != null && value.length() > 0)
                        foundBook.setMESSAGE(value);
                    break;
                case "COLOR":
                    if (foundBook != null && value.length() > 0)
                        foundBook.setCOLOR(value);

                    break;
                case "HEIGHT":
                    if (foundBook != null && value.length() > 0)
                        if (Double.parseDouble(value) != 0)
                            foundBook.setHEIGHT(Double.parseDouble(value));
                    break;
                case "WIDTH":
                    if (foundBook != null && value.length() > 0)
                        if (Double.parseDouble(value) != 0)
                            foundBook.setWIDTH(Double.parseDouble(value));
                    break;
                case "Coordinate X":
                    if (foundBook != null && value.length() > 0)
                        if (Double.parseDouble(value) != 0)
                            foundBook.setCoordinateX(Double.parseDouble(value));
                    break;
                case "Coordinate Y":
                    if (foundBook != null && value.length() > 0)
                        if (Double.parseDouble(value) != 0)
                            foundBook.setCoordinateY(Double.parseDouble(value));
                    break;
            }
        }
        if (foundBook != null)
            message = "-----Successfully updated-----\n" + foundBook.toString();
        else
            message = "ERROR: The book does not exist";
        return message;
    }

    private String handleRemove(String[] data) {
        String message;
        int removedCount = 0;
        ArrayList<ArrayList<BookEntry>> bookEntriesList = new ArrayList<>();
        for (String line : data) {
            line = line.trim();
            String[] words = line.split(" ");
            String value = line.substring(words[0].length()).trim();
            switch (words[0]) {
                case "STATUS":
                    if (value.length() > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "STATUS", value));
                    break;
                case "MESSAGE":
                    if (value.length() > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "MESSAGE", value));
                    break;
                case "COLOR":
                    if (value.length() > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "COLOR", value));
                    break;
                case "HEIGHT":
                    if (Double.parseDouble(value) > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "HEIGHT", value));
                    break;
                case "WIDTH":
                    if (Double.parseDouble(value) > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "WIDTH", value));
                    break;
                case "Coordinate X":
                    if (Double.parseDouble(value) > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "Coordinate X", value));
                    break;
                case "Coordinate Y":
                    if (Double.parseDouble(value) > 0)
                        bookEntriesList.add(Util.findByAttribute(bookEntries, "Coordinate Y", value));
                    break;
            }
        }
        ArrayList<BookEntry> intersection = Util.intersection(bookEntriesList);
        if (intersection != null)
            for (BookEntry bookEntry : intersection) {
                bookEntries.remove(bookEntry);
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
