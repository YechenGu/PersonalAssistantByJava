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

public class CycleTaskAddInterFrm extends JDialog {
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CycleTaskAddInterFrm frame = new CycleTaskAddInterFrm(new JFrame(),0);
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
	public CycleTaskAddInterFrm(JFrame frame,int taskListId) {
		super(frame,"周期任务添加",true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(CycleTaskAddInterFrm.class.getResource("/images/checklist_16px_1142185_easyicon.net.png")));
		setResizable(false);
		setTitle("\u5468\u671F\u4EFB\u52A1\u6DFB\u52A0");
		setBounds(300, 100, 495, 427);
		
		JLabel label = new JLabel("\u540D\u79F0\uFF1A");
		label.setBounds(10, 42, 60, 15);
		
		nameTxt = new JTextField();
		nameTxt.setBounds(130, 39, 123, 21);
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
		dayTxt.setColumns(10);
		
		monthTxt = new JTextField();
		monthTxt.setBounds(234, 105, 66, 21);
		monthTxt.setColumns(10);
		
		yearTxt = new JTextField();
		yearTxt.setBounds(133, 105, 66, 21);
		yearTxt.setColumns(10);
		
		cycleTxt = new JTextField();
		cycleTxt.setBounds(133, 171, 66, 21);
		cycleTxt.setColumns(10);
		
		JLabel label_6 = new JLabel("\u5468\u671F\uFF1A");
		label_6.setBounds(10, 169, 45, 24);
		
		JLabel label_7 = new JLabel("\u6B21\u6570\uFF1A");
		label_7.setBounds(296, 174, 53, 15);
		
		timesTxt = new JTextField();
		timesTxt.setBounds(380, 171, 66, 21);
		timesTxt.setColumns(10);
		
		JLabel label_8 = new JLabel("\u63CF\u8FF0\uFF1A");
		label_8.setBounds(10, 224, 45, 24);
		
		descTxt = new JTextArea();
		descTxt.setBounds(130, 233, 294, 97);
		
		JButton addBtn = new JButton("\u6DFB\u52A0");
		addBtn.setIcon(new ImageIcon(CycleTaskAddInterFrm.class.getResource("/images/add_16px_1232917_easyicon.net.png")));
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addActionPerformed(e);
			}
		});
		addBtn.setBounds(10, 351, 95, 23);
		
		JButton resetBtn = new JButton("\u91CD\u7F6E");
		resetBtn.setIcon(new ImageIcon(CycleTaskAddInterFrm.class.getResource("/images/restart_16px_1168699_easyicon.net.png")));
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetValue();
			}
		});
		resetBtn.setBounds(329, 351, 95, 23);
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
		getContentPane().add(addBtn);
		getContentPane().add(resetBtn);
		getContentPane().add(label);
		getContentPane().add(nameTxt);
		
		JLabel label_1 = new JLabel("\u5929");
		label_1.setBounds(223, 169, 22, 24);
		getContentPane().add(label_1);

		listId = taskListId;
	}

	private void addActionPerformed(ActionEvent evt) {
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
			int pointer = pointerDao.getPointer(con);
			Task task = new Task(pointer,listId,name,desc,0,2);
			CycleTask cycleTask = new CycleTask(pointer,listId,name,desc,0,2,sqlDate,times,cycle);
			int n1 = taskDao.add(con, task);
			int n2 = cycleTaskDao.add(con, cycleTask);
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
		this.timesTxt.setText("");
		this.cycleTxt.setText("");
	}
}
