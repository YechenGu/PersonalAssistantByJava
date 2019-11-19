package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
//import javax.swing.text.html.AccessibleHTML.TableElementInfo.TableAccessibleContext;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.CycleTaskDao;
import dao.LongTaskDao;
import dao.TaskDao;
import dao.TaskListDao;
import dao.TempTaskDao;
import model.CycleTask;
import model.LongTask;
import model.Task;
import model.TaskList;
import model.TempTask;
import util.Dbutil;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

/**
 * 主窗口类
 * @author Administrator
 *
 */
public class Mainfrm extends JFrame {

	private JPanel contentPane;
	private JDesktopPane table = null;
	private JTable listTable;
	private JTable taskTable;
	private Dbutil dbutil = new Dbutil();	//数据库工具类
	private TaskListDao taskListDao = new TaskListDao();	//数据库操作执行类 
	private TaskDao taskDao = new TaskDao();
	private TempTaskDao tempTaskDao = new TempTaskDao();
	private CycleTaskDao cycleTaskDao = new CycleTaskDao();
	private LongTaskDao longTaskDao = new LongTaskDao();
	private JTextField listTextField;
	private JTextField taskTextField;
	private JButton addListBtn;
	private JButton changeListBtn;
	private JButton taskBtn;
	private JButton addTempBtn;
	private JButton addCycleBtn;
	private JButton addLongBtn;
	private JButton changeTaskBtn;
	private JButton copyTask;
	private JButton transTask;
	private JButton listByIdBtn;
	private JButton listByNameBtn;
	private JButton listByTypeBtn;
	private JButton taskByIdBtn;
	private JButton taskByNameBtn;
	private JButton taskByTypeBtn;
	private int selectedLeftRow;
	private Connection outConnection;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mainfrm frame = new Mainfrm();
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
	public Mainfrm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Mainfrm.class.getResource("/images/checklist_16px_1142185_easyicon.net.png")));
		setResizable(false);
		setTitle("\u4E2A\u4EBA\u52A9\u7406\u4E3B\u754C\u9762");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 50, 1000, 650);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("\u9000\u51FA");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("\u9000\u51FA\u7CFB\u7EDF");
		menuItem.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/login_exit_16px_516164_easyicon.net.png")));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int res = JOptionPane.showConfirmDialog(null, "是否确定退出？");
				if (res == 0) {
					dispose();
				}
			}
		});
		menu.add(menuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		table = new JDesktopPane();
		table.setBackground(Color.WHITE);
		contentPane.add(table, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 64, 327, 385);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(483, 64, 408, 385);
		
		JLabel label = new JLabel("\u5217\u8868\u540D\u79F0\uFF1A");
		label.setBounds(46, 30, 71, 25);
		
		listTextField = new JTextField();
		listTextField.setBounds(114, 32, 129, 21);
		listTextField.setColumns(10);
		
		JButton listBtn = new JButton("\u67E5\u8BE2");
		listBtn.setBounds(285, 30, 87, 25);
		listBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/query_search_16px_1135839_easyicon.net.png")));
		listBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listSearchActionPerformed(arg0);
				listToggleOff();
			}
		});
		
		taskTextField = new JTextField();
		taskTextField.setBounds(571, 32, 198, 21);
		taskTextField.setColumns(10);
		
		taskTable = new JTable();
		taskTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u7F16\u53F7", "\u4EFB\u52A1\u540D\u79F0", "\u4EFB\u52A1\u63CF\u8FF0", "\u4EFB\u52A1\u7C7B\u578B", "\u662F\u5426\u5B8C\u6210"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		taskTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				taskMousePressed(arg0);
			}
			
		});
		scrollPane_1.setViewportView(taskTable);
		
		listTable = new JTable();
		listTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u7F16\u53F7", "\u5217\u8868\u540D\u79F0", "\u5217\u8868\u7C7B\u578B"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		listTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				listMousePressed(arg0);
			}
			
		});
		scrollPane.setViewportView(listTable);
		Mainfrm mainfrm = this;		//获得一个主窗口的引用对象
		
		
		addListBtn = new JButton("\u6DFB\u52A0\u4EFB\u52A1\u5217\u8868");
		addListBtn.setBounds(237, 480, 137, 25);
		addListBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/add_16px_1232917_easyicon.net.png")));
		addListBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TaskListAddInterFrm taskListAddInterFrm = new TaskListAddInterFrm();
				taskListAddInterFrm.setVisible(true);
				mainfrm.fillListTable(new TaskList());
				listToggleOff();
			}
		});
		
		changeListBtn = new JButton("\u4FEE\u6539\u4EFB\u52A1\u5217\u8868");
		changeListBtn.setBounds(237, 540, 137, 25);
		changeListBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/Modify_16px_1060983_easyicon.net.png")));
		changeListBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = listTable.getSelectedRow();
				String id = (String)listTable.getValueAt(row,0);
				String name = (String)listTable.getValueAt(row,1);
				String type = (String)listTable.getValueAt(row,2);
				TaskListManageInterFrm taskListManageInterFrm = new TaskListManageInterFrm(id,name,type);				
				taskListManageInterFrm.setVisible(true);
				mainfrm.fillListTable(new TaskList());
				listToggleOff();
			}
		});
		changeListBtn.setEnabled(false);
		
		taskBtn = new JButton("\u67E5\u8BE2");
		taskBtn.setEnabled(false);
		taskBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/query_search_16px_1135839_easyicon.net.png")));
		taskBtn.setBounds(808, 29, 83, 27);
		taskBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				taskSearchActionPerformed(arg0);
				taskToggleOff();
			}
		});
		table.add(taskBtn);
		
		addTempBtn = new JButton("\u6DFB\u52A0\u77ED\u671F\u4EFB\u52A1");
		addTempBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/add_16px_1232917_easyicon.net.png")));
		addTempBtn.setEnabled(false);
		addTempBtn.setBounds(617, 468, 145, 23);
		addTempBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TempTaskAddInterFrm tempTaskAddInterFrm = new TempTaskAddInterFrm(mainfrm, selectedLeftRow);
				tempTaskAddInterFrm.setVisible(true);
				Task task = new Task();
				task.setListId(selectedLeftRow);
				mainfrm.fillTaskTable(task);
				taskToggleOff();
			}
		});
		table.add(addTempBtn);
		
		addCycleBtn = new JButton("\u6DFB\u52A0\u5468\u671F\u4EFB\u52A1");
		addCycleBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/add_16px_1232917_easyicon.net.png")));
		addCycleBtn.setBounds(618, 514, 144, 23);
		addCycleBtn.setEnabled(false);
		addCycleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CycleTaskAddInterFrm cycleTaskAddInterFrm = new CycleTaskAddInterFrm(mainfrm,selectedLeftRow);
				cycleTaskAddInterFrm.setVisible(true);
				Task task = new Task();
				task.setListId(selectedLeftRow);
				mainfrm.fillTaskTable(task);
				taskToggleOff();
			}
		});
		
		addLongBtn = new JButton("\u6DFB\u52A0\u957F\u671F\u4EFB\u52A1");
		addLongBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/add_16px_1232917_easyicon.net.png")));
		addLongBtn.setEnabled(false);
		addLongBtn.setBounds(618, 558, 144, 23);
		addLongBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LongTaskAddInterFrm longTaskAddInterFrm = new LongTaskAddInterFrm(mainfrm,selectedLeftRow);
				longTaskAddInterFrm.setVisible(true);
				Task task = new Task();
				task.setListId(selectedLeftRow);
				mainfrm.fillTaskTable(task);
				taskToggleOff();
			}
		});
		table.add(addLongBtn);
		
		/**
		 * 修改任务的按钮
		 */
		changeTaskBtn = new JButton("\u4FEE\u6539\u4EFB\u52A1");
		changeTaskBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/Modify_16px_1060983_easyicon.net.png")));
		changeTaskBtn.setBounds(780, 468, 111, 23);
		changeTaskBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = taskTable.getSelectedRow();
				ResultSet resultSet = null;
				String tasktype = (String)taskTable.getValueAt(row,3);
				switch (tasktype) {
				case "短期":
					String id_1 = (String)taskTable.getValueAt(row,0);
					int id_11 = Integer.parseInt(id_1);
					TempTask tempTask = new TempTask();
					tempTask.setId(id_11);
					resultSet = getTask(tempTask);					
					try {
						while (resultSet.next()) {
						String id1 = resultSet.getString("id");
						String name1 = resultSet.getString("name");
						String desc1 = resultSet.getString("desc");
						Date date1 = resultSet.getDate("dueDate");
						String year1 = String.valueOf(date1.getYear()+1900);
						String month1 = String.valueOf(date1.getMonth()+1);
						String day1 = String.valueOf(date1.getDate());						
						TempTaskManageDialog tempTaskManageDialog = new TempTaskManageDialog(id1, name1, desc1, year1, month1, day1);
						tempTaskManageDialog.setVisible(true);						
						}
						Task task = new Task();
						task.setListId(selectedLeftRow);
						mainfrm.fillTaskTable(task);
						taskToggleOff();
						dbutil.closeCon(outConnection);	
					} catch (Exception e1) {
						System.out.println("数据解析错误");
						e1.printStackTrace();
					}	
					break;
				case "周期":
					String id_2 = (String)taskTable.getValueAt(row,0);
					int id_22 = Integer.parseInt(id_2);
					CycleTask cycleTask = new CycleTask();
					cycleTask.setId(id_22);
					resultSet = getTask(cycleTask);	
					try {
						while (resultSet.next()) {							
						String id1 = resultSet.getString("id");
						String name1 = resultSet.getString("name");
						String desc1 = resultSet.getString("desc");
						String cycle = resultSet.getString("cycle");
						String times = resultSet.getString("times");
						Date date1 = resultSet.getDate("excDate");
						String year1 = String.valueOf(date1.getYear()+1900);
						String month1 = String.valueOf(date1.getMonth()+1);
						String day1 = String.valueOf(date1.getDate());						
						CycleTaskManageDialog cycleTaskManageDialog = new CycleTaskManageDialog(id1, name1, desc1, year1, month1, day1,times,cycle);
						cycleTaskManageDialog.setVisible(true);						
						}
						Task task = new Task();
						task.setListId(selectedLeftRow);
						mainfrm.fillTaskTable(task);
						taskToggleOff();
						dbutil.closeCon(outConnection);	
					} catch (Exception e1) {
						System.out.println("数据解析错误");
						e1.printStackTrace();
					}
					break;
				case "长期":
					String id_3 = (String)taskTable.getValueAt(row,0);
					int id_33 = Integer.parseInt(id_3);
					LongTask longTask = new LongTask();
					longTask.setId(id_33);
					resultSet = getTask(longTask);	
					try {
						while (resultSet.next()) {
						String id1 = resultSet.getString("id");
						String name1 = resultSet.getString("name");
						String desc1 = resultSet.getString("desc");
						Date date1 = resultSet.getDate("dueDate");
						String year1 = String.valueOf(date1.getYear()+1900);
						String month1 = String.valueOf(date1.getMonth()+1);
						String day1 = String.valueOf(date1.getDate());						
						LongTaskManageDialog longTaskManageDialog = new LongTaskManageDialog(id1, name1, desc1, year1, month1, day1);
						longTaskManageDialog.setVisible(true);	
						break;
						}
						Task task = new Task();
						task.setListId(selectedLeftRow);
						mainfrm.fillTaskTable(task);
						taskToggleOff();
						dbutil.closeCon(outConnection);	
					} catch (Exception e1) {
						System.out.println("数据解析错误");
						e1.printStackTrace();
					}
					break;
				default:
					System.out.println("任务类型获取失败");
					break;
				}
			}
		});
		changeTaskBtn.setEnabled(false);
		
		/**
		 * 复制任务
		 */
		copyTask = new JButton("\u590D\u5236\u4EFB\u52A1");
		copyTask.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/copy_13.333333333333px_1218046_easyicon.net.png")));
		copyTask.setBounds(780, 514, 111, 23);
		copyTask.setEnabled(false);
		copyTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Task sourceTask = getCertainTaskFromSelectedRow(taskTable.getSelectedRow());
				Task sourceCertainTask = getCertainTaskFromSelectedRow(taskTable.getSelectedRow());
				CopyTransDialog copyTransDialog = new CopyTransDialog(0,selectedLeftRow,sourceTask,sourceCertainTask);
				copyTransDialog.setVisible(true);
				taskToggleOff();
			}
		});
		
		transTask = new JButton("\u8F6C\u79FB\u4EFB\u52A1");
		transTask.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/transfer_14.279569892473px_1209002_easyicon.net.png")));
		transTask.setEnabled(false);
		transTask.setBounds(780, 558, 111, 23);
		transTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Task sourceTask = getCertainTaskFromSelectedRow(taskTable.getSelectedRow());
				Task sourceCertainTask = getCertainTaskFromSelectedRow(taskTable.getSelectedRow());
				CopyTransDialog copyTransDialog = new CopyTransDialog(1,selectedLeftRow,sourceTask,sourceCertainTask);				
				copyTransDialog.setVisible(true);
				Task task = new Task();
				task.setListId(selectedLeftRow);
				mainfrm.fillTaskTable(task);
				taskToggleOff();
			}
		});
		table.add(transTask);
		
		listByIdBtn = new JButton("\u6309\u7F16\u53F7\u6392\u5E8F");
		listByIdBtn.setBounds(46, 467, 128, 25);
		listByIdBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/Sorting_17.399239543726px_1183582_easyicon.net.png")));
		listByIdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainfrm.fillListTableByOrder(new TaskList(),1);
			}
		});
		
		listByNameBtn = new JButton("\u6309\u540D\u79F0\u6392\u5E8F");
		listByNameBtn.setBounds(46, 513, 129, 25);
		listByNameBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/Sorting_17.399239543726px_1183582_easyicon.net.png")));
		listByNameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainfrm.fillListTableByOrder(new TaskList(),2);
			}
		});
		
		listByTypeBtn = new JButton("\u6309\u7C7B\u578B\u6392\u5E8F");
		listByTypeBtn.setBounds(46, 557, 129, 25);
		listByTypeBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/Sorting_17.399239543726px_1183582_easyicon.net.png")));
		listByTypeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainfrm.fillListTableByOrder(new TaskList(),3);
			}
		});
		
		taskByIdBtn = new JButton("\u6309\u7F16\u53F7\u6392\u5E8F");
		taskByIdBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/Sorting_17.399239543726px_1183582_easyicon.net.png")));
		taskByIdBtn.setEnabled(false);
		taskByIdBtn.setBounds(479, 468, 128, 23);
		taskByIdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainfrm.fillTaskTableByOrder(new Task(),1);
			}
		});
		table.add(taskByIdBtn);
		
		taskByNameBtn = new JButton("\u6309\u540D\u79F0\u6392\u5E8F");
		taskByNameBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/Sorting_17.399239543726px_1183582_easyicon.net.png")));
		taskByNameBtn.setBounds(479, 514, 128, 23);
		taskByNameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainfrm.fillTaskTableByOrder(new Task(),2);
			}
		});
		taskByNameBtn.setEnabled(false);
		
		taskByTypeBtn = new JButton("\u6309\u7C7B\u578B\u6392\u5E8F");
		taskByTypeBtn.setIcon(new ImageIcon(Mainfrm.class.getResource("/images/Sorting_17.399239543726px_1183582_easyicon.net.png")));
		taskByTypeBtn.setEnabled(false);
		taskByTypeBtn.setBounds(479, 558, 128, 23);
		taskByTypeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainfrm.fillTaskTableByOrder(new Task(),3);
			}
		});
		table.add(taskByTypeBtn);
		
		JLabel label_1 = new JLabel("\u4EFB\u52A1\u540D\u79F0\uFF1A");
		label_1.setBounds(479, 25, 68, 34);
		table.setLayout(null);
		table.add(label);
		table.add(listTextField);
		table.add(listBtn);
		table.add(listByTypeBtn);
		table.add(listByNameBtn);
		table.add(changeListBtn);
		table.add(addListBtn);
		table.add(label_1);
		table.add(listByIdBtn);
		table.add(changeTaskBtn);
		table.add(taskTextField);
		table.add(taskByNameBtn);
		table.add(copyTask);
		table.add(addCycleBtn);
		table.add(scrollPane);
		table.add(scrollPane_1);
		
		this.fillListTable(new TaskList());
	}
	
	
	/**
	 * 点击任务事件
	 * @param arg0
	 */
	private void taskMousePressed(MouseEvent arg0) {
		changeTaskBtn.setEnabled(true);
		copyTask.setEnabled(true);
		transTask.setEnabled(true);
		int row = taskTable.getSelectedRow();
	}

	/**
	 * 选中任务列表某行
	 * @param arg0
	 */
	private void listMousePressed(MouseEvent arg0) {
		changeListBtn.setEnabled(true);
		addTempBtn.setEnabled(true);
		addCycleBtn.setEnabled(true);
		addLongBtn.setEnabled(true);
		changeTaskBtn.setEnabled(false);
		copyTask.setEnabled(false);
		transTask.setEnabled(false);
		taskByIdBtn.setEnabled(true);
		taskByNameBtn.setEnabled(true);
		taskByTypeBtn.setEnabled(true);
		taskBtn.setEnabled(true);
		int row = listTable.getSelectedRow();				//获得选中的id
		String id1 = (String)listTable.getValueAt(row,0);
		int id = Integer.valueOf(id1);
		selectedLeftRow = id;
		Task task = new Task();
		task.setListId(id);
		this.fillTaskTable(task);
	}
	
	
	/**
	 * 搜索列表
	 * @param arg0
	 */
	private void listSearchActionPerformed(ActionEvent arg0) {
		String stxt = this.listTextField.getText();
		TaskList taskList = new TaskList();
		taskList.setName(stxt);
		this.fillListTable(taskList);	
	}
	
	private void taskSearchActionPerformed(ActionEvent arg0) {
		String stxt = this.taskTextField.getText();
		Task task = new Task();
		task.setName(stxt);
		task.setListId(selectedLeftRow);
		this.fillTaskTable(task);
	}
	
	
	/**
	 * 填充任务清单表
	 * @param taskList
	 */
	private void fillListTable(TaskList taskList){				//填充JTable的方法
		DefaultTableModel dtm = (DefaultTableModel) listTable.getModel();
		dtm.setRowCount(0);
		Connection con = null;
		try {
			con = dbutil.getCon();
			ResultSet resultSet = taskListDao.list(con, taskList);
			while (resultSet.next()) {
				//如果还有下一行，使用Vector容器
				Vector vector = new Vector();
				vector.add(resultSet.getString("id"));
				vector.add(resultSet.getString("name"));
				vector.add(resultSet.getString("type"));
				dtm.addRow(vector);
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
	
	
	/**
	 * 按顺序填充任务清单表
	 * @param taskList
	 */
	private void fillListTableByOrder(TaskList taskList,int order){				//填充JTable的方法
		DefaultTableModel dtm = (DefaultTableModel) listTable.getModel();
		dtm.setRowCount(0);
		Connection con = null;
		try {
			con = dbutil.getCon();
			ResultSet resultSet = taskListDao.listByOrder(con, taskList, order);
			while (resultSet.next()) {
				//如果还有下一行，使用Vector容器
				Vector vector = new Vector();
				vector.add(resultSet.getString("id"));
				vector.add(resultSet.getString("name"));
				vector.add(resultSet.getString("type"));
				dtm.addRow(vector);
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
	/**
	 * 填充任务表
	 * @param task
	 */
	private void fillTaskTable(Task task){				//填充JTable的方法
		DefaultTableModel dtm = (DefaultTableModel) taskTable.getModel();
		dtm.setRowCount(0);
		Connection con = null;
		try {
			con = dbutil.getCon();
			ResultSet resultSet = taskDao.list(con, task);
			while (resultSet.next()) {
				//如果还有下一行，使用Vector容器
				Vector vector = new Vector();
				vector.add(resultSet.getString("id"));
				vector.add(resultSet.getString("name"));
				vector.add(resultSet.getString("desc"));
				
				String type = resultSet.getString("taskType");
				switch (type) {
				case "1":
					vector.add("短期");
					break;
				case "2":
					vector.add("周期");
					break;
				case "3":
					vector.add("长期");
					break;
				default:
					break;
				}
				
				int status = resultSet.getInt("isCompleted");
				if (status == 1) {
					vector.add("是");
				} else {
					vector.add("否");
				}
				
				dtm.addRow(vector);
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
	
	/**
	 * 按顺序填充任务表
	 * @param task
	 */
	private void fillTaskTableByOrder(Task task,int order){				//填充JTable的方法
		DefaultTableModel dtm = (DefaultTableModel) taskTable.getModel();
		dtm.setRowCount(0);
		Connection con = null;
		try {
			con = dbutil.getCon();
			task.setListId(selectedLeftRow);
			ResultSet resultSet = taskDao.listByOrder(con, task, order);
			while (resultSet.next()) {
				//如果还有下一行，使用Vector容器
				Vector vector = new Vector();
				vector.add(resultSet.getString("id"));
				vector.add(resultSet.getString("name"));
				vector.add(resultSet.getString("desc"));
				
				String type = resultSet.getString("taskType");
				switch (type) {
				case "1":
					vector.add("短期");
					break;
				case "2":
					vector.add("周期");
					break;
				case "3":
					vector.add("长期");
					break;
				default:
					break;
				}
				
				int status = resultSet.getInt("isCompleted");
				if (status == 1) {
					vector.add("是");
				} else {
					vector.add("否");
				}
				
				dtm.addRow(vector);
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
	/**
	 * 获得任务的ResultSet对象
	 * @param task
	 * @return
	 */
	private ResultSet getTask(Task task) {
		outConnection = null;
		ResultSet resultSet = null;
		try {
			outConnection = dbutil.getCon();
			if (task instanceof TempTask) {
				TempTask tempTask = (TempTask) task;
				resultSet = tempTaskDao.list(outConnection, tempTask);
			}
			if (task instanceof CycleTask) {
				CycleTask cycleTask = (CycleTask) task;
				resultSet = cycleTaskDao.list(outConnection, cycleTask);
			}
			if (task instanceof LongTask) {
				LongTask longTask = (LongTask) task;
				resultSet = longTaskDao.list(outConnection, longTask);			
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取连接失败");
		}finally {
			return resultSet;
		}
	}
	
	private Task getCertainTaskFromSelectedRow(int row) {
		ResultSet resultSet = null;
		Task task = null;
		String tasktype = (String)taskTable.getValueAt(row,3);
		switch (tasktype) {
		case "短期":
			String id_1 = (String)taskTable.getValueAt(row,0);
			int id_11 = Integer.parseInt(id_1);
			TempTask tempTask = new TempTask();
			tempTask.setId(id_11);
			resultSet = getTask(tempTask);					
			try {
				while (resultSet.next()) {
					tempTask.setName(resultSet.getString("name"));
					tempTask.setListId(Integer.parseInt(resultSet.getString("listId")));
					tempTask.setDesc(resultSet.getString("desc"));
					tempTask.setCompleted(resultSet.getInt("isCompleted"));
					tempTask.setTaskType(1);
					tempTask.setDueDate(resultSet.getDate("dueDate"));			
					task = tempTask;				
				}
				dbutil.closeCon(outConnection);	
			} catch (Exception e1) {
				System.out.println("数据解析错误");
				e1.printStackTrace();
			}finally {
				return task;
			}				
		case "周期":
			String id_2 = (String)taskTable.getValueAt(row,0);
			int id_22 = Integer.parseInt(id_2);
			CycleTask cycleTask = new CycleTask();
			cycleTask.setId(id_22);
			resultSet = getTask(cycleTask);	
			try {
				while (resultSet.next()) {							
					cycleTask.setName(resultSet.getString("name"));
					cycleTask.setListId(Integer.parseInt(resultSet.getString("listId")));
					cycleTask.setDesc(resultSet.getString("desc"));
					cycleTask.setCompleted(resultSet.getInt("isCompleted"));
					cycleTask.setTaskType(2);
					cycleTask.setExcDate(resultSet.getDate("excDate"));	
					cycleTask.setTimes(resultSet.getInt("times"));
					cycleTask.setCycle(resultSet.getInt("cycle"));
					task = cycleTask;										
				}
				dbutil.closeCon(outConnection);	
			} catch (Exception e1) {
				System.out.println("数据解析错误");
				e1.printStackTrace();
			}finally {
				return task;
			}
		case "长期":
			String id_3 = (String)taskTable.getValueAt(row,0);
			int id_33 = Integer.parseInt(id_3);
			LongTask longTask = new LongTask();
			longTask.setId(id_33);
			resultSet = getTask(longTask);	
			try {
				while (resultSet.next()) {
					longTask.setName(resultSet.getString("name"));
					longTask.setListId(Integer.parseInt(resultSet.getString("listId")));
					longTask.setDesc(resultSet.getString("desc"));
					longTask.setCompleted(resultSet.getInt("isCompleted"));
					longTask.setTaskType(3);
					longTask.setDueDate(resultSet.getDate("dueDate"));			
					task = longTask;						
				}
				dbutil.closeCon(outConnection);	
			} catch (Exception e1) {
				System.out.println("数据解析错误");
				e1.printStackTrace();
			}finally {
				return task;
			}
		default:
			System.out.println("任务类型获取失败");
			return task;
		}
	}
	
	private Task getTaskFromSelectedRow(int row) {
			ResultSet resultSet = null;
			Task task = null;
			String id_1 = (String)taskTable.getValueAt(row,0);
			int id_11 = Integer.parseInt(id_1);
			
			String tasktype = (String)taskTable.getValueAt(row,3);
			int type;
			switch (tasktype) {
			case "短期":
				type = 1;
				break;
			case "周期":
				type = 2;
				break;
			case "长期":
				type = 3;
				break;
			default:
				type = 0;
				break;
			}
			
			task = new Task();
			task.setId(id_11);
			resultSet = getTask(task);					
			try {
				while (resultSet.next()) {
					task.setName(resultSet.getString("name"));
					task.setListId(Integer.parseInt(resultSet.getString("listId")));
					task.setDesc(resultSet.getString("desc"));
					task.setCompleted(resultSet.getInt("isCompleted"));	
					task.setTaskType(type);				
				}
				dbutil.closeCon(outConnection);	
			} catch (Exception e1) {
				System.out.println("数据解析错误");
				e1.printStackTrace();
			}finally {
				return task;
			}				

	}
	
	private void listToggleOff() {
		changeListBtn.setEnabled(false);
		addTempBtn.setEnabled(false);
		addCycleBtn.setEnabled(false);
		addLongBtn.setEnabled(false);
		changeTaskBtn.setEnabled(false);
		copyTask.setEnabled(false);
		transTask.setEnabled(false);
		taskByIdBtn.setEnabled(false);
		taskByNameBtn.setEnabled(false);
		taskByTypeBtn.setEnabled(false);
		taskBtn.setEnabled(false);
		Task task = new Task();
		task.setName("此0任0务0名0称0是0保0留0字");
		fillTaskTable(task);
	}
	
	private void taskToggleOff() {
		changeTaskBtn.setEnabled(false);
		copyTask.setEnabled(false);
		transTask.setEnabled(false);
		
	}
}
