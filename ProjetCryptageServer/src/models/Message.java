package models;

import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = -5130689345004322457L;
	
	private String publicKey;
	private String message;
	private long packets;
	private String senderName;
	
	public Message(){
		publicKey = new String();
		message = new String();
		senderName = new String();
		packets = 0l;
	}
	
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String data) {
		this.message = data;
	}
	public long getPackets() {
		return packets;
	}
	public void setPackets(long packets) {
		this.packets = packets;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
}
