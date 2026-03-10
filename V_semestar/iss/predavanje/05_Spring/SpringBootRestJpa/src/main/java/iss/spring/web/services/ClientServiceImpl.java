package iss.spring.web.services;

import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import iss.spring.web.entities.lob.Client;
import iss.spring.web.entities.lob.ClientInfo;
import iss.spring.web.repositories.ClientRepository;
import iss.spring.web.services.interfaces.IClientService;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	ClientRepository allClients;
	
	ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", LocaleContextHolder.getLocale());

	
	@Override
	public Collection<Client> getAll() {
		return allClients.findAll();
	}

	@Override
	public Client findClient(Long clientId) {
		Optional<Client> found = allClients.findById(clientId);
		if (found.isEmpty()) {
			String value = bundle.getString("client.notFound");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
		}
		return found.get();
	}
	
	@Override
	public Client insert(Client client) {
		client.setInfo(new ClientInfo("adresa", "slika.gif"));
		Client ret = allClients.save(client);
		return ret;
	}

	@Override
	public void deleteAll() {
		allClients.deleteAll();
	}

}
