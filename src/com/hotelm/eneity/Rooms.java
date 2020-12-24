package com.hotelm.eneity;

public class Rooms {
    private int id;
    private String roomid;
    private String floor;
    private Double price;
    private String Status;

    public Rooms() {
    }

    public Rooms(int id, String roomid, String floor, Double price, String status) {
        this.id = id;
        this.roomid = roomid;
        this.floor = floor;
        this.price = price;
        this.Status = status;
    }
    public Rooms( String roomid, String floor, Double price, String status) {
        this.roomid = roomid;
        this.floor = floor;
        this.price = price;
        this.Status = status;
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

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Rooms{" +
                "id=" + id +
                ", roomid='" + roomid + '\'' +
                ", floor='" + floor + '\'' +
                ", price=" + price +
                ", Status='" + Status + '\'' +
                '}';
    }
}
