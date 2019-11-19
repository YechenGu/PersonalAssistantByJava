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
import javax.swing.border.EmptyBorder;

import dao.TaskListDao;
import model.TaskList;
import util.Dbutil;
import util.Stringutil;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

/**
 * 任务列表添加的窗口类
 * @author Administrator
 *
 */

public class TaskListAddInterFrm extends JDialog {

	private JPanel contentPane;
	private JTextField listNameTxt;
	private JTextField listTypeTxt;
	
	private Dbutil dbutil = new Dbutil();
	private TaskListDao taskListDao = new TaskListDao(); 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskListAddInterFrm frame = new TaskListAddInterFrm();
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
	public TaskListAddInterFrm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TaskListAddInterFrm.class.getResource("/images/checklist_16px_1142185_easyicon.net.png")));
		setResizable(false);
		setModal(true);
		setTitle("\u4EFB\u52A1\u7C7B\u522B\u6DFB\u52A0");
		setBounds(400, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel label = new JLabel("\u7C7B\u522B\u540D\u79F0\uFF1A");
		
		JLabel label_1 = new JLabel("\u7C7B\u522B\u7C7B\u578B\uFF1A");
		
		listNameTxt = new JTextField();
		listNameTxt.setColumns(20);
		
		listTypeTxt = new JTextField();
		listTypeTxt.setColumns(20);
		
		JButton button = new JButton("\u6DFB\u52A0");
		button.setIcon(new ImageIcon(TaskListAddInterFrm.class.getResource("/images/add_16px_1232917_easyicon.net.png")));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listAddActionPerformed(arg0);
			}
		});
		
		JButton button_1 = new JButton("\u91CD\u7F6E");
		button_1.setIcon(new ImageIcon(TaskListAddInterFrm.class.getResource("/images/restart_16px_1168699_easyicon.net.png")));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetValueActionPerformed(arg0);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(40)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(label_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(43)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(listTypeTxt)
								.addComponent(listNameTxt, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGap(60)
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(77, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(71)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(listNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(44)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(listTypeTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_1)
						.addComponent(button))
					.addGap(32))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	private void listAddActionPerformed(ActionEvent evt) {
		String name = this.listNameTxt.getText();
		String type = this.listTypeTxt.getText();
		if (Stringutil.isEmpty(name) || Stringutil.isEmpty(type)) {
			JOptionPane.showMessageDialog(null, "名称或类型不能为空");
			return;
		}	
			TaskList taskList = new TaskList(name, type);
			Connection con = null;
			try {
				con = dbutil.getCon();			//使用工具类获得连接
				int n = taskListDao.add(con, taskList);	 		//调用数据操作类的方法获得操作结果
				if (n == 1) {
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

	private void resetValueActionPerformed(ActionEvent evt) {
		this.resetValue();
	}

	
	//方法分离，方便不同主体调用
	private void resetValue() {
		this.listNameTxt.setText("");
		this.listTypeTxt.setText("");	
	}
	
}
