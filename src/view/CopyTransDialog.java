package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.html.HTMLIsIndexElement;

import dao.CycleTaskDao;
import dao.LongTaskDao;
import dao.PointerDao;
import dao.TaskDao;
import dao.TaskListDao;
import dao.TempTaskDao;
import model.CycleTask;
import model.LongTask;
import model.Task;
import model.TaskList;
import model.TempTask;
import util.Dbutil;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class CopyTransDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	private JComboBox comboBox;
	private JButton okBtn;
	private TaskListDao taskListDao = new TaskListDao();
	private TaskDao taskDao= new TaskDao();
	private TempTaskDao tempTaskDao = new TempTaskDao();
	private CycleTaskDao cycleTaskDao = new CycleTaskDao();
	private LongTaskDao longTaskDao = new LongTaskDao();
	private PointerDao pointerDao = new PointerDao();
	private Dbutil dbutil = new Dbutil();
	private Task sourceTask;
	private Task sourceCertainTask;
	private int outListId;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CopyTransDialog dialog = new CopyTransDialog(0,0,new Task(),new Task());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CopyTransDialog(int status,int listId,Task sourceTask,Task sourceCertainTask) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CopyTransDialog.class.getResource("/images/checklist_16px_1142185_easyicon.net.png")));
		setTitle("\u590D\u5236\u4EFB\u52A1");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 372, 227);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		if (status==1) {
			setTitle("任务转移");			
		}
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblNewLabel = new JLabel("\u76EE\u6807\u4EFB\u52A1\u6E05\u5355\uFF1A");
		}
		{
			comboBox = new JComboBox();
		}
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addGap(32)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addGap(45))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(51)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(67, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okBtn = new JButton("\u786E\u8BA4");
				okBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (status==0) {
							copyActionPerformed(arg0);
						} else {
							transActionPerformed(arg0);
						}
					}
				});
				okBtn.setActionCommand("OK");
				buttonPane.add(okBtn);
				getRootPane().setDefaultButton(okBtn);
			}
		}
		outListId = listId;
		this.sourceTask = sourceTask;
		this.sourceCertainTask = sourceCertainTask;
		fillComboBox();
	}

	private void copyActionPerformed(ActionEvent arg0) {
		Connection con = null;	
		TaskList taskList = (TaskList) comboBox.getSelectedItem();
		int desId = taskList.getId();
		if (sourceCertainTask instanceof TempTask) {
			try {
				con = dbutil.getCon();
				int pointer = pointerDao.getPointer(con);
				TempTask tempTask = (TempTask) sourceCertainTask;
				sourceTask.setId(pointer);
				sourceTask.setListId(desId);
				tempTask.setId(pointer);
				tempTask.setListId(desId);
				int n1 = taskDao.add(con, sourceTask);
				int n2 = tempTaskDao.add(con,tempTask);
				int n3 = pointerDao.increase(con);
				if (n1==1 && n2==1 && n3==1) {
					JOptionPane.showMessageDialog(null, "复制成功");
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "复制失败");
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
		if (sourceCertainTask instanceof CycleTask) {
			try {
				con = dbutil.getCon();
				int pointer = pointerDao.getPointer(con);
				CycleTask cycleTask = (CycleTask) sourceCertainTask;
				sourceTask.setId(pointer);
				sourceTask.setListId(desId);
				cycleTask.setId(pointer);
				cycleTask.setListId(desId);
				int n1 = taskDao.add(con, sourceTask);
				int n2 = cycleTaskDao.add(con,cycleTask);
				int n3 = pointerDao.increase(con);
				if (n1==1 && n2==1 && n3==1) {
					JOptionPane.showMessageDialog(null, "复制成功");
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "复制失败");
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
		if (sourceCertainTask instanceof LongTask) {
			try {
				con = dbutil.getCon();
				int pointer = pointerDao.getPointer(con);
				LongTask longTask = (LongTask) sourceCertainTask;
				sourceTask.setId(pointer);
				sourceTask.setListId(desId);
				longTask.setId(pointer);
				longTask.setListId(desId);
				int n1 = taskDao.add(con, sourceTask);
				int n2 = longTaskDao.add(con,longTask);
				int n3 = pointerDao.increase(con);
				if (n1==1 && n2==1 && n3==1) {
					JOptionPane.showMessageDialog(null, "复制成功");
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "复制失败");
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
	
	private void transActionPerformed(ActionEvent arg0) {		
		Connection con = null;	
		TaskList taskList = (TaskList) comboBox.getSelectedItem();
		int desId = taskList.getId();
		if (sourceCertainTask instanceof TempTask) {
			try {
				con = dbutil.getCon();
				int n4 = taskDao.delete(con, sourceTask.getId());
				int n5 = tempTaskDao.delete(con, sourceCertainTask.getId());
				int pointer = pointerDao.getPointer(con);
				TempTask tempTask = (TempTask) sourceCertainTask;
				sourceTask.setId(pointer);
				sourceTask.setListId(desId);
				tempTask.setId(pointer);
				tempTask.setListId(desId);
				int n1 = taskDao.add(con, sourceTask);
				int n2 = tempTaskDao.add(con,tempTask);
				int n3 = pointerDao.increase(con);
				if (n1==1 && n2==1 && n3==1 && n4==1 && n5==1) {
					JOptionPane.showMessageDialog(null, "转移成功");
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "转移失败");
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
		if (sourceCertainTask instanceof CycleTask) {
			try {
				con = dbutil.getCon();
				int n4 = taskDao.delete(con, sourceTask.getId());
				int n5 = cycleTaskDao.delete(con, sourceCertainTask.getId());
				int pointer = pointerDao.getPointer(con);
				CycleTask cycleTask = (CycleTask) sourceCertainTask;
				sourceTask.setId(pointer);
				sourceTask.setListId(desId);
				cycleTask.setId(pointer);
				cycleTask.setListId(desId);
				int n1 = taskDao.add(con, sourceTask);
				int n2 = cycleTaskDao.add(con,cycleTask);
				int n3 = pointerDao.increase(con);
				if (n1==1 && n2==1 && n3==1 && n4==1 && n5==1) {
					JOptionPane.showMessageDialog(null, "转移成功");
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "转移失败");
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
		if (sourceCertainTask instanceof LongTask) {
			try {
				con = dbutil.getCon();
				int n4 = taskDao.delete(con, sourceTask.getId());
				int n5 = longTaskDao.delete(con, sourceCertainTask.getId());
				int pointer = pointerDao.getPointer(con);
				LongTask longTask = (LongTask) sourceCertainTask;
				sourceTask.setId(pointer);
				sourceTask.setListId(desId);
				longTask.setId(pointer);
				longTask.setListId(desId);
				int n1 = taskDao.add(con, sourceTask);
				int n2 = longTaskDao.add(con,longTask);
				int n3 = pointerDao.increase(con);
				if (n1==1 && n2==1 && n3==1&& n4==1 && n5==1) {
					JOptionPane.showMessageDialog(null, "转移成功");
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "转移失败");
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



	private void fillComboBox() {
	Connection connection = null;
	TaskList taskList = null;
	try {
		connection = dbutil.getCon();
		ResultSet resultSet = taskListDao.list(connection, new TaskList());
		while (resultSet.next()) {
			taskList = new TaskList();
			taskList.setId(resultSet.getInt("id"));
			taskList.setName(resultSet.getString("name"));
			if (outListId != resultSet.getInt("id")) {
				comboBox.addItem(taskList);
			}			
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
			dbutil.closeCon(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

}
