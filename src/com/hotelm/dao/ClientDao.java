package com.hotelm.dao;

import com.hotelm.eneity.Client;
import com.hotelm.eneity.Employee;
import com.hotelm.util.JDBC;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientDao {
    private JDBC jdbc =new JDBC();
    //添加客户信息
    public int addClient(Client client){
        int result = 0;
        String sql = "insert into client(name, card, sex, age) value (?,?,?,?)";
        //商品的信息,集合元素的顺序与sql语句中的属性要能对应
        List<Object> list = new ArrayList<>();
        list.add(client.getName());
        list.add(client.getCard());
        list.add(client.getSex());
        list.add(client.getAge());
        result = jdbc.insert(sql,list);
        return result;
    }
    //查询全部客户信息
    public List<Map<String,Object>> queryClient(){
        String sql = "select * from client";
        List<Map<String,Object>> list = jdbc.queryAll(sql);
        jdbc.close();
        return list;
    }
    //修改员工信息
    public int updateClient(Client client){
        String sql = "update client set name=?,card=?,sex=?,age=? where id=?";
        List<Object> list = new ArrayList<Object>();
        //good对象属性值封装成集合元素
        list.add(client.getName());
        list.add(client.getCard());
        list.add(client.getSex());
        list.add(client.getAge());
        list.add(client.getId());
        int result = jdbc.update(sql, list);
        jdbc.close();
        return result;
    }
    //删除用户
    public void deleteClient(int id){
        String sql ="delete from client where id=?";
        int result = jdbc.delete(sql,id);
        System.out.print("result:"+result);
        if(result!=1){
            System.out.print("删除房间失败");
        }else{
            System.out.print("删除房间成功");
        }
        jdbc.close();
    }

}
