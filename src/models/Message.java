package models;

import java.io.Serializable;

public class Message implements Serializable {
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	private static final long serialVersionUID = -5130689345004322457L;
	
	private byte[] publicKey;
	private String message;
	private long packets;
	private String senderName;
	private int code;
	
	public Message(){
		publicKey = new byte[]{};
		message = new String();
		senderName = new String();
		packets = 0l;
	}
	
	public byte[] getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(byte[] publicKey) {
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
