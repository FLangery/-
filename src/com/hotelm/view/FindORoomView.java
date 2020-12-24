package com.hotelm.view;

import com.hotelm.dao.OpenRoomDao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import java.util.List;
/**
 * 查找开房信息视图
 * */
public class FindORoomView extends JPanel {
    //public List<Object> conList= null;
    JTable jt = null;       //表格
    JScrollPane jsc = null; //滚动窗口
    JButton jb1,jb2,jb3;
    String[] tableName = {"编号","房间id","开房天数","住房天数","房间价格","收款","房间状态","实际消费","开房时间","退房时间"};
    // Object Id;                   //获取id列的值
    Vector<Vector> vp =null;     //数据行数组
    //定义数据模型，调用Vector集合
    Vector title = new Vector(); //标题行
    DefaultTableModel tableModel = new DefaultTableModel();
    int selectRow=-1;
    //构造方法
    public FindORoomView(){
        this.setVisible(true); //可见
        this.setLayout(null);  //布局空
        this.setBounds(0, 100, 1300, 600); //边界
        //this.setBackground(Color.GREEN);
        initVector();
        //intoButton();
    }
    //显示数据
    public void initVector(){
        //定义 jtable显示的数据集合类
        vp = new Vector<Vector>();			//行
        //定义jtable表头
        //定义数据模型，调用Vector集合
        //DefaultTableModel tableModel =  new DefaultTableModel();
        //定义jtable,并重写方法，阻止可编辑
        jt = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jt.setBounds(20, 20, 1050, 460);//设置表格位置
        jsc = new JScrollPane();
        jsc.setViewportView(jt);					//滚动条绑定表格
        jsc.setBounds(20, 20, 1050, 460);
        this.add(jsc);//容器添加滚动条
        //将JScrollPane设置为透明
        jsc.getViewport().setOpaque(false); //jScrollPanel为table存放的容器，一般在Swing创    // 建表格时，它自动生成，原代码为：jScrollPane1 = newjavax.swing.JScrollPane();
        jsc.setOpaque(false);    //将中间的viewport设置为透明
        jsc.setViewportView(jt);
        //标题行
        for(int i=0;i<tableName.length;i++)
            title.add(tableName[i]);
        //数据来源，存放在vp集合中
        //tableModel数据模型设置数据：数据行，标题行，
        tableModel.setDataVector(vp, title);
        //jtable调用模型
        jt.setModel(tableModel);


    }
    //根据开房时间条件查询，保存在vp数组中
    public void queryOpenRoom(List<Object> conList){
        String sql = "select * from openroom where opentime like ? ";
        OpenRoomDao dao = new OpenRoomDao();
        List<Map<String,Object>> list = dao.querytimeOpen(sql,conList);
        if(list!=null&&list.size()>0) {
            for(int i=0;i<list.size();i++){
                Vector v = new Vector<>();//行
                Map<String,Object> map = list.get(i);//行中的数据，用map集合存放
                if(map.containsKey("id")) {
                    v.add(map.get("id"));
                }
                if(map.containsKey("roomid")) {
                    v.add(map.get("roomid"));
                }
                if(map.containsKey("day")) {
                    v.add(map.get("day"));
                }
                if(map.containsKey("rday")) {
                    v.add(map.get("rday"));
                }
                if (map.containsKey("price")) {
                    v.add(map.get("price"));
                }
                if (map.containsKey("depost")) {
                    v.add(map.get("depost"));
                }
                if(map.containsKey("status")) {
                    v.add(map.get("status"));
                }
                if(map.containsKey("money")) {
                    v.add(map.get("money"));
                }
                if(map.containsKey("opentime")) {
                    v.add(map.get("opentime"));
                }
                if(map.containsKey("outtime")) {
                    v.add(map.get("outtime"));
                }
                vp.add(v);//添加行
            }
        }
    }
    //警告信息框
    private void showMessage(String msg){
        JOptionPane.showMessageDialog(null, msg,"警告", JOptionPane.ERROR_MESSAGE);
    }

}