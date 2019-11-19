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
import model.TempTask;
import util.Dateutil;
import util.Dbutil;
import util.Stringutil;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class TempTaskAddInterFrm extends JDialog {
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TempTaskAddInterFrm frame = new TempTaskAddInterFrm(new JFrame(),0);
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
	public TempTaskAddInterFrm(JFrame frame,int taskListId) {
		super(frame,"短期任务添加",true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(TempTaskAddInterFrm.class.getResource("/images/checklist_16px_1142185_easyicon.net.png")));
		setResizable(false);
		setTitle("\u77ED\u671F\u4EFB\u52A1\u6DFB\u52A0");
		setBounds(300, 100, 520, 370);
		
		JLabel label = new JLabel("\u540D\u79F0\uFF1A");
		label.setBounds(10, 26, 60, 15);
		
		nameTxt = new JTextField();
		nameTxt.setBounds(137, 23, 127, 21);
		nameTxt.setColumns(10);
		
		
		JLabel label_2 = new JLabel("\u622A\u6B62\u65E5\u671F\uFF1A");
		label_2.setBounds(10, 103, 84, 15);
		
		yearTxt = new JTextField();
		yearTxt.setBounds(137, 100, 66, 21);
		yearTxt.setColumns(10);
		
		JLabel label_3 = new JLabel("\u5E74");
		label_3.setBounds(213, 99, 28, 23);
		
		monthTxt = new JTextField();
		monthTxt.setBounds(251, 100, 65, 21);
		monthTxt.setColumns(10);
		
		dayTxt = new JTextField();
		dayTxt.setBounds(353, 100, 66, 21);
		dayTxt.setColumns(10);
		
		JLabel label_4 = new JLabel("\u6708");
		label_4.setBounds(326, 101, 30, 18);
		
		JLabel label_6 = new JLabel("\u63CF\u8FF0\uFF1A");
		label_6.setBounds(10, 158, 66, 30);
		
		descTxt = new JTextArea();
		descTxt.setBounds(137, 169, 285, 85);
		
		JLabel label_5 = new JLabel("\u65E5");
		label_5.setBounds(437, 99, 36, 23);
		
		JButton addBtn = new JButton("\u6DFB\u52A0");
		addBtn.setIcon(new ImageIcon(TempTaskAddInterFrm.class.getResource("/images/add_16px_1232917_easyicon.net.png")));
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addActionPerformed(arg0);
			}
		});
		addBtn.setBounds(10, 286, 114, 23);
		
		JButton resetBtn = new JButton("\u91CD\u7F6E");
		resetBtn.setIcon(new ImageIcon(TempTaskAddInterFrm.class.getResource("/images/restart_16px_1168699_easyicon.net.png")));
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetValue();
			}
		});
		resetBtn.setBounds(335, 286, 107, 23);
		getContentPane().setLayout(null);
		getContentPane().add(addBtn);
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
		getContentPane().add(resetBtn);

		listId = taskListId;
	}
	
	private void addActionPerformed(ActionEvent evt) {
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
		
		try {
			con = dbutil.getCon();
			int pointer = pointerDao.getPointer(con);
			Task task = new Task(pointer,listId,name,desc,0,1);
			TempTask tempTask = new TempTask(pointer,listId,name,desc,0,1,sqlDate);
			int n1 = taskDao.add(con, task);
			int n2 = tempTaskDao.add(con, tempTask);
			int n3 = pointerDao.increase(con);
			if (n1==1 && n2==1 && n3==1) {
				JOptionPane.showMessageDialog(null, "添加成功");
				resetValue();
			}else {
				JOptionPane.showMessageDialog(null, "添加失败");
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

	private void resetValue() {
		this.nameTxt.setText("");
		this.yearTxt.setText("");
		this.monthTxt.setText("");
		this.dayTxt.setText("");
		this.descTxt.setText("");
	}

//	private void fillComboBox() {
//		Connection connection = null;
//		TaskList taskList = null;
//		try {
//			connection = dbutil.getCon();
//			ResultSet resultSet = taskListDao.list(connection, new TaskList());
//			while (resultSet.next()) {
//				taskList = new TaskList();
//				taskList.setId(resultSet.getInt("id"));
//				taskList.setName(resultSet.getString("name"));
//				comboBox.addItem(taskList);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				dbutil.closeCon(connection);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//	}
	

}
