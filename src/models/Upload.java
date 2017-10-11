package models;

import java.io.Serializable;

public class Upload extends Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2783129448548822419L;
	
	private String name;
	private byte[] data;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return this.data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public Upload(String name,  byte[] data) {
		super();
		this.name = name;
		this.data = data;
	}
	
	public Upload(){
		
	}
}
