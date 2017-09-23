package models;

import java.io.Serializable;

public class Upload extends Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2783129448548822419L;
	
	private String name;
	private String extension;
	private byte[] data;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExtension() {
		return this.extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public byte[] getData() {
		return this.data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public Upload(String name, String extension, byte[] data) {
		super();
		this.name = name;
		this.extension = extension;
		this.data = data;
	}
	
	public Upload(){
		
	}
}
