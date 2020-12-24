package com.hotelm.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hotelm.dao.RoomDao;

/**房间展示视图*/
public class RoomsView extends JPanel{
	JTable jt = null;
	JScrollPane jsc;
	//底部按钮
	JButton jb1,jb2,jb3,jb4;
	String[] tableName = {"编号","房间号","楼层","价格","状态"};
	//获取id列的值
	Object RoomsId;
	//数据行数组
	Vector<Vector> vp =null;
	//标题行
	Vector title = new Vector();
	//定义数据模型，调用Vector集合
	DefaultTableModel tableModel = new DefaultTableModel();
	int selectRow=-1;
	public RoomsView(){
		this.setVisible(true);  							//设置可见
		this.setLayout(null);								//布局
		this.setBounds(0, 100, 1300, 600); 	//设置界限
		//this.setBackground(Color.green);
		initVector();										//表格数据初始化
		initBottom();										//底部按钮初始化
	}
	//底部模块
	public void initBottom(){
		jb1 = new JButton("添加房间");
		jb1.setBounds(20, 490, 160, 50);	//设置界限
		jb1.addActionListener(new ActionListener() {		//添加事件
			public void actionPerformed(ActionEvent e) {
				System.out.println("添加房间");
				AddRoomView add = new AddRoomView(200,200,null,0);//新增操作
				updateData();
			}
		});
		jb2 = new JButton("删除");
		jb2.setBounds(200, 490, 80, 50);
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int res=JOptionPane.showConfirmDialog(null, "是否确认删除", "请选择", JOptionPane.YES_NO_OPTION);
				if(res==JOptionPane.YES_OPTION) {
					if(selectRow>=0){
						System.out.println("row:"+selectRow);
						RoomDao dao = new RoomDao();
						dao.deleteRoom((int)RoomsId);
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
		jb3 = new JButton("修改");
		jb3.setBounds(300, 490, 80, 50);
		jb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("选择修改的数据");
				Vector v = new Vector();				//选中行数据
				if(selectRow==-1)
					showMessage("请选中修改的数据");
				else{
					for(int i=0;i<title.size();i++){
						System.out.println("row:"+selectRow+",col:"+i);
						Object obj = jt.getValueAt(selectRow, i);
						v.add(obj);							//当前行数据写入v数组中
					}
					AddRoomView update = new AddRoomView(400,300,v,1);//修改操作
					//更新界面数据
					updateData();
				}
			}
		});
		jb4 = new JButton("更新");
		jb4.setBounds(400,490,80,50);
		jb4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("更新数据");
				updateData();
			}
		});
		this.add(jb1);
		this.add(jb2);
		this.add(jb3);
		this.add(jb4);
	}

	//更新数据
	public void updateData(){
		//数据来源，存放在vp集合中
		vp= new Vector<Vector>();			//初始化数组
		queryRooms();//查询全部数据
		//table数据模型设置数据，
		tableModel.setDataVector(vp, title);//设置表格模型数据
		jt.setModel(tableModel);			//表格添加模型数据
		jt.validate();						//更新
	}

	public void initVector(){
		//定义 jtable显示的数据集合类
		vp = new Vector<Vector>();			//行
		//定义jtable表头
		//定义数据模型，调用Vector集合
		//DefaultTableModel tableModel =  new DefaultTableModel();
		//定义jtable,并重写方法，阻止可编辑
		jt = new JTable(){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jt.setOpaque(false);
		//标题行
		for(int i=0;i<tableName.length;i++)
			title.add(tableName[i]);
		//数据来源，存放在vp集合中
		queryRooms();
		//tableModel数据模型设置数据：数据行，标题行，
		tableModel.setDataVector(vp, title);
		//jtable调用模型
		jt.setModel(tableModel);
		//jtable注册鼠标监听器
		jt.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				//获取选中行号
				selectRow = jt.getSelectedRow();
				System.out.println("selectRow:"+selectRow);
				RoomsId =  jt.getValueAt(selectRow,0);
				System.out.println("房间Id:"+RoomsId);
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
		jt.setBounds(20, 20, 1050, 460);//设置表格位置
		jsc = new JScrollPane();
		jsc.setViewportView(jt);					//滚动条绑定表格
		jsc.setBounds(20, 20, 1050, 460);
		this.add(jsc);//容器添加滚动条
	}
	//全查商品信息，保存在vp数组中
	public void queryRooms(){
		RoomDao dao = new RoomDao();
		List<Map<String,Object>> list = dao.queryAllGoods();
		if(list!=null&&list.size()>0)
		for(int i=0;i<list.size();i++){
			Vector v = new Vector<>();//行
			Map<String,Object> map = list.get(i);//行
			if(map.containsKey("id"))
				v.add(map.get("id"));
			if(map.containsKey("roomid"))
				v.add(map.get("roomid"));
			if(map.containsKey("floor"))
				v.add(map.get("floor"));
			if(map.containsKey("price"))
				v.add(map.get("price"));
			if(map.containsKey("status"))
				v.add(map.get("status"));
			vp.add(v);//添加行
		}
	}
	//警告信息框
	private void showMessage(String msg){
		JOptionPane.showMessageDialog(null, msg,"警告", JOptionPane.ERROR_MESSAGE);
	}
}
