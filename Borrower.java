package pages;

public class Borrower {
    private int Id;
    private String borrowerType;
    private int borrowerRefId;

    public Borrower() {}

    public Borrower(int Id, String borrowerType, int borrowerRefId) {
        this.Id = Id;
        this.borrowerType = borrowerType;
        this.borrowerRefId = borrowerRefId;
    }

    // Getters and setters
    public int getId() {
        return Id;
    }

    public void setId(int borrowerId) {
        this.Id = borrowerId;
    }

    public String getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(String borrowerType) {
        this.borrowerType = borrowerType;
    }

    public int getBorrowerRefId() {
        return borrowerRefId;
    }

    public void setBorrowerRefId(int borrowerRefId) {
        this.borrowerRefId = borrowerRefId;
    }

    @Override
    public String toString() {
        return "Borrower{" +
                "borrowerId=" + Id +
                ", borrowerType='" + borrowerType + '\'' +
                ", borrowerRefId=" + borrowerRefId +
                '}';
    }
}
