package models;

public class Client {

	private int id;
	private String name;
	
	public Client(){
		this.id = 0;
		this.name = new String();
	}

	public Client(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
	public boolean isEmpty(){
		return id <= 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Client && ((Client)obj).id == id;
	}
	
	@Override
	public int hashCode() {
		return new String(String.valueOf(id) + name).hashCode();
	}
}
