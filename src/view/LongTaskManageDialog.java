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

import dao.LongTaskDao;
import dao.PointerDao;
import dao.TaskDao;
import dao.TaskListDao;
import model.LongTask;
import model.Task;
import util.Dateutil;
import util.Dbutil;
import util.Stringutil;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class LongTaskManageDialog extends JDialog {
	private JTextField nameTxt;
	private JTextField yearTxt;
	private JTextField monthTxt;
	private JTextField dayTxt;
	private JTextArea descTxt;
	private int listId;
	
	private Dbutil dbutil = new Dbutil();
	private TaskListDao taskListDao = new TaskListDao();
	private TaskDao taskDao= new TaskDao();
	private LongTaskDao longTaskDao = new LongTaskDao();
	private PointerDao pointerDao = new PointerDao();
	private JTextField idTxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LongTaskManageDialog frame = new LongTaskManageDialog("","","","","","");
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
	public LongTaskManageDialog(String id,String name,String desc,String year,String month,String day) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LongTaskManageDialog.class.getResource("/images/checklist_16px_1142185_easyicon.net.png")));
		setModal(true);
		setResizable(false);
		setTitle("\u957F\u671F\u4EFB\u52A1\u7BA1\u7406");
		setBounds(300, 100, 532, 392);
		
		JLabel label = new JLabel("\u540D\u79F0\uFF1A");
		label.setBounds(35, 69, 83, 15);
		
		JLabel label_1 = new JLabel("\u622A\u6B62\u65E5\u671F\uFF1A");
		label_1.setBounds(35, 128, 71, 15);
		
		JLabel label_2 = new JLabel("\u5E74");
		label_2.setBounds(202, 122, 23, 26);
		
		JLabel label_3 = new JLabel("\u6708");
		label_3.setBounds(311, 125, 23, 21);
		
		JLabel label_4 = new JLabel("\u65E5");
		label_4.setBounds(438, 125, 24, 21);
		
		JLabel label_5 = new JLabel("\u63CF\u8FF0\uFF1A");
		label_5.setBounds(35, 180, 43, 26);
		
		nameTxt = new JTextField();
		nameTxt.setBounds(128, 66, 200, 21);
		nameTxt.setText(name);
		nameTxt.setColumns(10);
		
		yearTxt = new JTextField();
		yearTxt.setBounds(126, 125, 66, 21);
		yearTxt.setText(year);
		yearTxt.setColumns(10);
		
		monthTxt = new JTextField();
		monthTxt.setBounds(235, 125, 66, 21);
		monthTxt.setText(month);
		monthTxt.setColumns(10);
		
		dayTxt = new JTextField();
		dayTxt.setBounds(344, 128, 74, 21);
		dayTxt.setText(day);
		dayTxt.setColumns(10);
		
		descTxt = new JTextArea();
		descTxt.setBounds(122, 187, 296, 89);
		descTxt.setText(desc);
		
		JButton changeBtn = new JButton("\u4FEE\u6539");
		changeBtn.setIcon(new ImageIcon(LongTaskManageDialog.class.getResource("/images/Modify_16px_1060983_easyicon.net.png")));
		changeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				longTaskUpdate(e);
			}
		});
		changeBtn.setBounds(35, 330, 83, 23);
		
		JButton deleteBtn = new JButton("\u5220\u9664");
		deleteBtn.setIcon(new ImageIcon(LongTaskManageDialog.class.getResource("/images/delete_15.617747440273px_1195198_easyicon.net.png")));
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				longTaskDelete(e);
			}
		});
		deleteBtn.setBounds(412, 330, 80, 23);
		getContentPane().setLayout(null);
		getContentPane().add(label);
		getContentPane().add(label_1);
		getContentPane().add(label_2);
		getContentPane().add(label_3);
		getContentPane().add(label_4);
		getContentPane().add(label_5);
		getContentPane().add(nameTxt);
		getContentPane().add(yearTxt);
		getContentPane().add(monthTxt);
		getContentPane().add(dayTxt);
		getContentPane().add(descTxt);
		getContentPane().add(changeBtn);
		getContentPane().add(deleteBtn);
		
		JLabel label_6 = new JLabel("\u7F16\u53F7\uFF1A");
		label_6.setBounds(35, 26, 54, 15);
		getContentPane().add(label_6);
		
		idTxt = new JTextField();
		idTxt.setEditable(false);
		idTxt.setText(id);
		idTxt.setBounds(128, 23, 66, 21);
		getContentPane().add(idTxt);
		idTxt.setColumns(10);
		
		JButton completeBtn = new JButton("\u5B8C\u6210");
		completeBtn.setIcon(new ImageIcon(LongTaskManageDialog.class.getResource("/images/complete_16px_1143782_easyicon.net.png")));
		completeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				longTaskComplete(e);
			}
		});
		completeBtn.setBounds(165, 330, 93, 23);
		getContentPane().add(completeBtn);
		
		JButton subTaskBtn = new JButton("\u67E5\u770B\u5B50\u4EFB\u52A1");
		subTaskBtn.setIcon(new ImageIcon(LongTaskManageDialog.class.getResource("/images/query_search_16px_1135839_easyicon.net.png")));
		subTaskBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SubTaskDialog subTaskDialog = new SubTaskDialog(0,Integer.parseInt(idTxt.getText()));
				subTaskDialog.setVisible(true);
			}
		});
		subTaskBtn.setBounds(279, 330, 123, 23);
		getContentPane().add(subTaskBtn);

	}

	private void longTaskUpdate(ActionEvent evt) {
		int id = Integer.parseInt(this.idTxt.getText());
		String name = this.nameTxt.getText();
		String desc = this.descTxt.getText();
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

		
		Connection con = null;
		Task task = new Task(id,listId,name,desc,0,3);
		LongTask longTask = new LongTask(id,listId,name,desc,0,3, sqlDate);
		
		try {
			con = dbutil.getCon();
			int n1 = taskDao.update(con, task);
			int n2 = longTaskDao.update(con, longTask);
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
	
	private void longTaskDelete(ActionEvent evt) {
		int resId = Integer.parseInt(idTxt.getText());
		int n = JOptionPane.showConfirmDialog(null, "确认删除？");
		if (n == 0) {					// 选择是返回0
			Connection con = null;
			try {
				con = dbutil.getCon();
				int res = taskDao.delete(con, resId);
				int res1 = longTaskDao.delete(con, resId);
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
	
	private void longTaskComplete(ActionEvent evt) {
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

		
		Connection con = null;
		Task task = new Task(id,listId,name,desc,1,3);
		LongTask longTask = new LongTask(id,listId,name,desc,1,3, sqlDate);
		
		try {
			con = dbutil.getCon();
			int n1 = taskDao.complete(con, task);
			int n2 = longTaskDao.complete(con, longTask);
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


