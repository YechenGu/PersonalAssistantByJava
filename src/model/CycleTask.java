package model;

import java.util.Date;

public class CycleTask extends Task{
	private Date excDate;
	private int times;
	private int cycle;
	
	public CycleTask() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public CycleTask(int listId, String name, String desc, int isCompleted, int taskType,Date excDate,int times,int cycle) {
		super(listId, name, desc, isCompleted, taskType);
		this.excDate = excDate;
		this.times = times;
		this.cycle = cycle;
		// TODO 自动生成的构造函数存根
	}
	public CycleTask(int id,int listId, String name, String desc, int isCompleted, int taskType,Date excDate,int times,int cycle) {
		super(id,listId, name, desc, isCompleted, taskType);
		this.excDate = excDate;
		this.times = times;
		this.cycle = cycle;
	}
	

	public Date getExcDate() {
		return excDate;
	}
	public void setExcDate(Date excDate) {
		this.excDate = excDate;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	
	
}
