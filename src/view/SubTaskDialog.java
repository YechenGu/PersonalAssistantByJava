package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.SubTaskDao;
import model.SubTask;
import util.Dbutil;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class SubTaskDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton addBtn;
	private JButton lookBtn;
	private JTable table;
	private Dbutil dbutil = new Dbutil();
	private SubTaskDao subTaskDao= new SubTaskDao();
	private int state;
	private int father;
	private int selectedRow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SubTaskDialog dialog = new SubTaskDialog(0,0);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SubTaskDialog(int state,int father) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SubTaskDialog.class.getResource("/images/checklist_16px_1142185_easyicon.net.png")));
		setTitle("\u5B50\u4EFB\u52A1");
		setModal(true);
		setBounds(200-father, 200-father, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			addBtn = new JButton("\u65B0\u5EFA");
			addBtn.setIcon(new ImageIcon(SubTaskDialog.class.getResource("/images/add_16px_1232917_easyicon.net.png")));
			addBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					subTaskAddActionPerformed(arg0);
				}
			});
			addBtn.setActionCommand("OK");
			getRootPane().setDefaultButton(addBtn);
		}
		{
			lookBtn = new JButton("\u67E5\u770B");
			lookBtn.setIcon(new ImageIcon(SubTaskDialog.class.getResource("/images/query_search_16px_1135839_easyicon.net.png")));
			lookBtn.setEnabled(false);
			lookBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lookActionPerformed(e);
				}
			});
			lookBtn.setActionCommand("Cancel");
		}
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
					.addGap(42)
					.addComponent(addBtn, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addGap(184)
					.addComponent(lookBtn, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(23, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap(99, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
					.addGap(70))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(28)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(addBtn)
						.addComponent(lookBtn))
					.addContainerGap())
		);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				mouseActionPerformed(arg0);
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u7F16\u53F7", "\u540D\u79F0"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		contentPanel.setLayout(gl_contentPanel);
		this.state = state;
		this.father = father;
		fillTable(new SubTask());
	}

	private void lookActionPerformed(ActionEvent evt) {
		SubTaskDialog subTaskDialog = new SubTaskDialog(1,selectedRow);
		subTaskDialog.setVisible(true);
	}

	private void subTaskAddActionPerformed(ActionEvent arg0) {
		SubTaskAddDialog subTaskAddDialog = new SubTaskAddDialog(state, father);
		subTaskAddDialog.setVisible(true);
		SubTask task = new SubTask();
		if (state==0) {
			task.setFatherTask(father);
		} else {
			task.setFather(father);
		}
		this.fillTable(task);
	}

	private void mouseActionPerformed(MouseEvent evt) {
		lookBtn.setEnabled(true);
		int row = table.getSelectedRow();				//获得选中的id
		String id1 = (String)table.getValueAt(row,0);
		int id = Integer.valueOf(id1);
		selectedRow = id;
		SubTask task = new SubTask();
		this.fillTable(task);
		
	}
	
	private void fillTable(SubTask task) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		Connection con = null;
		ResultSet resultSet;
		Boolean isEmpty = true;
		if (state==0) {
			task.setFatherTask(father);
		} else {
			task.setFather(father);
		}
		try {
			con = dbutil.getCon();
			if (state==0) {
				resultSet = subTaskDao.listFromTask(con, task);
			}else {
				resultSet = subTaskDao.listFromSub(con, task);
			}			
			while (resultSet.next()) {
				//如果还有下一行，使用Vector容器
				Vector vector = new Vector();
				vector.add(resultSet.getString("id"));
				vector.add(resultSet.getString("name"));
				dtm.addRow(vector);
				isEmpty = false;
			}	
			if (isEmpty) {
				JOptionPane.showMessageDialog(null, "请注意:当前子任务没有从属的子任务!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取连接失败");
		}finally {
			try {
				dbutil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
