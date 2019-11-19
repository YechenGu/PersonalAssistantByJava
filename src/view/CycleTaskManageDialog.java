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

import dao.CycleTaskDao;
import dao.PointerDao;
import dao.TaskDao;
import dao.TaskListDao;
import model.CycleTask;
import model.Task;
import util.Dateutil;
import util.Dbutil;
import util.Stringutil;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class CycleTaskManageDialog  extends JDialog {
	private JTextField nameTxt;
	private JTextField dayTxt;
	private JTextField monthTxt;
	private JTextField yearTxt;
	private JTextField cycleTxt;
	private JTextField timesTxt;
	private JTextArea descTxt;
	private int listId;
	
	private Dbutil dbutil = new Dbutil();
	private TaskListDao taskListDao = new TaskListDao();
	private TaskDao taskDao= new TaskDao();
	private CycleTaskDao cycleTaskDao = new CycleTaskDao();
	private PointerDao pointerDao = new PointerDao();
	private JTextField idTxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CycleTaskManageDialog frame = new CycleTaskManageDialog("","","","","","","","");
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
	public CycleTaskManageDialog(String id,String name,String desc,String year,String month,String day,String times,String cycle) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CycleTaskManageDialog.class.getResource("/images/checklist_16px_1142185_easyicon.net.png")));
		setModal(true);
		setResizable(false);
		setTitle("\u5468\u671F\u4EFB\u52A1\u7BA1\u7406");
		setBounds(300, 100, 495, 427);
		
		JLabel label = new JLabel("\u540D\u79F0\uFF1A");
		label.setBounds(10, 64, 60, 15);
		
		nameTxt = new JTextField();
		nameTxt.setBounds(130, 61, 123, 21);
		nameTxt.setText(name);
		nameTxt.setColumns(10);
		
		
		JLabel label_2 = new JLabel("\u6267\u884C\u65E5\u671F\uFF1A");
		label_2.setBounds(10, 108, 76, 15);
		
		JLabel label_3 = new JLabel("\u5E74");
		label_3.setBounds(209, 105, 22, 20);
		
		JLabel label_4 = new JLabel("\u6708");
		label_4.setBounds(310, 105, 20, 20);
		
		JLabel label_5 = new JLabel("\u65E5");
		label_5.setBounds(424, 103, 22, 24);
		
		dayTxt = new JTextField();
		dayTxt.setBounds(342, 105, 66, 21);
		dayTxt.setText(day);
		dayTxt.setColumns(10);
		
		monthTxt = new JTextField();
		monthTxt.setBounds(234, 105, 66, 21);
		monthTxt.setText(month);
		monthTxt.setColumns(10);
		
		yearTxt = new JTextField();
		yearTxt.setBounds(133, 105, 66, 21);
		yearTxt.setText(year);
		yearTxt.setColumns(10);
		
		cycleTxt = new JTextField();
		cycleTxt.setBounds(133, 171, 66, 21);
		cycleTxt.setText(cycle);
		cycleTxt.setColumns(10);
		
		JLabel label_6 = new JLabel("\u5468\u671F\uFF1A");
		label_6.setBounds(10, 169, 43, 24);
		
		JLabel label_7 = new JLabel("\u6B21\u6570\uFF1A");
		label_7.setBounds(296, 174, 53, 15);
		
		timesTxt = new JTextField();
		timesTxt.setBounds(380, 171, 66, 21);
		timesTxt.setText(times);
		timesTxt.setColumns(10);
		
		JLabel label_8 = new JLabel("\u63CF\u8FF0\uFF1A");
		label_8.setBounds(10, 224, 43, 24);
		
		descTxt = new JTextArea();
		descTxt.setBounds(130, 233, 294, 97);
		descTxt.setText(desc);
		
		JButton changeBtn = new JButton("\u4FEE\u6539");
		changeBtn.setIcon(new ImageIcon(CycleTaskManageDialog.class.getResource("/images/Modify_16px_1060983_easyicon.net.png")));
		changeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cycleTaskUpdate(e);
			}
		});
		changeBtn.setBounds(10, 351, 95, 23);
		
		JButton deleteBtn = new JButton("\u5220\u9664");
		deleteBtn.setIcon(new ImageIcon(CycleTaskManageDialog.class.getResource("/images/delete_15.617747440273px_1195198_easyicon.net.png")));
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cycleTaskDelete(arg0);
			}
		});
		deleteBtn.setBounds(344, 351, 93, 23);
		getContentPane().setLayout(null);
		getContentPane().add(label_2);
		getContentPane().add(label_3);
		getContentPane().add(label_4);
		getContentPane().add(label_5);
		getContentPane().add(dayTxt);
		getContentPane().add(monthTxt);
		getContentPane().add(yearTxt);
		getContentPane().add(cycleTxt);
		getContentPane().add(label_6);
		getContentPane().add(label_7);
		getContentPane().add(timesTxt);
		getContentPane().add(label_8);
		getContentPane().add(descTxt);
		getContentPane().add(changeBtn);
		getContentPane().add(deleteBtn);
		getContentPane().add(label);
		getContentPane().add(nameTxt);
		
		JLabel label_1 = new JLabel("\u7F16\u53F7:");
		label_1.setBounds(10, 21, 54, 15);
		getContentPane().add(label_1);
		
		idTxt = new JTextField();
		idTxt.setEditable(false);
		idTxt.setBounds(133, 18, 66, 21);
		idTxt.setText(id);
		getContentPane().add(idTxt);
		idTxt.setColumns(10);
		
		JButton completeBtn = new JButton("\u5B8C\u6210");
		completeBtn.setIcon(new ImageIcon(CycleTaskManageDialog.class.getResource("/images/complete_16px_1143782_easyicon.net.png")));
		completeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cycleTaskComplete(arg0);
			}
		});
		completeBtn.setBounds(183, 351, 93, 23);
		getContentPane().add(completeBtn);
		
		JLabel label_9 = new JLabel("\u5929");
		label_9.setBounds(219, 169, 22, 24);
		getContentPane().add(label_9);

	}
	
	private void cycleTaskUpdate(ActionEvent evt) {
		int id = Integer.parseInt(this.idTxt.getText());
		String name = this.nameTxt.getText();
		String desc = this.descTxt.getText();
		if (Stringutil.isEmpty(name) || Stringutil.isEmpty(desc) || !Stringutil.isNumeric(this.timesTxt.getText()) || !Stringutil.isNumeric(this.cycleTxt.getText())) {
			JOptionPane.showMessageDialog(null, "请输入信息！");
			return;
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
		
		int times = Integer.parseInt(this.timesTxt.getText());
		int cycle = Integer.parseInt(this.cycleTxt.getText());	
		
		Connection con = null;
		
		
		try {
			con = dbutil.getCon();
			Task task = new Task(id,listId,name,desc,0,2);
			CycleTask cycleTask = new CycleTask(id,listId,name,desc,0,2,sqlDate,times,cycle);
			int n1 = taskDao.update(con, task);
			int n2 = cycleTaskDao.update(con, cycleTask);
			if (n1==1 || n2==1) {
				JOptionPane.showMessageDialog(null, "修改成功");
				dispose();
			}else {
				JOptionPane.showMessageDialog(null, "修改失败");
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
	
	private void cycleTaskDelete(ActionEvent evt) {
		int resId = Integer.parseInt(idTxt.getText());
		int n = JOptionPane.showConfirmDialog(null, "确认删除？");
		if (n == 0) {					// 选择是返回0
			Connection con = null;
			try {
				con = dbutil.getCon();
				int res = taskDao.delete(con, resId);
				int res1 = cycleTaskDao.delete(con, resId);
				if (res == 1 && res1==1 ) {					//删除成功
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
	
	private void cycleTaskComplete(ActionEvent evt) {
		String name = this.nameTxt.getText();
		String desc = this.descTxt.getText();
		int id = Integer.parseInt(this.idTxt.getText());
		if (Stringutil.isEmpty(name) || Stringutil.isEmpty(desc) || !Stringutil.isNumeric(this.timesTxt.getText()) || !Stringutil.isNumeric(this.cycleTxt.getText())) {
			JOptionPane.showMessageDialog(null, "请输入信息！");
			return;
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
		
		int times = Integer.parseInt(this.timesTxt.getText());
		int cycle = Integer.parseInt(this.cycleTxt.getText());	
		
		Connection con = null;
		Task task = new Task(id,listId,name,desc,1,2);
		CycleTask cycleTask = new CycleTask(id,listId,name,desc,1,2,sqlDate,times,cycle);
		
		try {
			con = dbutil.getCon();
			int n1 = taskDao.complete(con, task);
			int n2 = cycleTaskDao.complete(con, cycleTask);
			if (n1==1 || n2==1) {
				JOptionPane.showMessageDialog(null, "修改成功");
				dispose();
			}else {
				JOptionPane.showMessageDialog(null, "修改失败");
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


