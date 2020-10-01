public class BookEntry {
    private String ISBN;
    private String TITLE;
    private String AUTHOR;
    private String PUBLISHER;
    private int YEAR;

    public BookEntry(String ISBN, String TITLE, String AUTHOR, String PUBLISHER, int YEAR) {
        this.ISBN = ISBN;
        this.TITLE = TITLE;
        this.AUTHOR = AUTHOR;
        this.PUBLISHER = PUBLISHER;
        this.YEAR = YEAR;
    }

    @Override
    public String toString() {
        return "BookEntry{" +
                "ISBN='" + ISBN + '\'' +
                ", TITLE='" + TITLE + '\'' +
                ", AUTHOR='" + AUTHOR + '\'' +
                ", PUBLISHER='" + PUBLISHER + '\'' +
                ", YEAR=" + YEAR +
                '}';
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
