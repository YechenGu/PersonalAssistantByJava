package model;

import java.util.Date;

public class SubTask {
	private int id;
	private String name;
	private Date dueDate;
	private int father;
	private int fatherTask;
	
	
	
	public SubTask() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	
	public SubTask(String name, Date dueDate, int father, int fatherTask) {
		super();
		this.name = name;
		this.dueDate = dueDate;
		this.father = father;
		this.fatherTask = fatherTask;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public int getFather() {
		return father;
	}
	public void setFather(int father) {
		this.father = father;
	}
	public int getFatherTask() {
		return fatherTask;
	}
	public void setFatherTask(int fatherTask) {
		this.fatherTask = fatherTask;
	}
}
