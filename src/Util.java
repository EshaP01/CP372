import java.util.ArrayList;

public class Util {
    public static ArrayList<BookEntry> findByAttribute(ArrayList<BookEntry> bookEntries, String attribute, String value) {
        ArrayList<BookEntry> foundSet = new ArrayList<>();
        for (BookEntry bookEntry : bookEntries) {
            switch (attribute) {
                case "ISBN":
                    if (bookEntry.getISBN().equals(value))
                        foundSet.add(bookEntry);
                    break;
                case "TITLE":
                    if (bookEntry.getTITLE().equals(value))
                        foundSet.add(bookEntry);
                    break;
                case "AUTHOR":
                    if (bookEntry.getAUTHOR().equals(value))
                        foundSet.add(bookEntry);
                    break;
                case "PUBLISHER":
                    if (bookEntry.getPUBLISHER().equals(value))
                        foundSet.add(bookEntry);
                case "YEAR":
                    if (Integer.toString(bookEntry.getYEAR()).equals(value))
                        foundSet.add(bookEntry);
                default:
                    break;
            }
        }
        return foundSet;
    }

}
