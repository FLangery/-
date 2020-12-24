package com.hotelm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.mysql.fabric.xmlrpc.base.Array;

/**
 * 数据库工具类
 * @author LENOVO
 *
 */
public class JDBC {
	//定义操作数据库所需要的对象
	Connection con = null;//定义连接数据库的接口
    PreparedStatement psms=null;//执行数据库操作的预编译接口
    ResultSet rs = null;//查询结果集
    
    //定义连接数据库的参数
	String url = "jdbc:mysql://localhost:3306/hotelm?serverTimezone=GMT%2B8";//访问数据库地址
    String username= "root";//数据库的用户名和密码
    String password ="123456";
    String driverName ="com.mysql.cj.jdbc.Driver";//mysql的驱动名
    
    //构造方法，加载连接数据库的驱动
    public JDBC(){
    	try {
    		//加载驱动
			Class.forName(driverName);
			//获取连接
			con = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			System.out.print("驱动加载失败，请检查！");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e){
			System.out.print("连接失败，请重试！");
			e.printStackTrace();
		}
    	catch(SQLException e){
			System.out.println("数据库连接失败，请重试!");
			e.printStackTrace();
		}
    }
    //数据新增操作
    public int insert(String sql,List<Object> st){
    	int result = 0;
    	try {
			psms=con.prepareStatement(sql);//通过连接接口创建预编译接口
			//遍历保存数据的集合,对注入集合中的元素赋值给sql语句中的占位符“？”
			setPsmsValue(psms, st);
			//执行插入操作
			result = psms.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return result;
    }
    //修改操作
    public int update(String sql,List<Object> list){
    	int result = -1;
    	try {
			psms = con.prepareStatement(sql);
			setPsmsValue(psms,list);
			result = psms.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return result;
    }
    //全查操作
    public List<Map<String,Object>> queryAll(String sql){
    	//定义集合，存放结果集的所有行的所有列：list-行，map-列
		List<Map<String,Object>> list = new ArrayList<>();
    	try {
			psms=con.prepareStatement(sql);//初始化预编译接口
			rs = psms.executeQuery();//执行查询操作
			//获取查询结果集的列的个数
			int colums = rs.getMetaData().getColumnCount();
			if(rs!=null){//判断结果集是否为空
				while(rs.next()){//遍历结果集的所有行
					Map<String,Object> map = new HashMap<String, Object>();//存放所有列
					for(int i=1;i<=colums;i++){//遍历当前行的所有列	
						//以key-value来保存所有列（当前行的列名，当前行的列值）
						map.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
					}
                    //添加行
					list.add(map);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return list;
    	
    }
    //条件查询
    public List<Map<String,Object>> queryByCondition(String sql,List<Object> conList){
    	//定义集合，存放结果集的所有行的所有列：list-行，map-列
		List<Map<String,Object>> list = new ArrayList<>();
    	try {
			psms=con.prepareStatement(sql);//初始化预编译接口
			setPsmsValue(psms, conList);
			rs = psms.executeQuery();//执行查询操作
			//获取查询结果集的列的个数
			int colums = rs.getMetaData().getColumnCount();
			if(rs!=null){//判断结果集是否为空
				while(rs.next()){//遍历结果集的所有行
					Map<String,Object> map = new HashMap<String, Object>();//存放所有列
					for(int i=1;i<=colums;i++){//遍历当前行的所有列	
						//以key-value来保存所有列（当前行的列名，当前行的列值）
						map.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
					}
                    //添加行
					list.add(map);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return list;
    }
    //对name字段进行模糊查询
    public List<Map<String,Object>> MHQueryByCondition(String sql,String name){
    	//定义集合，存放结果集的所有行的所有列：list-行，map-列
		List<Map<String,Object>> list = new ArrayList<>();
    	try {
			psms=con.prepareStatement(sql);//初始化预编译接口
			psms.setString(1, "%"+name+"%");
			rs = psms.executeQuery();//执行查询操作
			//获取查询结果集的列的个数
			int colums = rs.getMetaData().getColumnCount();
			if(rs!=null){//判断结果集是否为空
				while(rs.next()){//遍历结果集的所有行
					Map<String,Object> map = new HashMap<String, Object>();//存放所有列
					for(int i=1;i<=colums;i++){//遍历当前行的所有列	
						//以key-value来保存所有列（当前行的列名，当前行的列值）
						map.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
					}
                    //添加行
					list.add(map);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return list;
    }
    //删除操作
    public int delete(String sql,int id){
    	int result = -1;
    	try {
			psms = con.prepareStatement(sql);
			psms.setInt(1, id);
			result = psms.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    //预编译接口对占位符赋值类型统配
    public void setPsmsValue(PreparedStatement pstm,List<Object> list){
    	if(list==null||list.size()==0)
    		return;
    	try{
    		for(int i=0;i<list.size();i++){
        		if(list.get(i) instanceof String){
        			//instanceof左边的数据是右边类型，则为真
        			pstm.setString(i+1,(String)list.get(i));
        		}else if(list.get(i) instanceof Integer){
        			pstm.setInt(i+1,(int)list.get(i));
        		}else if(list.get(i) instanceof Double){
        			pstm.setDouble(i+1,(Double)list.get(i));
        		}else if(list.get(i) instanceof Date){
        			pstm.setDate(i+1, (Date)list.get(i));
        		}else{
        			pstm.setObject(i+1, list.get(i));
        		}
        	}
    	}catch(SQLException e){
    		System.out.println("预编译注入参数错误");
    		e.printStackTrace();
    	}
    }
    //释放数据库连接池资源
    public void close(){
		try {
			if(rs!=null)
				rs.close();
			if(psms!=null)
				psms.close();
			if(con!=null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
}
