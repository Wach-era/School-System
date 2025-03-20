package pages;

import java.time.LocalDate;

public class Student {
    private int studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String gender;
    private String religion;
    private LocalDate dateOfEnrollment;
    private String medicalHistory;
    private String emergencyContact;
    private String learningDisabilities;
    private String disabilityDetails;
    private String fullName;
    private double totalFeesDue;
    private double overpayment;

    
    public Student(int studentId, String firstName, String middleName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber, String gender, String religion, String medicalHistory, String emergencyContact, String learningDisabilities, LocalDate dateOfEnrollment, String disabilityDetails) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.religion = religion;
        this.medicalHistory = medicalHistory;
        this.emergencyContact = emergencyContact;
        this.learningDisabilities = learningDisabilities;
        this.dateOfEnrollment = dateOfEnrollment;
        this.disabilityDetails = disabilityDetails;
    }
    
    public Student(int studentId, String fullName) {
        this.studentId = studentId;
        this.fullName = fullName;
    }
    
    public Student(int studentId,  String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Student(int studentId,  String firstName, String lastName, LocalDate dateOfEnrollment) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfEnrollment = dateOfEnrollment;

    }
    
    public Student(int studentId, String firstName, String lastName, LocalDate dateOfEnrollment, double totalFeesDue, double overpayment) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfEnrollment = dateOfEnrollment;
        this.totalFeesDue = totalFeesDue;
        this.overpayment = overpayment;
    }
    
    
    public Student() {}

    
    // Getters and Setters for all fields

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public LocalDate getDateOfEnrollment() {
        return dateOfEnrollment;
    }

    public void setDateOfEnrollment(LocalDate dateOfEnrollment) {
        this.dateOfEnrollment = dateOfEnrollment;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getLearningDisabilities() {
        return learningDisabilities;
    }

    public void setLearningDisabilities(String learningDisabilities) {
        this.learningDisabilities = learningDisabilities;
    }

    public String getDisabilityDetails() {
        return disabilityDetails;
    }

    public void setDisabilityDetails(String disabilityDetails) {
        this.disabilityDetails = disabilityDetails;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public double getTotalFeesDue() {
        return totalFeesDue;
    }

    public void setTotalFeesDue(double totalFeesDue) {
        this.totalFeesDue = totalFeesDue;
    }
    
    public double getOverpayment() {
        return overpayment;
    }

    public void setOverpayment(double overpayment) {
        this.overpayment = overpayment;
    }
}
