package pages;

public class Patron {
    private int patronId;
    private int nationalId;
    private String clubName;

    public Patron(int patronId, int nationalId, String clubName) {
        this.patronId = patronId;
        this.nationalId = nationalId;
        this.clubName = clubName;
    }

    public int getPatronId() {
        return patronId;
    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public int getNationalId() {
        return nationalId;
    }

    public void setNatioalId(int nationalId) {
        this.nationalId = nationalId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
