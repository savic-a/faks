package iss.spring.web.services.interfaces;

import java.util.Collection;

import iss.spring.web.entities.inheritance.Instrument;

public interface IInstrumentService {
	
	public Collection<Instrument> getAll();
	
	public void createAll();

	public void deleteAll();

}
