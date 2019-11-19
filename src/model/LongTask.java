package model;

import java.util.Date;

public class LongTask extends Task{
	private Date dueDate;
	
	public LongTask() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public LongTask(int id, int listId, String name, String desc, int isCompleted, int taskType,Date dueDate) {
		super(id, listId, name, desc, isCompleted, taskType);
		this.dueDate = dueDate;
	}
	
	public LongTask(int listId, String name, String desc, int isCompleted, int taskType,Date dueDate) {
		super(listId, name, desc, isCompleted, taskType);
		this.dueDate = dueDate;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	
}
