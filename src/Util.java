import java.util.ArrayList;

public class Util {

    public static ArrayList<BookEntry> findByAttribute(ArrayList<BookEntry> bookEntries, String attribute, String value) {
        ArrayList<BookEntry> foundSet = new ArrayList<>();
        for (BookEntry bookEntry : bookEntries) {
            switch (attribute) {
                case "STATUS":
                    if (bookEntry.getSTATUS().equals(value))
                        foundSet.add(bookEntry);
                    break;
                case "MESSAGE":
                    if (bookEntry.getMESSAGE().equals(value))
                        foundSet.add(bookEntry);
                    break;
                case "COLOR":
                    if (bookEntry.getCOLOR().equals(value))
                        foundSet.add(bookEntry);
                    break;
                case "HEIGHT":
                    if (bookEntry.getHEIGHT().equals(value))
                        foundSet.add(bookEntry);
                    // if (Double.toString(bookEntry.getWIDTH()).equals(value))
                    //     foundSet.add(bookEntry);
                    break;
                case "WIDTH":
                    if (Double.toString(bookEntry.getWIDTH()).equals(value))
                        foundSet.add(bookEntry);
                    break;
                case "CoordinateX":
                    if (Double.toString(bookEntry.getCoordinateX()).equals(value))
                        foundSet.add(bookEntry);
                    break;

                case "CoordinateY":
                    if (Double.toString(bookEntry.getCoordinateY()).equals(value))
                        foundSet.add(bookEntry);
                    break;
                default:
                    break;
            }
        }
        if (foundSet.size() == 0)
            return null;
        return foundSet;
    }

    public static BookEntry findBySTATUS(ArrayList<BookEntry> bookEntries, String STATUS) {
        for (BookEntry bookEntry : bookEntries)
            if (bookEntry.getSTATUS().equals(STATUS))
                return bookEntry;
        return null;
    }


    public static ArrayList<BookEntry> intersection(ArrayList<ArrayList<BookEntry>> bookEntriesList) {
        ArrayList<BookEntry> intersection = null;
        for (ArrayList<BookEntry> bookEntries : bookEntriesList) {
            intersection = intersection == null ? bookEntries : intersection;
            if (intersection == null) break;
            if (bookEntries != null)
                intersection.retainAll(bookEntries);
            else {
                intersection = null;
                break;
            }
        }
        return intersection;
    }

}
