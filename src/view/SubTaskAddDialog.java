package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.SubTaskDao;
import model.SubTask;
import model.Task;
import model.TempTask;
import util.Dateutil;
import util.Dbutil;
import util.Stringutil;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class SubTaskAddDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dbutil dbutil = new Dbutil();
	private SubTaskDao subTaskDao= new SubTaskDao();
	private JButton addBtn;
	private JTextField nameTxt;
	private JTextField yearTxt;
	private JTextField monthTxt;
	private JTextField dayTxt;
	private int state;
	private int father;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SubTaskAddDialog dialog = new SubTaskAddDialog(0,0);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SubTaskAddDialog(int state,int father) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SubTaskAddDialog.class.getResource("/images/checklist_16px_1142185_easyicon.net.png")));
		setTitle("\u5B50\u4EFB\u52A1\u6DFB\u52A0");
		setModal(true);
		setBounds(200, 200, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			addBtn = new JButton("\u6DFB\u52A0");
			addBtn.setIcon(new ImageIcon(SubTaskAddDialog.class.getResource("/images/add_16px_1232917_easyicon.net.png")));
			addBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addActionPerformed(e);
				}
			});
			addBtn.setActionCommand("OK");
			getRootPane().setDefaultButton(addBtn);
		}
		
		JLabel label = new JLabel("\u540D\u79F0\uFF1A");
		
		nameTxt = new JTextField();
		nameTxt.setColumns(10);
		
		JLabel label_1 = new JLabel("\u622A\u6B62\u65E5\u671F\uFF1A");
		
		yearTxt = new JTextField();
		yearTxt.setColumns(10);
		
		JLabel label_2 = new JLabel("\u5E74");
		
		monthTxt = new JTextField();
		monthTxt.setColumns(10);
		
		JLabel label_3 = new JLabel("\u6708");
		
		dayTxt = new JTextField();
		dayTxt.setColumns(10);
		
		JLabel label_4 = new JLabel("\u65E5");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(39)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(yearTxt, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(monthTxt, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dayTxt, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addComponent(nameTxt, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
					.addGap(8))
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addContainerGap(166, Short.MAX_VALUE)
					.addComponent(addBtn, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addGap(157))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(nameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(56)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(yearTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(monthTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(dayTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addGap(77)
					.addComponent(addBtn)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		
		this.state = state;
		this.father = father;
	}

	private void addActionPerformed(ActionEvent evt) {
		String name = this.nameTxt.getText();
		
		if (Stringutil.isEmpty(name)) {
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
		
		SubTask task;
		Connection con = null;		
		
		try {
			con = dbutil.getCon();
			if (state==0) {
				task = new SubTask(name, sqlDate, 0, father);
			} else {
				task = new SubTask(name, sqlDate, father, 0);
			}
			int n1 = subTaskDao.add(con, task);
			if (n1 ==1 ) {
				JOptionPane.showMessageDialog(null, "添加成功");
				dispose();
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
}
