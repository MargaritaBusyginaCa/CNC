package com.example.cnc.supporters;

/**
 * Created by NyNguyen on Feb 10, 2021
 */

public class User {

    private String studentID;
    private String email;
    private String password;

    /*set & get student ID methods */
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public String getStudentID() {
        return studentID;
    }

    /*set & get email methods */
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    /*set & get password methods */
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

}