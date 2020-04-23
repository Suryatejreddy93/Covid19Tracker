package model;

public class VirusData {
    private String state;
    private String country;
    private String totalCases;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(String totalCases) {
        this.totalCases = totalCases;
    }

    @Override
    public String toString() {
        return "VirusData{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", totalCases='" + totalCases + '\'' +
                '}';
    }
}
