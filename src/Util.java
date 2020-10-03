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
        if (foundSet.size() == 0)
            return null;
        return foundSet;
    }

    public static BookEntry findByISBN(ArrayList<BookEntry> bookEntries, String ISBN) {
        for (BookEntry bookEntry : bookEntries)
            if (bookEntry.getISBN().equals(ISBN))
                return bookEntry;
        return null;
    }

    public static int calculateISBNDigit(String ISBN) {
        int calculatedDigit = 0;
        for (int i = 0; i < 12; i++) {
            final int i1 = Integer.parseInt(ISBN.toCharArray()[i] + "");
            if (i % 2 == 0)
                calculatedDigit += i1;
            else
                calculatedDigit += 3 * i1;
        }
        calculatedDigit = 10 - (calculatedDigit % 10);
        if (calculatedDigit == 10) return 0;
        return calculatedDigit;
    }

    public static ArrayList<BookEntry> intersection(ArrayList<ArrayList<BookEntry>> bookEntriesList) {
        ArrayList<BookEntry> intersection = null;
        for (ArrayList<BookEntry> bookEntries : bookEntriesList) {
            if (intersection == null) {
                if (bookEntries != null)
                    intersection = bookEntries;
            } else {
                if (bookEntries != null)
                    intersection.retainAll(bookEntries);
                else {
                    intersection = null;
                    break;
                }
            }
        }
        return intersection;
    }

}
