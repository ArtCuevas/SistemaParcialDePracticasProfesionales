package com.sppp.model;

public class Student {
    private int idstudent;
    private String name;
    private String lastname;
    private String nrc;
    private String enrolment;
    private Project idproject;

    public Student() {}

    public Student(int idstudent, String name, String lastname, String nrc, String enrolment, Project idproject) {
        this.idstudent = idstudent;
        this.name = name;
        this.lastname = lastname;
        this.nrc = nrc;
        this.enrolment = enrolment;
        this.idproject = idproject;
    }

    public int getIdstudent() {
        return idstudent;
    }

    public void setIdstudent(int idstudent) {
        this.idstudent = idstudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getEnrolment() {
        return enrolment;
    }

    public void setEnrolment(String enrolment) {
        this.enrolment = enrolment;
    }

    public Project getIdproject() {
        return idproject;
    }

    public void setIdproject(Project idproject) {
        this.idproject = idproject;
    }
}
