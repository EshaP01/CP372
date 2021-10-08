import java.util.ArrayList;

public class Util {

    public static ArrayList<PostEntry> findByAttribute(ArrayList<PostEntry> postEntries, String attribute, String value) {
        ArrayList<PostEntry> foundSet = new ArrayList<>();
        for (PostEntry postEntry : postEntries) {
            switch (attribute) {
                case "STATUS":
                    if (postEntry.getSTATUS().equals(value))
                        foundSet.add(postEntry);
                    break;
                case "MESSAGE":
                    if (postEntry.getMESSAGE().equals(value))
                        foundSet.add(postEntry);
                    break;
                case "COLOR":
                    if (postEntry.getCOLOR().equals(value))
                        foundSet.add(postEntry);
                    break;
                case "HEIGHT":
                    if (postEntry.getHEIGHT().equals(value))
                        foundSet.add(postEntry);
                    break;
                case "WIDTH":
                    if (postEntry.getWIDTH().equals(value))
                    foundSet.add(postEntry);
                    break;

                case "CoordinateX":
                    if (postEntry.getCoordinateX().equals(value))
                        foundSet.add(postEntry);
                    break;

                case "CoordinateY":
                    if (postEntry.getCoordinateX().equals(value))
                        foundSet.add(postEntry);
                    break;
                default:
                    break;
            }
        }
        if (foundSet.size() == 0)
            return null;
        return foundSet;
    }

    public static PostEntry findBySTATUS(ArrayList<PostEntry> postEntries, String STATUS) {
        for (PostEntry postEntry : postEntries)
            if (postEntry.getSTATUS().equals(STATUS))
                return postEntry;
        return null;
    }

    public static PostEntry findByMESSAGE(ArrayList<PostEntry> postEntries, String MESSAGE) {
        for (PostEntry postEntry : postEntries)
            if (postEntry.getMESSAGE().equals(MESSAGE))
                return postEntry;
        return null;
    }


    public static ArrayList<PostEntry> intersection(ArrayList<ArrayList<PostEntry>>postEntriesList) {
        ArrayList<PostEntry> intersection = null;
        for (ArrayList<PostEntry> postEntries : postEntriesList) {
            intersection = intersection == null ? postEntries : intersection;
            if (intersection == null) break;
            if (postEntries != null)
                intersection.retainAll(postEntries);
            else {
                intersection = null;
                break;
            }
        }
        return intersection;
    }

}
