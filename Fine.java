package pages;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Fine {
    private int fineId;
    private int borrowId;
    private BigDecimal fineAmount;
    private LocalDate fineDate;
    private boolean paid;

    public Fine(int fineId, int borrowId, BigDecimal fineAmount, LocalDate fineDate, boolean paid) {
        this.fineId = fineId;
        this.borrowId = borrowId;
        this.fineAmount = fineAmount;
        this.fineDate = fineDate;
        this.paid = paid;
    }

    public int getFineId() {
        return fineId;
    }

    public void setFineId(int fineId) {
        this.fineId = fineId;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    public LocalDate getFineDate() {
        return fineDate;
    }

    public void setFineDate(LocalDate fineDate) {
        this.fineDate = fineDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
