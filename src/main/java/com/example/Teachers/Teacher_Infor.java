package com.example.Teachers;

public class Teacher_Infor {
    String userName;
    String ID;
    String department;
    String status;
    public Teacher_Infor() {}

    public Teacher_Infor(String userName, String ID, String department, String status) {
        this.userName = userName;
        this.ID = ID;
        this.department = department;
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
