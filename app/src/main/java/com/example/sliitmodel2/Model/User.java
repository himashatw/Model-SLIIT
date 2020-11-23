package com.example.sliitmodel2.Model;

public class User {
    public String UserName, DateOfBirth, Gender, Password;

    public User(String userName, String dateOfBirth, String gender, String password) {
        UserName = userName;
        DateOfBirth = dateOfBirth;
        Gender = gender;
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
