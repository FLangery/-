package com.hotelm.dao;

import com.hotelm.eneity.OpenRoom;
import com.hotelm.eneity.Rooms;
import com.hotelm.util.JDBC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenRoomDao {
    //开房记录新增
    public int addOpenRoom(OpenRoom openRoom) {
        int result = 0;
        String sql = "insert into openroom(roomid , day, rday, price, depost,status, money,opentime)"
                + "values(?,?,?,?,?,?,?,?)";
        String sql1 = "update room set status=? where roomid=?";
        //开房的信息,集合元素的顺序与sql语句中的属性要能对应
        List<Object> list = new ArrayList<Object>();
        list.add(openRoom.getRoomid());
        list.add(openRoom.getDay());
        list.add(openRoom.getRday());
        list.add(openRoom.getPrice());
        list.add(openRoom.getDepost());
        list.add(openRoom.getStatus());
        list.add(openRoom.getMoney());
        list.add(openRoom.getOpentime());
        //定义数据库操作类的对象
        JDBC jdbc = new JDBC();
        //先查询房间是否存在，
        List<Object> conList = new ArrayList<>();
        List<Object> conList1 = new ArrayList<>();
        conList.add(openRoom.getRoomid());
        conList1.add("占用");
        conList1.add(openRoom.getRoomid());
        List<Map<String, Object>> resultList = jdbc.queryByCondition("select status from room where roomid=? ", conList);
        Map<String, Object> map = resultList.get(0);
        if (resultList != null && resultList.size() > 0 && map.get("status").toString().equals("空闲")) {
            result = jdbc.insert(sql, list);
            jdbc.update(sql1, conList1);
        }
        else if(map.get("status").toString().equals("占用")){
            System.out.println("该房间已被占用");
            result = 2;
        }else{
            System.out.println("该房间不存在");
            result =0;
        }
        jdbc.close();
        return result;
    }
    //开房管理
    public int updateOpenRoom(OpenRoom openRoom) {
        String sql = "update openRoom set status=? where roomid=?";
        List<Object> list = new ArrayList<Object>();
        //good对象属性值封装成集合元素
        list.add(openRoom.getStatus());
        list.add(openRoom.getRoomid());
        //定义数据库操作类的对象
        JDBC jdbc = new JDBC();
        // System.out.println("修改成功");
        int result = jdbc.update(sql, list);
        System.out.print(result == 1?"开房信息修改成功":"开房信息修改失败");
        jdbc.close();
        return result;
    }
    //删除开房记录
    public void deleteRoom(int id) {
        String sql = "delete from openroom where id=?";
        //定义数据库操作类的对象
        JDBC jdbc = new JDBC();
        int result = jdbc.delete(sql, id);
        System.out.print(result == 1?"删除记录成功" :"删除记录失败");
        jdbc.close();
    }

    //开房记录全查
    public List<Map<String, Object>> queryAllOpen() {
        String sql = "select * from openroom";
        JDBC jdbc = new JDBC();
        List<Map<String, Object>> list = jdbc.queryAll(sql);
        jdbc.close();
        return list;
    }
    //开房记录条件查询
    public List<Map<String, Object>> querytimeOpen(String sql,List<Object> conList) {
        //String sql = "select * from openroom";
        JDBC jdbc = new JDBC();
        List<Map<String, Object>> list = jdbc.queryByCondition(sql,conList);
        jdbc.close();
        return list;
    }
    //修改房间信息
    public void setORoomS(List<Object> conList){
       // String sql = "update room set roomid=?,floor=?,price=?,status=? where roomid=?";
        String sql = "update openroom set status=? where roomid=?";
        //定义数据库操作类的对象
        JDBC jdbc = new JDBC();
        int result = jdbc.update(sql,conList);
        jdbc.close();
    }
    //添加退房时间
    public void setORoomOutT(List<Object> conList){
        // String sql = "update room set roomid=?,floor=?,price=?,status=? where roomid=?";
        String sql = "update openroom set outtime=? where roomid=?";
        //定义数据库操作类的对象
        JDBC jdbc = new JDBC();
        int result = jdbc.update(sql,conList);
        jdbc.close();
    }
}
