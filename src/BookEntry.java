public class BookEntry {
    private String ISBN = "";
    private String TITLE = "";
    private String AUTHOR = "";
    private String PUBLISHER = "";
    private int YEAR = 0;

    @Override
    public String toString() {
        return "ISBN: " + ISBN +
                "\nTITLE: " + TITLE +
                "\nAUTHOR: " + AUTHOR +
                "\nPUBLISHER: " + PUBLISHER +
                "\nYEAR: " + YEAR + "\r\n";
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public void setAUTHOR(String AUTHOR) {
        this.AUTHOR = AUTHOR;
    }

    public String getPUBLISHER() {
        return PUBLISHER;
    }

    public void setPUBLISHER(String PUBLISHER) {
        this.PUBLISHER = PUBLISHER;
    }

    public int getYEAR() {
        return YEAR;
    }

    public void setYEAR(int YEAR) {
        this.YEAR = YEAR;
    }
}
