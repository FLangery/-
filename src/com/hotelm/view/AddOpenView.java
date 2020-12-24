package com.hotelm.view;

import com.hotelm.dao.OpenRoomDao;
import com.hotelm.dao.RoomDao;
import com.hotelm.eneity.OpenRoom;
import com.hotelm.eneity.Rooms;
import com.hotelm.tool.MyFont;
import com.hotelm.util.JDBC;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
/**
 *添加开房数据视图
 * */
public class AddOpenView extends JDialog {
    //全局的位置变量，用于表示鼠标在窗口上的位置
    static Point origin = new Point(); //点
    // 定义组件
    JButton close;				//按钮
    JPanel showinput, all;		//面板
    JLabel id, roomid, day, rday , price ,depost, status,money;	//标签
    JTextField idt, roomidt, dayt, rdayt , pricet ,depostt, statust,moneyt;	//文本栏

    JButton confirm, cancel;

    Vector v = new Vector();  //向量
    int type=0;//type=1为修改，type=0为添加
    //如果为修改，将表格中选中行的数据写入文本框默认内容
    public  void setJTextView(){
        if(type==1){
            if(v.size()>0&&v!=null){
                roomidt.setText(v.get(1).toString());
                dayt.setText(v.get(2).toString());
                rdayt.setText(v.get(3).toString());
               // pricet.setText(v.get(3).toString());
                depostt.setText(v.get(5).toString());
               // statust.setText(v.get(5).toString());
                //moneyt.setText(v.get(6).toString());
            }
        }
    }
    public AddOpenView(int x, int y, Vector v, int type){
        // 设置窗体的样式为当前系统的样式
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        this.v =v;//获取上一个界面传过来的数据
        this.type=type;//判断是修改还是添加
        // 关闭按钮
        close = new JButton();
        close.setBounds(525, 13, 22, 22);
        close.setForeground(Color.red);
        setbutton(close, 1);

        confirm = new JButton("确 定");
        confirm.setBounds(100, 350, 110, 50);
        setbutton(confirm, 2);
        cancel = new JButton("退 出");
        cancel.setBounds(300, 350, 110, 50);
        setbutton(cancel, 3);

        this.add(close);

        all = new JPanel(null);
        all.setBackground(Color.white);
        all.setBounds(0, 0, 560, 498);
        all.setBorder(new MatteBorder(2, 2, 2, 2, Color.GRAY));//边框
        initShowinput();
        all.add(confirm);
        all.add(cancel);

        this.add(all);
        this.setUndecorated(true);
        this.setLayout(null);
        this.setSize(560, 498);
        //窗体移动
        this.setLocation(x, y);
        WindowMove();
        this.setModal(true);    //模态
        this.setVisible(true);  //视图可见
    }
    // 窗体移动函数
    public void WindowMove() {

        //设置没有标题的窗口可以拖动
        this.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {  //按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
                origin.x = e.getX();  //当鼠标按下的时候获得窗口当前的位置
                origin.y = e.getY();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                Point p =getLocation();  //当鼠标拖动时获取窗口当前位置
                //设置窗口的位置
                //窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
                setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
            }
        });
    }
    private void setlab(JLabel jlb) {

        showinput.add(jlb);
        jlb.setFont(MyFont.Infolab);
    }
    private void setjtf(JTextField jtf) {
        showinput.add(jtf);
        jtf.setOpaque(false);
        jtf.setFont(MyFont.PaddInfotext);
    }
    // 显示输入信息面板初始化
    private void initShowinput() {
        showinput = new JPanel(new GridLayout(4, 2, -180, 10));
        showinput.setBounds(50, 40, 400, 250);

        roomid = new JLabel("房间号");
        setlab(roomid);
        roomidt = new JTextField();
        setjtf(roomidt);

        day = new JLabel("开房天数");
        setlab(day);
        dayt = new JTextField();
        setjtf(dayt);

        rday = new JLabel("住房天数");
        setlab(rday);
        rdayt = new JTextField();
        setjtf(rdayt);

       /*price = new JLabel("房间价格");
        setlab(price);
        pricet = new JTextField();
        setjtf(pricet);*/

        depost = new JLabel("收款");
        setlab(depost);
        depostt = new JTextField();
        setjtf(depostt);

        /*status = new JLabel("房间状态");
        setlab(status);
        statust = new JTextField();
        setjtf(statust);*/

        /*money = new JLabel("实际消费");
        setlab(money);
        moneyt = new JTextField();
        setjtf(moneyt);*/

        showinput.setOpaque(false);

        setJTextView();//设置文本框的默认值
        all.add(showinput);
    }
    //设置按钮布局，并绑定点击事件，监听处理事务
    public void setbutton(JButton jb, int typeValue) {
        final int buttonTtype=typeValue;
        if (buttonTtype == 1) {
            jb.setContentAreaFilled(false);
        }

        jb.setForeground(Color.blue); //颜色
        jb.setBorderPainted(false);
        jb.setFocusPainted(false);
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(buttonTtype==3||buttonTtype==1)
                    dispose();
                else{
                    //获取编辑框文本信息
                    System.out.println("开房房间号:"+ roomidt.getText().trim());
                    if(!roomid.equals("")&&!day.equals("")) {
                        String roomid = roomidt.getText().trim();
                        String day = dayt.getText().trim();
                        String rday = rdayt.getText().trim();

                        JDBC jdbc = new JDBC();
                        List a = new ArrayList();
                        List<Map<String, Object>> list = new ArrayList<>();
                        a.add(roomid);
                        list = jdbc.queryByCondition("select price,status from room where roomid=?", a);
                        if (list.size() != 0) {
                            Map<String, Object> map = list.get(0);
                            Double price = (Double) map.get("price");
                            Double depost = Double.valueOf(depostt.getText().trim());
							/*Double createDate = Double.valueOf(pricet.getText().trim());
							Date date = strToDateLong(createDate);*/
                            String status = "占用";
                            Double money = price * Double.valueOf(rday);

                            Date time = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String timef = formatter.format(time);
                            //文本框封装Goods类的对象
                            OpenRoom oroom = new OpenRoom(roomid, day, rday, price, depost, status, money,timef);
                            OpenRoomDao dao = new OpenRoomDao();
                            // System.out.println("开房已记录"+type);
                            //访问数据库操作
                            if (type == 0) {//判断新增操作
                                int result = dao.addOpenRoom(oroom);
                                if (result == 0) {
                                    showMessage("该房间不存在");
                                } else if (result == 2) {
                                    showMessage("房间被占用");
                                }
                            } else if (type == 1) {//判断修改操作
                                 int result = dao.updateOpenRoom(oroom);
                                    if(result==0)
                                showMessage("数据修改失败");
                            }
                            dispose();
                            } else
                                {showMessage("该房间不存在");}
                    } else {
                        showMessage("数据不能为空"); }
                }

            }
        });;
        jb.setOpaque(false);
        jb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jb.setFont(MyFont.PaddInfotext);
    }
    //警告信息框
    private void showMessage(String msg){
        JOptionPane.showMessageDialog(null, msg,"警告", JOptionPane.ERROR_MESSAGE);
    }

}

