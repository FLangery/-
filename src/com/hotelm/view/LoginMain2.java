package com.hotelm.view;

import com.hotelm.dao.EmployeeDao;
import com.hotelm.tool.ImagePanel;
import com.hotelm.util.JDBC;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

public class LoginMain2 extends JFrame implements ActionListener {
    JTextField C = new JTextField();
    JPasswordField D = new JPasswordField();
    JButton E= new JButton("登录");
    JButton F = new JButton("注册");
    JLabel A = new JLabel("用户名");
    JLabel B = new JLabel("用户密码");

    ImagePanel jbg = null;
    private static final Color red = null;
    public static void main(String[] args) {
        LoginMain2 frm = new LoginMain2();
        frm.pack();
        frm.show();
    }

    public LoginMain2() {
        initBkPanel();
        A.setBounds(new Rectangle(300, 150, 100, 40));
        A.setFont(new Font("新宋体", Font.PLAIN, 25));	//设置JLable字体
        A.setForeground(Color.BLACK);
        B.setBounds(new Rectangle(300, 230, 100, 40));
        B.setFont(new Font("新宋体", Font.PLAIN, 25));	//设置JLable字体
        B.setForeground(Color.BLACK);
        C.setBounds(new Rectangle(450,150, 150, 40));
        D.setBounds(new Rectangle(450, 230, 150, 40));
        E.setBounds(new Rectangle(300, 300, 86, 30));
        F.setBounds(new Rectangle(500, 300, 86, 30));
        this.add(A);
        this.add(B);
        this.add(C);
        this.add(D);
        this.add(E);
        this.add(F);
        E.addActionListener(this);
        F.addActionListener(this);
        this.add(jbg);
        this.setLayout(null);
        this.setTitle("艾利克斯酒店");
        this.setSize(850,600);
        this.setLocationRelativeTo(null);
        setVisible(true);

    }
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "登录") {
            JDBC sqlHepler = new JDBC();
            EmployeeDao dao = new EmployeeDao();
            List<Map<String, Object>> list = dao.queryAllEmployees();
            int count=0;
            for (int i = 0; i < list.size(); i++) {
                if (C.getText().equals(list.get(i).get("name").toString())) {
                    if (D.getText().equals(list.get(i).get("card").toString())){
                        dispose();
                        MainView mw=new MainView();
                        mw.user_name=C.getText();
                        mw.show();}
                    else {
                        JOptionPane.showMessageDialog(null,"密码错误","Title",JOptionPane.ERROR_MESSAGE);
                        D.setText("");
                    }
                    count++;
                }
            }
            if(count==0){
                JOptionPane.showMessageDialog(null,"用戶不存在","Title",JOptionPane.ERROR_MESSAGE);
                C.setText("");
                D.setText("");
            }
        }

        else if (e.getActionCommand() == "注册") {
            RegisterView r = new RegisterView(200,200,null,0);
        }
    }

    public Dimension getPreferredSize() {
        {
            return new Dimension(1100, 600);
        }
    }
    public void initBkPanel() {
        // 使用工具包里的图片面板设置窗体的背景图片
        Image bk = null;
        try {
            bk= ImageIO.read(new File("img/BK.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        jbg = new ImagePanel(bk);
    }
}