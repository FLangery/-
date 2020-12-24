package com.hotelm.eneity;

import java.util.Date;

public class Employee {
    private int id;
    private String employeeid;
    private String name;
    private int card;
    private String sex;
    private String joind;
    private String qx;

    public Employee() {
    }
    public Employee(int id, String employeeid, String name, int card, String sex, String joind, String qx) {
        this.id = id;
        this.employeeid = employeeid;
        this.name = name;
        this.card = card;
        this.sex = sex;
        this.joind = joind;
        this.qx = qx;
    }
    public Employee( String employeeid, String name, int card, String sex, String joind, String qx) {
        this.employeeid = employeeid;
        this.name = name;
        this.card = card;
        this.sex = sex;
        this.joind = joind;
        this.qx = qx;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmployeeid() {
        return employeeid;
    }
    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCard() {
        return card;
    }
    public void setCard(int card) {
        this.card = card;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getJoind() {
        return joind;
    }
    public void setJoind(String joind) {
        this.joind = joind;
    }
    public String getQx() {
        return qx;
    }
    public void setQx(String qx) {
        this.qx = qx;
    }
}
