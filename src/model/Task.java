package model;

public class Task {
	private int id;
	private int listId;
	private String name;
	private String desc;
	private int isCompleted;
	private int taskType;
	
	public Task() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public Task(int id, int listId, String name, String desc, int isCompleted, int taskType) {
		super();
		this.id = id;
		this.listId = listId;
		this.name = name;
		this.desc = desc;
		this.isCompleted = isCompleted;	//0没完成，1完成
		this.taskType = taskType;
	}

	
	public Task(int listId, String name, String desc, int isCompleted, int taskType) {
		super();
		this.listId = listId;
		this.name = name;
		this.desc = desc;
		this.isCompleted = isCompleted;
		this.taskType = taskType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getCompleted() {
		return isCompleted;
	}

	public void setCompleted(int isCompleted) {
		this.isCompleted = isCompleted;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	
	
}




