package com.example.cnc.supporters;

/**
 * Created by Lai Shan Law on March 13, 2021
 */

public class Timestamp {
    private String studentID;
    private String assmntCode;
    private String timestamp;

    public Timestamp(){
        this.studentID = studentID;
        this.assmntCode = assmntCode;
        this.timestamp = timestamp;
    }

    public String getStudentID() {
        return studentID;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
/** assmntCode:
    01 = Orientation completed
    11 = Checklist1 completed
    12 = Assignment1 started
    13 = Assignment1 submitted
    21 = Checklist2 completed
    22 = Assignment2 started
    23 = Assignment2 submitted
 */

    public String getAssmntCode() {
        return assmntCode;
    }
    public void setAssmntCode(String assmntCode) {
        this.assmntCode = assmntCode;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}