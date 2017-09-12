package dao;

import java.sql.ResultSet;
import interfaces.Patterns.IDelegate;
import models.Client;

public class ClientService {
	
	private final GenericDAO<Client> clientDAO;
	private final IDelegate<Client, ResultSet> clientReader;
	private final String getClientByLoginPassQuery = "SELECT u.* FROM cryptobase.user u WHERE u.login=? AND u.password=?";
	private final String getClietByLoginQuery = "SELECT u.* FROM cryptobase.user u WHERE u.login=?";
	private final String insertClientQuery = "INSERT INTO cryptobase.user (login, password, name) VALUES (?, ?, ?)";
	
	public ClientService(){
		this.clientDAO = new GenericDAO<Client>();
		this.clientReader = (ResultSet rs) -> readClient(rs);
	}
	
	private Client readClient(ResultSet rs) {
		Client cl = new Client();
		try{
			cl.setName(rs.getString("Name"));
			cl.setId(rs.getInt("Id"));
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return cl;
	}
	
	public Client getClient(String login, String pass){
		return clientDAO.getObject(getClientByLoginPassQuery, clientReader, login, pass);
	}	
	
	public int insertClient(String login, String pass, String name) {
		Client cl = clientDAO.getObject(getClietByLoginQuery, clientReader, login);
		return (cl != null) ? -1 : clientDAO.insertObject(insertClientQuery, login, pass, name);
	}
}
