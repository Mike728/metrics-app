package info.mike.dynatraceapp.web.transfer;

public class MidEntry {

    private String date;
    private Double mid;

    public MidEntry() {
    }

    public MidEntry(String date, Double mid) {
        this.date = date;
        this.mid = mid;
    }

    public String getDate() {
        return date;
    }

    public Double getMid() {
        return mid;
    }
}
