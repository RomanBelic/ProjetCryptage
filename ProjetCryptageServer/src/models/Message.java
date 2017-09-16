package models;

import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = -5130689345004322457L;
	
	private String publicKey;
	private byte[] data;
	private long packets;
	private String senderName;
	
	public Message(){
		publicKey = "";
		data = new byte[0];
		packets = 0l;
		senderName = "";
	}
	
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
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
