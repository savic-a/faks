package iss.spring.web.services;

import java.util.Collection;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import iss.spring.web.entities.inheritance.Guitar;
import iss.spring.web.entities.inheritance.Instrument;
import iss.spring.web.entities.inheritance.Piano;
import iss.spring.web.repositories.InstrumentRepository;
import iss.spring.web.services.interfaces.IInstrumentService;

@Service
public class InstrumentServiceImpl implements IInstrumentService {

	@Autowired
	InstrumentRepository allInstruments;
	
	ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", LocaleContextHolder.getLocale());

	
	@Override
	public Collection<Instrument> getAll() {
		return allInstruments.findAll();
	}

	@Override
	public void createAll() {
		Instrument klavir = new Piano("Klavir", 100);
		Instrument gitara = new Guitar("Gitara", 6);
		
		allInstruments.save(klavir);
		allInstruments.save(gitara);
	}

	@Override
	public void deleteAll() {
		allInstruments.deleteAll();
	}

}
