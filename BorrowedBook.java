package pages;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BorrowedBook {
    private int borrowId;
    private int borrowerRefId;
    private String bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate; // Add this field
    private boolean lost;
    private BigDecimal overdueFine;
    private List<Fine> fines; 
    

    // Constructor, getters, and setters
    public BorrowedBook(int borrowId, int borrowerRefId, String bookId, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate, boolean lost, BigDecimal overdueFine) {
        this.borrowId = borrowId;
        this.borrowerRefId = borrowerRefId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate; // Initialize this field
        this.lost = lost;
        this.overdueFine = overdueFine;
    }
    
    public BorrowedBook(int borrowId, int borrowerRefId, String bookId, LocalDate borrowDate, LocalDate dueDate, boolean lost, BigDecimal overdueFine) {
        this.borrowId = borrowId;
        this.borrowerRefId = borrowerRefId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.lost = lost;
        this.overdueFine = overdueFine;
    }
    
    public BorrowedBook(int borrowId, String bookId, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate, boolean lost, BigDecimal overdueFine) {
        this.borrowId = borrowId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.lost = lost;
        this.overdueFine = overdueFine;
    }
    // Getters and setters

	public List<Fine> getFines() {
        return fines;
    }

    public void setFines(List<Fine> fines) {
        this.fines = fines;
    }
    
    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public int getBorrowerRefId() {
        return borrowerRefId;
    }

    public void setBorrowerRefId(int borrowerRefId) {
        this.borrowerRefId = borrowerRefId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public BigDecimal getOverdueFine() {
        return overdueFine;
    }

    public void setOverdueFine(BigDecimal overdueFine) {
        this.overdueFine = overdueFine;
    }
}
