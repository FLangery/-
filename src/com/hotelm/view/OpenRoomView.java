package com.hotelm.view;

import com.hotelm.dao.OpenRoomDao;
import com.hotelm.dao.RoomDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**开房展示视图*/
public class OpenRoomView extends JPanel {
    JTable jt = null;       //表格
    JScrollPane jsc = null; //滚动窗口
    JButton jb1,jb2,jb3;
    String[] tableName = {"编号","房间id","开房天数","住房天数","房间价格","收款","房间状态","实际消费","开房时间","退房时间"};
    Object Id;                   //获取id列的值
    Vector<Vector> vp =null;     //数据行数组
    //定义数据模型，调用Vector集合
    Vector title = new Vector(); //标题行
    DefaultTableModel tableModel = new DefaultTableModel();
    int selectRow=-1;
    //构造方法
    public OpenRoomView(){
        this.setVisible(true); //可见
        this.setLayout(null);  //布局空
        this.setBounds(0, 100, 1300, 600); //边界
        //this.setBackground(Color.GREEN);
        initVector();
        intoButton();
    }
    //显示数据
    public void initVector(){
        //定义 jtable显示的数据集合类
        vp = new Vector<Vector>();			//行

        jt = new JTable(){
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
        queryOpenRoom();
        //tableModel数据模型设置数据：数据行，标题行，
        tableModel.setDataVector(vp, title);
        //jtable调用模型
        jt.setModel(tableModel);
        //jtable注册鼠标监听器
        jt.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                //获取选中行号
                selectRow = jt.getSelectedRow();
                System.out.println("Row:"+selectRow);
                Id =  jt.getValueAt(selectRow,0);
                System.out.println("开房记录Id:"+ Id);
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
        });

    }
    //全查开房信息，保存在vp数组中
    public void queryOpenRoom(){
       OpenRoomDao dao = new OpenRoomDao();
        List<Map<String,Object>> list = dao.queryAllOpen();
        if(list!=null&&list.size()>0)
            for(int i=0;i<list.size();i++){
                Vector v = new Vector<>();//行
                Map<String,Object> map = list.get(i);//行
                if(map.containsKey("id"))
                    v.add(map.get("id"));
                if(map.containsKey("roomid"))
                    v.add(map.get("roomid"));
                if(map.containsKey("day"))
                    v.add(map.get("day"));
                if(map.containsKey("rday"))
                    v.add(map.get("rday"));
                if (map.containsKey("price"))
                    v.add(map.get("price"));
                if (map.containsKey("depost"))
                    v.add(map.get("depost"));
                if(map.containsKey("status"))
                    v.add(map.get("status"));
                if(map.containsKey("money"))
                    v.add(map.get("money"));
                if(map.containsKey("opentime"))
                    v.add(map.get("opentime"));
                if(map.containsKey("outtime"))
                    v.add(map.get("outtime"));
                vp.add(v);//添加行
            }
    }
   //添加底部按钮
        public void intoButton(){
            jb1 = new JButton("开房");
            jb1.setBounds(20, 490, 80, 50);	//设置界限
            jb1.addActionListener(new ActionListener() {		//添加事件
                public void actionPerformed(ActionEvent e) {
                    AddOpenView add = new AddOpenView(200,200,null,0);//新增操作
                    updateData();
                }
            });

            jb2 = new JButton("退房");
            jb2.setBounds(120,490,80,50);
            jb2.addActionListener(new ActionListener() {		//添加事件
                public void actionPerformed(ActionEvent e) {
                    Vector v = new Vector();
                    if(selectRow==-1)
                        showMessage("请选中修改的数据");
                    else{
                        for(int i=0;i<title.size();i++){
                            Object obj = jt.getValueAt(selectRow, i);
                            v.add(obj);							//当前行数据写入v数组中
                        }

                        String roomid =v.get(1).toString();
                        List<Object> conList = new ArrayList<Object>();
                        List<Object> conList2 = new ArrayList<Object>();
                        conList.add("空闲");
                        conList.add(roomid);

                        Date time = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String timef = formatter.format(time);
                        conList2.add(timef);
                        conList2.add(roomid);

                        OpenRoomDao dao = new OpenRoomDao();
                        RoomDao rdao = new RoomDao();
                        dao.setORoomS(conList);
                        dao.setORoomOutT(conList2);
                        rdao.uRoom(conList);
                        //更新界面数据
                        updateData();
                        showMessage("退房成功");
                    }

                }
            });

            jb3 = new JButton("删除该记录");
            jb3.setBounds(220, 490, 160, 50);
            jb3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int res=JOptionPane.showConfirmDialog(null, "是否确认删除", "请选择", JOptionPane.YES_NO_OPTION);
                    if(res==JOptionPane.YES_OPTION) {
                        if(selectRow>=0){
                            System.out.println("row:"+selectRow);
                            OpenRoomDao dao = new OpenRoomDao();
                            dao.deleteRoom((int)Id);
                            selectRow=-1;
                            updateData();
                        }
                        else{
                            showMessage("请选中删除的数据");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "你选择了否！", "消息",JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            this.add(jb1);
            this.add(jb2);
            this.add(jb3);
    }
    //更新数据
    public void updateData(){
        //数据来源，存放在vp集合中
        vp= new Vector<Vector>();			//初始化数组
        queryOpenRoom();//查询全部数据
        //table数据模型设置数据，
        tableModel.setDataVector(vp, title);//设置表格模型数据
        jt.setModel(tableModel);			//表格添加模型数据
        jt.validate();						//更新
    }
    //警告信息框
    private void showMessage(String msg){
        JOptionPane.showMessageDialog(null, msg,"警告", JOptionPane.ERROR_MESSAGE);
    }

}
