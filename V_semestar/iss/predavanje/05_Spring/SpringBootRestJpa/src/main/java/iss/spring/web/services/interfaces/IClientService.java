package iss.spring.web.services.interfaces;

import java.util.Collection;

import iss.spring.web.entities.lob.Client;

public interface IClientService {
	
	public Collection<Client> getAll();
	
	public Client findClient(Long clientId);
	
	public Client insert(Client client);

	public void deleteAll();

}
