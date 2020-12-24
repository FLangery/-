package com.hotelm.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import com.hotelm.dao.RoomDao;
import com.hotelm.tool.MyFont;
import com.hotelm.eneity.Rooms;
/**
 * 添加房间视图*/
public class AddRoomView extends JDialog{
	//全局的位置变量，用于表示鼠标在窗口上的位置
	static Point origin = new Point(); //点
	// 定义组件
	JButton close;				//按钮
	JPanel showinput, all;		//面板
	JLabel id, roomid, floor, price, status;	//标签
	JTextField idt, roomidt, floort, pricet, statust;	//文本栏

	JButton confirm, cancel;
	
	Vector v = new Vector();  //向量
	int type=0;//type=1为修改，type=0为添加
	//如果为修改，将表格中选中行的数据写入文本框默认内容
	public  void setJTextView(){
		if(type==1){
			if(v.size()>0&&v!=null){
				roomidt.setText(v.get(1).toString());
				floort.setText(v.get(2).toString());
				pricet.setText(v.get(3).toString());
				statust.setText(v.get(4).toString());
			}
		}
	}
	public AddRoomView(int x, int y, Vector v, int type){
		// 设置窗体的样式为当前系统的样式
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		this.v =v;//获取上一个界面传过来的数据
		this.type=type;//判断是修改还是添加
		// 关闭按钮
		close = new JButton(new ImageIcon("image/JDialogClose.png"));
		close.setRolloverIcon(new ImageIcon("image/JDialogCloseC.png"));
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
			
			floor = new JLabel("房间楼层");
			setlab(floor);
			floort = new JTextField();
			setjtf(floort);
			
			price = new JLabel("房间价格");
			setlab(price);
			pricet = new JTextField();
			setjtf(pricet);
			
			status = new JLabel("房间状态");
			setlab(status);
			statust = new JTextField();
			setjtf(statust);

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
			
			jb.setForeground(Color.blue);
			jb.setBorderPainted(false);
			jb.setFocusPainted(false);
			jb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				    if(buttonTtype==3||buttonTtype==1)
					   dispose();
				    else{
				    	//获取编辑框文本信息
				    	System.out.println("name:"+ roomidt.getText().trim());
				    	if(!roomid.equals("")&&!floort.getText().trim().equals("")){
							String roomid = roomidt.getText().trim();
							String floor = floort.getText().trim();
							Double price = Double.valueOf(pricet.getText().trim());
							/*Double createDate = Double.valueOf(pricet.getText().trim());
							Date date = strToDateLong(createDate);*/
							String status = statust.getText().trim();
							//文本框封装Goods类的对象
							Rooms room = new Rooms(roomid,floor,price,status);
							RoomDao dao = new RoomDao();
							System.out.println("type...:"+type);
							//访问数据库操作
							if(type==0){//判断新增操作
								int result = dao.addRoom(room);
								if(result==0)
									showMessage("数据添加失败");
							}
							else if(type==1){//判断修改操作
								int result = dao.updateRoom(room);
								if(result==0)
									showMessage("数据修改失败");
							}
							
							dispose();
				    	}else{
				    		showMessage("数据不能为空");
				    	}
				    }
				    
				}
			});;
			jb.setOpaque(false);
			jb.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jb.setFont(MyFont.PaddInfotext);
		}
	//字符串转日期格式
		public Date strToDateLong(String strDate) {
			System.out.println("strDate="+strDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			   //ParsePosition pos = new ParsePosition(0);
			Date strtodate =null;
			try {
				strtodate = formatter.parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return strtodate;
		}
		//警告信息框
		private void showMessage(String msg){
			JOptionPane.showMessageDialog(null, msg,"警告", JOptionPane.ERROR_MESSAGE);
		}
}
