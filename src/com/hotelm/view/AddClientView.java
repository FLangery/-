package com.hotelm.view;

import com.hotelm.dao.ClientDao;
import com.hotelm.dao.EmployeeDao;
import com.hotelm.eneity.Client;
import com.hotelm.eneity.Employee;
import com.hotelm.tool.MyFont;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * 添加客户管理
 */
public class AddClientView extends JDialog{
    //全局的位置变量，用于表示鼠标在窗口上的位置
    static Point origin = new Point(); //点
    // 定义组件
    JButton close;                //按钮
    JPanel showinput, all;        //面板
    JLabel id, name, card, sex,age;//标签
    JTextField idt, namet, cardt, sext,aget;    //文本栏

    JButton confirm, cancel;

    Vector v = new Vector();  //向量
    int type = 0;//type=1为修改，type=0为添加

    //如果为修改，将表格中选中行的数据写入文本框默认内容
    public void setJTextView() {
        if (type == 1) {
            if (v.size() > 0 && v != null) {
                //idt.setText(v.get(0).toString());
                namet.setText(v.get(0).toString());
                cardt.setText(v.get(1).toString());
                sext.setText(v.get(2).toString());
                aget.setText(v.get(3).toString());
            }
        }
    }

    public AddClientView(int x, int y, Vector v, int type) {
        // 设置窗体的样式为当前系统的样式
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("type:" + type);
        this.v = v;//获取上一个界面传过来的数据
        this.type = type;//判断是修改还是添加
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
        all.setBorder(new MatteBorder(2, 2, 2, 2, Color.GRAY));
        initShowinput();
        all.add(confirm);
        all.add(cancel);

        this.add(all);
        this.setUndecorated(true);
        this.setLayout(null);
        this.setSize(560, 498);
        this.setLocation(x, y);
        WindowMove();
        this.setModal(true);
        this.setVisible(true);
    }

    // 窗体移动函数
    public void WindowMove() {

        //设置没有标题的窗口可以拖动
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {  //按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
                origin.x = e.getX();  //当鼠标按下的时候获得窗口当前的位置
                origin.y = e.getY();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();  //当鼠标拖动时获取窗口当前位置
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

        name = new JLabel("客户姓名");
        setlab(name);
        namet = new JTextField();
        setjtf(namet);

        card = new JLabel("身份证号");
        setlab(card);
        cardt = new JTextField();
        setjtf(cardt);

        sex = new JLabel("性别");
        setlab(sex);
        sext = new JTextField();
        setjtf(sext);

        age = new JLabel("年龄");
        setlab(age);
        aget = new JTextField();
        setjtf(aget);

        showinput.setOpaque(false);
        setJTextView();//设置文本框的默认值
        all.add(showinput);
    }

    //设置按钮布局，并绑定点击事件，监听处理事务
    public void setbutton(JButton jb, int typeValue) {
        final int buttonTtype = typeValue;
        if (buttonTtype == 1) {
            jb.setContentAreaFilled(false);
        }

        jb.setForeground(Color.blue);
        jb.setBorderPainted(false);
        jb.setFocusPainted(false);
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (buttonTtype == 3 || buttonTtype == 1)
                    dispose();
                else {
                    //获取编辑框文本信息
                    System.out.println("name:" + namet.getText().trim());
                    if (!namet.getText().trim().equals("")) {
                        String name = namet.getText().trim();
                        String card = cardt.getText().trim();
                        String sex =sext.getText().trim();
                        Integer age= Integer.valueOf(aget.getText().trim());
                        //文本框封装Goods类的对象
                        Client em = new Client(name, card, sex,age);
                        ClientDao dao = new ClientDao();
                        System.out.println("type...:" + type);
                        //访问数据库操作
                        if (type == 0) {//判断新增操作
                            int result = dao.addClient(em);
                            if (result == 0)
                                showMessage("数据添加失败");
                        } else if (type == 1) {//判断修改操作
                            int result = dao.updateClient(em);
                            if (result == 0)
                                showMessage("数据修改失败");
                        }

                        dispose();
                    } else {
                        showMessage("数据不能为空");
                    }
                }

            }
        });
        jb.setOpaque(false);
        jb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jb.setFont(MyFont.PaddInfotext);
    }

    //字符串转日期格式
    public Date strToDateLong(String strDate) {
        System.out.println("strDate=" + strDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //ParsePosition pos = new ParsePosition(0);
        Date strtodate = null;
        try {
            strtodate = formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strtodate;
    }

    //警告信息框
    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "警告", JOptionPane.ERROR_MESSAGE);
    }

}
