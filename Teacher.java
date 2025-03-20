package pages;

import java.time.LocalDate;

public class Teacher {
    private int teacherId;
    private int nationalId;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String gender;
    private String religion;
    private String degrees;
    private String majors;
    private String institution;
    private LocalDate dateOfGraduation;
    private String position;
    private LocalDate dateOfHire;
    private double salary;
    private String emergencyContact;
    private String healthInformation;
    private String specialAccommodation;

    // Constructor
    public Teacher() {
    }
    
    public Teacher(int nationalId, String firstName, String middleName, String lastName) {
    	this.nationalId = nationalId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public Teacher(int teacherId,int nationalId, String firstName, String middleName, String lastName, LocalDate dateOfBirth, String email,
                   String phoneNumber, String gender, String religion, String degrees, String majors,
                   String institution, LocalDate dateOfGraduation, String position, LocalDate dateOfHire,
                   double salary, String emergencyContact, String healthInformation, String specialAccommodation) {
        this.teacherId = teacherId;
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.religion = religion;
        this.degrees = degrees;
        this.majors = majors;
        this.institution = institution;
        this.dateOfGraduation = dateOfGraduation;
        this.position = position;
        this.dateOfHire = dateOfHire;
        this.salary = salary;
        this.emergencyContact = emergencyContact;
        this.healthInformation = healthInformation;
        this.specialAccommodation = specialAccommodation;
    }

    // Getters and Setters

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
    public int getnationalId() {
        return nationalId;
    }

    public void setnationalId(int nationalId) {
        this.nationalId = nationalId;
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

    public String getDegrees() {
        return degrees;
    }

    public void setDegrees(String degrees) {
        this.degrees = degrees;
    }

    public String getMajors() {
        return majors;
    }

    public void setMajors(String majors) {
        this.majors = majors;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public LocalDate getDateOfGraduation() {
        return dateOfGraduation;
    }

    public void setDateOfGraduation(LocalDate dateOfGraduation) {
        this.dateOfGraduation = dateOfGraduation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDate getDateOfHire() {
        return dateOfHire;
    }

    public void setDateOfHire(LocalDate dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getHealthInformation() {
        return healthInformation;
    }

    public void setHealthInformation(String healthInformation) {
        this.healthInformation = healthInformation;
    }

    public String getSpecialAccommodation() {
        return specialAccommodation;
    }

    public void setSpecialAccommodation(String specialAccommodation) {
        this.specialAccommodation = specialAccommodation;
    }
}
