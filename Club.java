package pages;

public class Club {
    private String clubName;
    private String patronName;
    private String patronNationalId;

    // Constructor for all fields
    public Club(String clubName, String patronName, String patronNationalId) {
        this.clubName = clubName;
        this.patronName = patronName;
        this.patronNationalId = patronNationalId;
    }

    // Constructor for only clubName
    public Club(String clubName) {
        this.clubName = clubName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getPatronName() {
        return patronName;
    }

    public void setPatronName(String patronName) {
        this.patronName = patronName;
    }

    public String getPatronNationalId() {
        return patronNationalId;
    }

    public void setPatronNationalId(String patronNationalId) {
        this.patronNationalId = patronNationalId;
    }
}
