package com.hotelm.eneity;

public class Client {
    private Integer id;
    private String name;
    private String card;
    private String sex;
    private Integer age;

    public Client(String name, String card, String sex, Integer age) {
        this.name = name;
        this.card = card;
        this.sex = sex;
        this.age = age;
    }

    public Client() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
