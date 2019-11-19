package model;

public class TaskList {
	private int id;
	private String name;
	private String type;
	
    // alt + shift + s 弹出菜单	
	// ctrl + shift + o 导入类
	
	public TaskList() {
		super();
	}

	public TaskList(int id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public TaskList(String name, String type) {
		super();
		this.name = name;
		this.type = type;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
}
