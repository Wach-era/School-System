package pages;
import java.time.LocalDate;

public class Staff {
    private int staffId;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;  // Changed to LocalDate
    private String email;
    private String phoneNumber;
    private String gender;
    private String religion;
    private String title;
    private String departmentId;
    private LocalDate dateOfHire;  // Changed to LocalDate
    private String responsibilities;
    private String educationLevel;
    private String certification;
    private String experience;
    private String emergencyContact;
    private String healthInformation;
    private String specialAccommodation;

    public Staff(int staffId, String firstName, String middleName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber, String gender, String religion, String title, String departmentId, LocalDate dateOfHire, String responsibilities, String educationLevel, String certification, String experience, String emergencyContact, String healthInformation, String specialAccommodation) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.religion = religion;
        this.title = title;
        this.departmentId = departmentId;
        this.dateOfHire = dateOfHire;
        this.responsibilities = responsibilities;
        this.educationLevel = educationLevel;
        this.certification = certification;
        this.experience = experience;
        this.emergencyContact = emergencyContact;
        this.healthInformation = healthInformation;
        this.specialAccommodation = specialAccommodation;
    }
    
    public Staff(int staffId, String firstName, String middleName, String lastName, String title, String departmentId, LocalDate dateOfHire) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.title = title;
        this.departmentId = departmentId;
        this.dateOfHire = dateOfHire;
    }
 

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
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

    public LocalDate getDateOfBirth() {  // Changed to LocalDate
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {  // Changed to LocalDate
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public LocalDate getDateOfHire() {  // Changed to LocalDate
        return dateOfHire;
    }

    public void setDateOfHire(LocalDate dateOfHire) {  // Changed to LocalDate
        this.dateOfHire = dateOfHire;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
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
