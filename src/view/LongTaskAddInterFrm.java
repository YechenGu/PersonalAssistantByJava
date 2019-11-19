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

public class LongTaskAddInterFrm extends JDialog {
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LongTaskAddInterFrm frame = new LongTaskAddInterFrm(new JFrame(),0);
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
	public LongTaskAddInterFrm(JFrame frame,int taskListId) {
		super(frame,"长期任务添加",true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LongTaskAddInterFrm.class.getResource("/images/checklist_16px_1142185_easyicon.net.png")));
		setResizable(false);
		setTitle("\u957F\u671F\u4EFB\u52A1\u6DFB\u52A0");
		setBounds(300, 100, 532, 392);
		
		JLabel label = new JLabel("\u540D\u79F0\uFF1A");
		label.setBounds(35, 54, 83, 15);
		
		JLabel label_1 = new JLabel("\u622A\u6B62\u65E5\u671F\uFF1A");
		label_1.setBounds(35, 128, 71, 15);
		
		JLabel label_2 = new JLabel("\u5E74");
		label_2.setBounds(202, 122, 23, 26);
		
		JLabel label_3 = new JLabel("\u6708");
		label_3.setBounds(311, 125, 23, 21);
		
		JLabel label_4 = new JLabel("\u65E5");
		label_4.setBounds(438, 125, 24, 21);
		
		JLabel label_5 = new JLabel("\u63CF\u8FF0\uFF1A");
		label_5.setBounds(35, 216, 46, 26);
		
		nameTxt = new JTextField();
		nameTxt.setBounds(126, 51, 200, 21);
		nameTxt.setColumns(10);
		
		yearTxt = new JTextField();
		yearTxt.setBounds(126, 125, 66, 21);
		yearTxt.setColumns(10);
		
		monthTxt = new JTextField();
		monthTxt.setBounds(235, 125, 66, 21);
		monthTxt.setColumns(10);
		
		dayTxt = new JTextField();
		dayTxt.setBounds(344, 128, 74, 21);
		dayTxt.setColumns(10);
		
		descTxt = new JTextArea();
		descTxt.setBounds(122, 223, 296, 89);
		
		JButton addBtn = new JButton("\u6DFB\u52A0");
		addBtn.setIcon(new ImageIcon(LongTaskAddInterFrm.class.getResource("/images/add_16px_1232917_easyicon.net.png")));
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addActionPerformed(e);
			}
		});
		addBtn.setBounds(35, 330, 96, 23);
		
		JButton resetBtn = new JButton("\u91CD\u7F6E");
		resetBtn.setIcon(new ImageIcon(LongTaskAddInterFrm.class.getResource("/images/restart_16px_1168699_easyicon.net.png")));
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetValue();
			}
		});
		resetBtn.setBounds(382, 330, 90, 23);
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
		getContentPane().add(addBtn);
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
			Task task = new Task(pointer,listId,name,desc,0,3);
			LongTask longTask = new LongTask(pointer,listId,name,desc,0,3, sqlDate);
			int n1 = taskDao.add(con, task);
			int n2 = longTaskDao.add(con, longTask);
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
	

}
