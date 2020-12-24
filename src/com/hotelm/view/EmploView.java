package com.hotelm.view;

import com.hotelm.dao.EmployeeDao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class EmploView extends JPanel{
	JTable jt = null;
	JScrollPane jsc = null;
	JButton jb1,jb2,jb3;

	String[] tableName = {"职工编号","职工id","姓名","身份证","年龄","入职时间","权限"};
	//获取id列的值
	Object Id;
	//数据行数组
	Vector<Vector> vp =null;
	//标题行
	Vector title = new Vector();
	//定义数据模型，调用Vector集合
	DefaultTableModel tableModel = new DefaultTableModel();
	int selectRow=-1;
	//构造方法
	public EmploView(){
		this.setVisible(true);
		this.setLayout(null);
		this.setBounds(0, 100, 1100, 600);
		//this.setBackground(Color.GREEN);
		initVector();
		initBottom();
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
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		/*jt.setOpaque(false);         //tblGroup为表格的名字
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setOpaque(false);
		jt.setDefaultRenderer(Object.class, render);
		//设置显示范围
		Dimension viewSize = new Dimension();
		viewSize.width =jt.getColumnModel().getTotalColumnWidth();;
		viewSize.height = jt.getRowHeight();
		jt.setPreferredScrollableViewportSize(viewSize);*/
		jt.setBounds(20, 20, 1050, 460);//设置表格位置
		jsc = new JScrollPane();
		jsc.setViewportView(jt);					//滚动条绑定表格
		jsc.setBounds(20, 20, 1050, 460);
		this.add(jsc);//容器添加滚动条

		jsc.getViewport().setOpaque(false); //jScrollPanel为table存放的容器，一般在Swing创    // 建表格时，它自动生成，原代码为：jScrollPane1 = newjavax.swing.JScrollPane();
		jsc.setOpaque(false);    //将中间的viewport设置为透明
		jsc.setViewportView(jt);
		//标题行
		for(int i=0;i<tableName.length;i++)
			title.add(tableName[i]);
		//数据来源，存放在vp集合中
		queryEmployees();
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
				System.out.println("职工编号Id:"+ Id);
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
	//全查员工信息，保存在vp数组中
	public void queryEmployees(){
		EmployeeDao dao = new EmployeeDao();
		List<Map<String,Object>> list = dao.queryAllEmployees();
		if(list!=null&&list.size()>0)
			for(int i=0;i<list.size();i++){
				Vector v = new Vector<>();//行
				Map<String,Object> map = list.get(i);//行
				if(map.containsKey("id"))
					v.add(map.get("id"));
				if(map.containsKey("employeeid"))
					v.add(map.get("employeeid"));
				if(map.containsKey("name"))
					v.add(map.get("name"));
				if(map.containsKey("card"))
					v.add(map.get("card"));
				if (map.containsKey("sex"))
					v.add(map.get("sex"));
				if(map.containsKey("joind"))
					v.add(map.get("joind"));
				if(map.containsKey("qx"))
					v.add(map.get("qx"));
				vp.add(v);//添加行
			}
	}
	//更新数据
	public void updateData(){
		//数据来源，存放在vp集合中
		vp= new Vector<Vector>();			//初始化数组
		queryEmployees();
		//table数据模型设置数据，
		tableModel.setDataVector(vp, title);//设置表格模型数据
		jt.setModel(tableModel);			//表格添加模型数据
		jt.validate();						//更新
	}
	//底部模块
	public void initBottom(){
		jb1 = new JButton("添加员工");
		jb1.setBounds(20, 500, 120, 50);	//设置界限
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					AddEmploView add = new AddEmploView(200,200,null,0);//新增操作
					updateData();
			}
		});
		jb2 = new JButton("删除");
		jb2.setBounds(160, 500, 80, 50);
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(selectRow<0){
					showMessage("请选中删除的数据");
				} else{
					int res=JOptionPane.showConfirmDialog(null,"是否删除员工",null,JOptionPane.YES_NO_OPTION);
					if(res==JOptionPane.YES_OPTION){
						System.out.println("row:"+selectRow);
						EmployeeDao dao = new EmployeeDao();
						dao.deleteEmployees((Integer) Id);
						selectRow=-1;
						updateData();
					} else return;
				}
			}
		});
		jb3 = new JButton("修改");
		jb3.setBounds(260, 500, 80, 50);

		jb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector v = new Vector();				//选中行数据
				if(selectRow==-1)
					showMessage("请选中修改的数据");
				else{
					for(int i=1;i<title.size();i++){
						System.out.println("row:"+selectRow+",col:"+i);
						Object obj = jt.getValueAt(selectRow, i);
						v.add(obj);							//当前行数据写入v数组中
					}
					AddEmploView update = new AddEmploView(400,300,v,1);//修改操作
					//更新界面数据
					updateData();
				}
			}
		});
		this.add(jb1);
		this.add(jb2);
		this.add(jb3);
	}
	private void showMessage(String msg){
		JOptionPane.showMessageDialog(null, msg,"警告", JOptionPane.ERROR_MESSAGE);
	}
}