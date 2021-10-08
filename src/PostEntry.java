public class PostEntry {
    private String STATUS = "";
    private String MESSAGE = "";
    private String COLOR = "";
    private Double HEIGHT = 0.0;
    private Double WIDTH = 0.0;
    private Double CoordinateX = 0.0;
    private Double CoordinateY = 0.0;

    @Override
    public String toString() {
        return "STATUS: " + STATUS +
                "\nMESSAGE: " + MESSAGE +
                "\nCOLOR: " + COLOR +
                "\nHEIGHT: " + HEIGHT +
                "\nWIDTH: " + WIDTH +
                "\nLower Coordinates (X,Y): " + CoordinateX + "," + CoordinateY + "\r\n";
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(String COLOR) {
        this.COLOR = COLOR;
    }

    public Double getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(Double HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public Double getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(Double WIDTH) {
        this.WIDTH = WIDTH;
    }

    public Double getCoordinateX() {
        return CoordinateX;
    }

    public void setCoordinateX(Double CoordinateX) {
        this.CoordinateX = CoordinateX;
    }

    public Double getCoordinateY() {
        return CoordinateY;
    }

    public void setCoordinateY(Double CoordinateY) {
        this.CoordinateY = CoordinateY;
    }
}
