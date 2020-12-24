package com.hotelm.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.hotelm.dao.EmployeeDao;
import com.hotelm.eneity.Employee;
import com.hotelm.tool.ImagePanel;
import com.hotelm.util.JDBC;

/**主界面*/
public class MainView extends JFrame implements MouseListener,ActionListener{
		public String user_name;
		public int user_password;

		ImagePanel jbg = null;   	 //背景面板
		JPanel jtop=null;			//顶部面板
		JPanel	jcenter = null;		//中间面板
		JLabel jlb1,jlb3,jlb4,jlb5,jlbn,jlbq;	//顶部标签
		CardLayout card = null;		//中间卡片容器，设置到中间面板的布局中，显示响应的面板，调用card.show()
		JButton jb1,jb2;       		 //右边按钮JButton
		JTextField jtf1;

		RoomsView pi = null;/**房间展示卡片*/
		EmploView ei = null;/**员工展示卡片*/
		OpenRoomView oi = null;/**开房记录展示卡片*/
		ManageView mi =null;/**员工管理卡片*/
		FindORoomView fi =null;/***/
		ClientView cv = null;/**客户信息视图*/
		//构造方法
		public MainView(){
			this.setTitle("酒店管理系统");
			initBkPanel();		//初始化面板
			initTop();			//添加顶部面板
			initCenter();		//添加中心面板
			//设置背景面板界面可见
			this.add(jbg);
			jbg.setLayout(null);
			int width=Toolkit.getDefaultToolkit().getScreenSize().width;
			int height=Toolkit.getDefaultToolkit().getScreenSize().height;
			this.setSize((int)(1300), (int)(700)); 					//设置主窗口大小
			this.setVisible(true);									//设置可见
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//设置默认关闭设置
			this.setLocationRelativeTo(null);						//相对位置
		}
		// 创建背景图片面板
		public void initBkPanel() {
				// 使用工具包里的图片面板设置窗体的背景图片
				Image bk = null;
				try {
					bk=ImageIO.read(new File("img/BK.png"));
					System.out.println("bk:"+bk);
				} catch (Exception e) {
					e.printStackTrace();
				}
				jbg = new ImagePanel(bk);
		}
		//顶部模块
		public void initTop(){
			jtop = new JPanel();
			jtop.setLayout(null);
			//jtop.setBackground(Color.GRAY);
			jtop.setSize(1100, 100);				//顶部大小

			jlb1 = new JLabel("房间信息");							//实例化JLable组建
			jlb1.setBounds(20, 10, 100, 80);			//设置JLable的位置和大小
			jlb1.setFont(new Font("新宋体", Font.PLAIN, 25));	//设置JLable字体
			jlb1.setForeground(Color.red);						    	//设置颜色
			jlb1.addMouseListener(this);

			jlb3 = new JLabel("开房管理");
			jlb3.setBounds(140, 10, 100, 80);
			jlb3.setFont(new Font("宋体", Font.PLAIN, 24));
			jlb3.addMouseListener(this);

			jlb4 = new JLabel("系统管理");
			jlb4.setBounds(260, 10, 100, 80);
			jlb4.setFont(new Font("宋体", Font.PLAIN, 24));
			jlb4.addMouseListener(this);

			jlb5 = new JLabel("客户管理");
			jlb5.setBounds(380, 10, 100, 80);
			jlb5.setFont(new Font("宋体", Font.PLAIN, 24));
			jlb5.addMouseListener(this);

			/*jlbq = new JLabel("用户姓名"+findQx());
			jlbn.setBounds(760,70,100,50);*/
			//JLable添加进中间JPanle容器
			jtop.add(jlb1);
			jtop.add(jlb3);
			jtop.add(jlb4);
			jtop.add(jlb5);

			jtop.setOpaque(false);	//设置透明度
			//中间容器添加进顶层容器
			jbg.add(jtop);
		}
		//中心模块
		public void initCenter(){
			//卡片容器初始化
			this.card = new CardLayout(); //卡片布局
			//卡片容器交给画纸管理
			jcenter = new JPanel(card);
			jcenter.setOpaque(false);    //设置透明度
			jcenter.setBounds(0, 100, 1300, 600);

			pi = new RoomsView();
			pi.setOpaque(false);
			jcenter.add(pi,"room");

			ei = new EmploView();
			ei.setOpaque(false);
			jcenter.add(ei,"emploeer");

			oi = new OpenRoomView();
			oi.setOpaque(false);
			jcenter.add(oi,"openroom");

			mi = new ManageView();
			mi.setOpaque(false);
			jcenter.add(mi,"manage");

			cv = new ClientView();
			cv.setOpaque(false);
			jcenter.add(cv,"client");

			jbg.add(jcenter);
		}
		//右边按钮
		public void intRButton(){
			jb1 = new JButton("职工管理");
			jb1.setBounds(1150, 210, 120, 50);    //设置界限
			jb1.addActionListener(this);

			jtf1 = new JTextField(10);
			jb1.setBounds(1150, 300, 120, 50);

			jb2 = new JButton("查询统计");
			jb2.setBounds(1150, 380, 120, 50);
			jb2.addActionListener(this);
			jbg.add(jb1);
			jbg.add(jb2);
			jbg.add(jtf1);
		}
		//顶部菜单样式设置
		public void topMenu(JLabel jlb){
			jlb1.setFont(new Font("宋体", Font.PLAIN, 24));
			jlb1.setForeground(Color.black);
			/*
			jlb2.setFont(new Font("宋体", Font.PLAIN, 24));
			jlb2.setForeground(Color.black);
			*/
			jlb3.setFont(new Font("宋体", Font.PLAIN, 24));
			jlb3.setForeground(Color.black);
			jlb4.setFont(new Font("宋体", Font.PLAIN, 24));
			jlb4.setForeground(Color.black);
			jlb5.setFont(new Font("宋体", Font.PLAIN, 24));
			jlb5.setForeground(Color.black);
			jlb.setFont(new Font("新宋体", Font.PLAIN, 25));
			jlb.setForeground(Color.red);
		}
		public void showCenter(MouseEvent e){
			if(e.getSource()==jlb1)
			{
				jlbn = new JLabel("用户姓名："+user_name);
				jlbn.setBounds(560,10,400,40);
				jlbn.setFont(new Font("宋体", Font.PLAIN, 24));
				jlbn.setForeground(Color.yellow);
				jtop.add(jlbn);
				jlbq = new JLabel("用户权限："+findQx());
				jlbq.setBounds(560,60,400,40);
				jlbq.setFont(new Font("宋体", Font.PLAIN, 24));
				jlbq.setForeground(Color.yellow);
				jtop.add(jlbq);
				topMenu(jlb1);
				card.show(jcenter, "room");
			}
			/*if(e.getSource()==jlb2){
				topMenu(jlb2);
				card.show(jcenter, "emploeer");
			}*/
			if(e.getSource()==jlb3){
				topMenu(jlb3);
				card.show(jcenter, "openroom");
			}
			if(e.getSource()==jlb4){
				String qx = findQx();
				if(qx.equals("高管")){
				topMenu(jlb4);
				card.show(jcenter, "manage");
				intRButton();
				}else {
					showMessage("没有权限");
				}
			}
			else if(e.getSource()==jlb5){
				topMenu(jlb5);
				card.show(jcenter, "client");
			}
		}

		//根据用户名查询权限
		public String findQx(){
			String sql = "select qx from employee where name=?";
			JDBC jdbc = new JDBC();
			List c = new ArrayList();
			c.add(user_name);
			List<Map<String, Object>> list=jdbc.queryByCondition(sql,c);
			Map<String, Object> map = list.get(0);//行
			return map.get("qx").toString();
		}
		//添加事件
		public void mouseClicked(MouseEvent e) {
			showCenter(e);
		}
		public void mousePressed(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "职工管理") {
				this.card.show(jcenter, "emploeer");
			}else if(e.getActionCommand() == "查询统计"){
				fi = new FindORoomView();
				fi.setOpaque(false);
				jcenter.add(mi,"findOR");

				this.card.show(jcenter,"findOR");
				//fi.conList.add(jb1.getText());
				jb1.setText("");
			}
		}
	private void showMessage(String msg){
		JOptionPane.showMessageDialog(null, msg,"警告", JOptionPane.ERROR_MESSAGE);
	}
}
