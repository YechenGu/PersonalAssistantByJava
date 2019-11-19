package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import dao.CycleTaskDao;
import dao.LongTaskDao;
import dao.TaskDao;
import dao.TaskListDao;
import dao.TempTaskDao;
import model.TaskList;
import util.Dbutil;
import util.Stringutil;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 * 任务列表管理的窗口类
 * @author Administrator
 *
 */

public class TaskListManageInterFrm extends JDialog {
	
	private Dbutil dbutil = new Dbutil();	//数据库工具类
	private TaskListDao taskListDao = new TaskListDao();	//数据库操作执行类 
	private TaskDao taskDao = new TaskDao();
	private TempTaskDao tempTaskDao = new TempTaskDao();
	private CycleTaskDao cycleTaskDao = new CycleTaskDao();
	private LongTaskDao longTaskDao = new LongTaskDao();
	private JTextField idTxt;
	private JTextField nameTxt;
	private JTextField typeTxt;
	//实例化两个对象

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskListManageInterFrm frame = new TaskListManageInterFrm("","","");
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
	public TaskListManageInterFrm(String id,String name,String type) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TaskListManageInterFrm.class.getResource("/images/checklist_16px_1142185_easyicon.net.png")));
		setResizable(false);
		setModal(true);
		setTitle("\u5217\u8868\u7BA1\u7406");
		setBounds(400, 100, 360, 400);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u5217\u8868\u64CD\u4F5C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(22, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE)
					.addGap(20))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(13, Short.MAX_VALUE))
		);
		
		JLabel label_1 = new JLabel("\u7F16\u53F7\uFF1A");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel label_2 = new JLabel("\u540D\u79F0\uFF1A");
		
		JLabel label_3 = new JLabel("\u7C7B\u578B\uFF1A");
		
		idTxt = new JTextField();
		idTxt.setEditable(false);
		idTxt.setColumns(10);
		idTxt.setText(id);
		
		nameTxt = new JTextField();
		nameTxt.setColumns(10);
		nameTxt.setText(name);
		
		typeTxt = new JTextField();
		typeTxt.setColumns(10);
		typeTxt.setText(type);
		
		
		JButton updateBtn = new JButton("\u4FEE\u6539");
		updateBtn.setIcon(new ImageIcon(TaskListManageInterFrm.class.getResource("/images/Modify_16px_1060983_easyicon.net.png")));
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				taskListUpdate(arg0);
			}
		});
		
		JButton deleteBtn = new JButton("\u5220\u9664");
		deleteBtn.setIcon(new ImageIcon(TaskListManageInterFrm.class.getResource("/images/delete_15.617747440273px_1195198_easyicon.net.png")));
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				taskListDelete(arg0);
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addComponent(updateBtn, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
							.addComponent(deleteBtn, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addContainerGap()
									.addComponent(label_3, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
									.addGap(30))
								.addGroup(gl_panel.createSequentialGroup()
									.addContainerGap()
									.addComponent(label_2, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addGap(28)))
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(idTxt, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 99, Short.MAX_VALUE))
								.addComponent(nameTxt, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
								.addComponent(typeTxt, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(idTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(36)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(nameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(58)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_3)
						.addComponent(typeTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(updateBtn)
						.addComponent(deleteBtn))
					.addGap(43))
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

		
	}
	
	/**
	 * 任务列表删除
	 * @param evt
	 */
	private void taskListDelete(ActionEvent evt) {
		String resId = idTxt.getText();
		int intId = Integer.parseInt(resId);
		if (Stringutil.isEmpty(resId)) {
			JOptionPane.showMessageDialog(null, "请进行选择!");
			return;
		}
		int n = JOptionPane.showConfirmDialog(null, "确认删除？");
		if (n == 0) {					// 选择是返回0
			Connection con = null;
			try {
				con = dbutil.getCon();
				int res = taskListDao.delete(con, resId);

				if (res == 1 ) {					//删除成功
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

	private void taskListUpdate(ActionEvent evt) {
		String resId = idTxt.getText();
		String resName = nameTxt.getText();
		String resType = typeTxt.getText();
		//异常处理
		if (Stringutil.isEmpty(resId)) {
			JOptionPane.showMessageDialog(null, "请进行选择!");
			return;
		}
		if (Stringutil.isEmpty(resName) || Stringutil.isEmpty(resType)) {
			JOptionPane.showMessageDialog(null, "修改内容不能为空!");
			return;
		}
		//实例化TaskList对象
		TaskList taskList = new TaskList(Integer.parseInt(resId), resName, resType);
		Connection con = null;
		try {
			con = dbutil.getCon();
			int res = taskListDao.update(con, taskList);
			if (res == 1) {
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
