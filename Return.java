package pages;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Return {
    private int returnId;
    private int borrowId;
    private LocalDate returnDate;
    private BigDecimal fineAmount;

    // Constructor, getters, and setters
    public Return(int returnId, int borrowId, LocalDate returnDate, BigDecimal fineAmount) {
        this.returnId = returnId;
        this.borrowId = borrowId;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
    }

    public int getReturnId() {
        return returnId;
    }

    public void setReturnId(int returnId) {
        this.returnId = returnId;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }
}
