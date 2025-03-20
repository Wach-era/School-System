package pages;

import java.util.List;

public class Parent {
    private int parentId;
    private String name;
    private String phoneNumber;
    private String email;
    private String gender;
    private String religion;
    private List<Integer> assignedStudents; // New field to hold assigned student IDs

    // Constructor
    public Parent(int parentId, String name, String phoneNumber, String email, String gender, String religion) {
        this.parentId = parentId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.religion = religion;
    }

    // Constructor with assigned students
    public Parent(int parentId, String name, String phoneNumber, String email, String gender, String religion, List<Integer> assignedStudents) {
        this.parentId = parentId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.religion = religion;
        this.assignedStudents = assignedStudents;
    }

    // Getter and Setter methods
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Integer> getAssignedStudents() {
        return assignedStudents;
    }

    public void setAssignedStudents(List<Integer> assignedStudents) {
        this.assignedStudents = assignedStudents;
    }

    // toString method
    @Override
    public String toString() {
        return "Parent{" +
                "parentId=" + parentId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", religion='" + religion + '\'' +
                ", assignedStudents=" + assignedStudents +
                '}';
    }
}
