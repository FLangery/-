package com.hotelm.dao;

import com.hotelm.eneity.Rooms;
import com.hotelm.util.JDBC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomDao {
        //房间新增
        public int addRoom(Rooms rooms) {
            int result = 0;
            String sql = "insert into room(roomid,floor,price,status)"
                    + "values(?,?,?,?)";
            //商品的信息,集合元素的顺序与sql语句中的属性要能对应
            List<Object> list = new ArrayList<Object>();
            list.add(rooms.getRoomid());
            list.add(rooms.getFloor());
            list.add(rooms.getPrice());
            list.add(rooms.getStatus());
            //System.out.println("添加房间成功");
            //定义数据库操作类的对象
            JDBC jdbc = new JDBC();
            //先查询id是否存在，
            List<Object> conList = new ArrayList<>();
            conList.add(rooms.getId());
            List<Map<String, Object>> resultList = jdbc.queryByCondition("select * from room where id=? ", conList);
            if (resultList != null && resultList.size() > 0) {
                System.out.println("该数据已存在");
            } else {
                result = jdbc.insert(sql, list);
                System.out.println(result == 1?"添加房间成功":"添加失败");
            }
            jdbc.close();
            return result;
        }

        //修改房间信息
        public int updateRoom(Rooms rooms) {
            String sql = "update room set roomid=?,floor=?,price=?,status=? where roomid=?";
            List<Object> list = new ArrayList<Object>();
            //good对象属性值封装成集合元素
            list.add(rooms.getRoomid());
            list.add(rooms.getFloor());
            list.add(rooms.getPrice());
            list.add(rooms.getStatus());
            list.add(rooms.getRoomid());
            //定义数据库操作类的对象
            JDBC jdbc = new JDBC();
           // System.out.println("修改成功");
            int result = jdbc.update(sql, list);
            System.out.print(result == 1?"修改成功":"修改失败");
            jdbc.close();
            return result;
        }
        //传数组修改房间信息
        public int uRoom(List<Object> conList) {
        String sql = "update room set status=? where roomid=?";
        //定义数据库操作类的对象
        JDBC jdbc = new JDBC();
        // System.out.println("修改成功");
        int result = jdbc.update(sql, conList);
        jdbc.close();
        return result;
    }
        //删除房间
        public void deleteRoom(int id) {
            String sql = "delete from room where id=?";
            //定义数据库操作类的对象
            JDBC jdbc = new JDBC();
            int result = jdbc.delete(sql, id);
            System.out.print("result:" + result);
            if (result != 1) {
                System.out.println("删除房间失败");
            } else {
                System.out.println("删除房间成功");
            }
            jdbc.close();
        }

        //商品全查
        public List<Map<String, Object>> queryAllGoods() {
            String sql = "select * from room";
            JDBC jdbc = new JDBC();
            List<Map<String, Object>> list = jdbc.queryAll(sql);
            jdbc.close();
            return list;
        }

        //商品条件查询
        public List<Map<String, Object>> queryGoodsByCondition(List<Object> conList) {
            String sql = "select * from room where roomid=?";
            JDBC sqlHepler = new JDBC();
            List<Map<String, Object>> list = sqlHepler.queryByCondition(sql, conList);
            System.out.print("条件查询");
            sqlHepler.close();
            return list;
        }

        //商品模糊查询
        public List<Map<String, Object>> MHqueryGoodsByCondition(String name) {
            String sql = "select * from room where name like ?";
            System.out.print("模糊查询");
            JDBC jdbc = new JDBC();
            List<Map<String, Object>> list = jdbc.MHQueryByCondition(sql, name);
            jdbc.close();
            return list;
        }
}
