package pages;

public class FeeStructure {
    private int feeId;
    private String trimesterId;
    private String description;
    private double amount;

    // Default constructor
    public FeeStructure() {
    }

    // Parameterized constructor
    public FeeStructure(int feeId, String trimesterId, String description, double amount) {
        this.feeId = feeId;
        this.trimesterId = trimesterId;
        this.description = description;
        this.amount = amount;
    }

    public FeeStructure(String trimesterId, String description, double amount) {
        this.trimesterId = trimesterId;
        this.description = description;
        this.amount = amount;
    }

    // Getters and setters
    public int getFeeId() {
        return feeId;
    }

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }

    public String getTrimesterId() {
        return trimesterId;
    }

    public void setTrimesterId(String trimesterId) {
        this.trimesterId = trimesterId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
