package com.hotelm.dao;

import com.hotelm.eneity.Employee;
import com.hotelm.util.JDBC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeDao {
    /**
     * 对模块进行数据操作--CRUD
     * @author LENOVO
     *
     */
        private JDBC jdbc;

        //员工新增
        public int addEmployees(Employee employees){
            int result = 0;
            String sql = "insert into employee(employeeid,name,card,sex,joind,qx)"
                    + "values(?,?,?,?,?,?)";
            //商品的信息,集合元素的顺序与sql语句中的属性要能对应
            List<Object> list = new ArrayList<Object>();
            //list.add(employees.getId());
            list.add(employees.getEmployeeid());
            list.add(employees.getName());
            list.add(employees.getCard());
            list.add(employees.getSex());
            list.add(employees.getJoind());
            list.add(employees.getQx());
            System.out.println("添加员工");
            //定义数据库操作类的对象
           jdbc = new JDBC();
            //先查询id是否存在，
            List<Object> conList = new ArrayList<>();
            conList.add(employees.getEmployeeid());
            List<Map<String,Object>> resultList = jdbc.queryByCondition("select * from employee where Employeeid=?", conList);
            if(resultList!=null&&resultList.size()>0)
                System.out.println("该数据已存在");
            else{
                result = jdbc.insert(sql, list);
            }
            jdbc.close();
            return result;

        }
        //修改员工信息
        public int updateEmployees(Employee employees){
            String sql = "update employee set employeeid=?,name=?,card=?,sex=?,joind=?,qx=? where employeeid=?";
            List<Object> list = new ArrayList<Object>();
            //good对象属性值封装成集合元素
            list.add(employees.getEmployeeid());
            list.add(employees.getName());
            list.add(employees.getCard());
            list.add(employees.getSex());
            list.add(employees.getJoind());
            list.add(employees.getQx());
            list.add(employees.getEmployeeid());
            //定义数据库操作类的对象
             jdbc = new JDBC();
            //System.out.println("update");
            int result = jdbc.update(sql, list);
            //System.out.print("result:"+result);
            jdbc.close();
            return result;
        }
        //删除用户
        public void deleteEmployees(int id){
            String sql ="delete from employee where id=?";
            //定义数据库操作类的对象
            jdbc = new JDBC();
            System.out.println("delete");
            int result = jdbc.delete(sql,id);
            System.out.print("result:"+result);
            if(result!=1){
                System.out.print("删除房间失败");
            }else{
                System.out.print("删除房间成功");
            }
            jdbc.close();
        }
        //房间全查
        public List<Map<String,Object>> queryAllEmployees(){
            String sql = "select * from employee";
            jdbc = new JDBC();
            List<Map<String,Object>> list = jdbc.queryAll(sql);
            jdbc.close();
            return list;
        }
        //员工条件查询
        public List<Map<String,Object>> queryemployeeByCondition(List<Object> conList){
            String sql = "select * from employee where name=?";
            JDBC jdbc = new JDBC();
            List<Map<String,Object>> list = jdbc.queryByCondition(sql, conList);
            jdbc.close();
            return list;
        }
    //商品模糊查询
    public List<Map<String,Object>> MHqueryGoodsByCondition(String name){
        String sql = "select * from employee where name like ?";
        System.out.print("模糊查询");
        JDBC jdbc = new JDBC();
        List<Map<String,Object>> list = jdbc.MHQueryByCondition(sql, name);
        jdbc.close();
        return list;
    }
    }


