package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.PointerDao;
import dao.TaskDao;
import dao.TaskListDao;
import dao.TempTaskDao;
import model.Task;
import model.TaskList;
import model.TempTask;
import util.Dateutil;
import util.Dbutil;
import util.Stringutil;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class TempTaskManageDialog extends JDialog {
	private JTextField nameTxt;
	private JTextField yearTxt;
	private JTextField monthTxt;
	private JTextField dayTxt;
	private JTextArea descTxt;
	private int listId;
	
	private Dbutil dbutil = new Dbutil();
	private TaskListDao taskListDao = new TaskListDao();
	private TaskDao taskDao= new TaskDao();
	private TempTaskDao tempTaskDao = new TempTaskDao();
	private PointerDao pointerDao = new PointerDao();
	private JTextField idTxt;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TempTaskManageDialog frame = new TempTaskManageDialog("","","","","","");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TempTaskManageDialog(String id,String name,String desc,String year,String month,String day) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TempTaskManageDialog.class.getResource("/images/checklist_16px_1142185_easyicon.net.png")));
		setModal(true);
		setResizable(false);
		setTitle("\u77ED\u671F\u4EFB\u52A1\u7BA1\u7406");
		setBounds(300, 100, 520, 370);
		
		JLabel label = new JLabel("\u540D\u79F0\uFF1A");
		label.setBounds(10, 60, 60, 15);
		
		nameTxt = new JTextField();
		nameTxt.setBounds(137, 57, 127, 21);
		nameTxt.setText(name);
		nameTxt.setColumns(10);
		
		
		JLabel label_2 = new JLabel("\u622A\u6B62\u65E5\u671F\uFF1A");
		label_2.setBounds(10, 118, 84, 15);
		
		yearTxt = new JTextField();
		yearTxt.setBounds(137, 115, 66, 21);
		yearTxt.setText(year);
		yearTxt.setColumns(10);
		
		JLabel label_3 = new JLabel("\u5E74");
		label_3.setBounds(213, 114, 28, 23);
		
		monthTxt = new JTextField();
		monthTxt.setBounds(251, 115, 65, 21);
		monthTxt.setText(month);
		monthTxt.setColumns(10);
		
		dayTxt = new JTextField();
		dayTxt.setBounds(361, 115, 66, 21);
		dayTxt.setText(day);
		dayTxt.setColumns(10);
		
		JLabel label_4 = new JLabel("\u6708");
		label_4.setBounds(328, 116, 30, 18);
		
		JLabel label_6 = new JLabel("\u63CF\u8FF0\uFF1A");
		label_6.setBounds(10, 158, 66, 30);
		
		descTxt = new JTextArea();
		descTxt.setBounds(137, 169, 285, 85);
		descTxt.setText(desc);
		
		JLabel label_5 = new JLabel("\u65E5");
		label_5.setBounds(440, 114, 36, 23);
		
		JButton changeBtn = new JButton("\u4FEE\u6539");
		changeBtn.setIcon(new ImageIcon(TempTaskManageDialog.class.getResource("/images/Modify_16px_1060983_easyicon.net.png")));
		changeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tempTaskUpdate(arg0);
			}
		});
		changeBtn.setBounds(10, 286, 114, 23);
		
		JButton deleteBtn = new JButton("\u5220\u9664");
		deleteBtn.setIcon(new ImageIcon(TempTaskManageDialog.class.getResource("/images/delete_15.617747440273px_1195198_easyicon.net.png")));
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tempTaskDelete(arg0);
			}
		});
		deleteBtn.setBounds(335, 286, 107, 23);
		getContentPane().setLayout(null);
		getContentPane().add(changeBtn);
		getContentPane().add(label_2);
		getContentPane().add(label_6);
		getContentPane().add(label);
		getContentPane().add(nameTxt);
		getContentPane().add(yearTxt);
		getContentPane().add(label_3);
		getContentPane().add(monthTxt);
		getContentPane().add(label_4);
		getContentPane().add(dayTxt);
		getContentPane().add(label_5);
		getContentPane().add(descTxt);
		getContentPane().add(deleteBtn);
		
		JLabel label_1 = new JLabel("\u7F16\u53F7\uFF1A");
		label_1.setBounds(10, 10, 54, 15);
		getContentPane().add(label_1);
		
		idTxt = new JTextField();
		idTxt.setEditable(false);
		idTxt.setBounds(137, 7, 66, 21);
		idTxt.setText(id);
		getContentPane().add(idTxt);
		idTxt.setColumns(10);
		
		JButton completeBtn = new JButton("\u5B8C\u6210");
		completeBtn.setIcon(new ImageIcon(TempTaskManageDialog.class.getResource("/images/complete_16px_1143782_easyicon.net.png")));
		completeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tempTaskComplete(arg0);
			}
		});
		completeBtn.setBounds(182, 286, 99, 23);
		getContentPane().add(completeBtn);


	}
	

	private void tempTaskUpdate(ActionEvent evt) {
		String name = this.nameTxt.getText();
		String desc = this.descTxt.getText();
		int id = Integer.parseInt(this.idTxt.getText());
		
		if (Stringutil.isEmpty(name) || Stringutil.isEmpty(desc)) {
			JOptionPane.showMessageDialog(null, "请输入信息！");
		}
			
		String year = this.yearTxt.getText();
		String month = this.monthTxt.getText();
		String day = this.dayTxt.getText();
		if (!Dateutil.isLegal(year, month, day)) {
			JOptionPane.showMessageDialog(null, "输入的日期不合法!");
			return;
		} 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String dstr = year+"-"+month+"-"+day;
		Date date = null;
		java.sql.Date sqlDate = null;
		try {
			date=sdf.parse(dstr);
			sqlDate = new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("日期处理失败");
		}
		
		//异常处理
		if (Stringutil.isEmpty(name) || Stringutil.isEmpty(desc)) {
			JOptionPane.showMessageDialog(null, "修改内容不能为空!");
			return;
		}
		//实例化Task对象
		
		Connection con = null;
		Task task = new Task(id,listId,name,desc,0,1);
		TempTask tempTask = new TempTask(id,listId,name,desc,0,1,sqlDate);
		
		try {
			con = dbutil.getCon();			
			int n1 = taskDao.update(con, task);
			int n2 = tempTaskDao.update(con, tempTask);
			if (n1 == 1 && n2==1) {
				JOptionPane.showMessageDialog(null, "修改成功!");
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "修改失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbutil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void tempTaskDelete(ActionEvent evt) {
		int resId = Integer.parseInt(idTxt.getText());
		int n = JOptionPane.showConfirmDialog(null, "确认删除？");
		if (n == 0) {					// 选择是返回0
			Connection con = null;
			try {
				con = dbutil.getCon();
				int res = taskDao.delete(con, resId);
				int res1 = tempTaskDao.delete(con, resId);
				if (res == 1 && res1==1) {					//删除成功
					JOptionPane.showMessageDialog(null, "删除成功!");					
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "删除失败!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					dbutil.closeCon(con);		//关闭连接
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		}
	}
	
	protected void tempTaskComplete(ActionEvent arg0) {
		String name = this.nameTxt.getText();
		String desc = this.descTxt.getText();
		int id = Integer.parseInt(this.idTxt.getText());
		
		if (Stringutil.isEmpty(name) || Stringutil.isEmpty(desc)) {
			JOptionPane.showMessageDialog(null, "请输入信息！");
		}
			
		String year = this.yearTxt.getText();
		String month = this.monthTxt.getText();
		String day = this.dayTxt.getText();
		if (!Dateutil.isLegal(year, month, day)) {
			JOptionPane.showMessageDialog(null, "输入的日期不合法!");
			return;
		} 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String dstr = year+"-"+month+"-"+day;
		Date date = null;
		java.sql.Date sqlDate = null;
		try {
			date=sdf.parse(dstr);
			sqlDate = new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("日期处理失败");
		}
		
		//异常处理
		if (Stringutil.isEmpty(name) || Stringutil.isEmpty(desc)) {
			JOptionPane.showMessageDialog(null, "修改内容不能为空!");
			return;
		}
		//实例化Task对象
		Task task = new Task(id,listId,name,desc,1,1);
		TempTask tempTask = new TempTask(id,listId,name,desc,1,1,sqlDate);
		Connection con = null;
		try {
			con = dbutil.getCon();
			int n1 = taskDao.complete(con, task);
			int n2 = tempTaskDao.complete(con, tempTask);
			if (n1 == 1 && n2==1) {
				JOptionPane.showMessageDialog(null, "修改成功!");
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "修改失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbutil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
