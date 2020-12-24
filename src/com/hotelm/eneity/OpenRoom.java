package com.hotelm.eneity;

public class OpenRoom {
    private int id;
    private  String roomid;
    private String day;    //开房天数
    private String rday;   //实际天数
    private double price;  //价格
    private double depost; //收款
    private String status; //房间状态
    private double money;  //实际消费金额
    private String opentime; //开房时间

    public OpenRoom() {
    }
    public OpenRoom(int id, String roomid, String day, String rday, double price, double depost, String status, double money) {
        this.id = id;
        this.roomid = roomid;
        this.day = day;
        this.rday = rday;
        this.price = price;
        this.depost = depost;
        this.status = status;
        this.money = money;
    }
    //7属性构造
    public OpenRoom( String roomid, String day, String rday, double price, double depost, String status, double money) {
        this.roomid = roomid;
        this.day = day;
        this.rday = rday;
        this.price = price;
        this.depost = depost;
        this.status = status;
        this.money = money;
    }
    //加入开房时间
    public OpenRoom( String roomid, String day, String rday, double price, double depost, String status, double money,String opentime) {
        this.roomid = roomid;
        this.day = day;
        this.rday = rday;
        this.price = price;
        this.depost = depost;
        this.status = status;
        this.money = money;
        this.opentime =opentime;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRday() {
        return rday;
    }

    public void setRday(String rday) {
        this.rday = rday;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDepost() {
        return depost;
    }

    public void setDepost(double depost) {
        this.depost = depost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }
}
