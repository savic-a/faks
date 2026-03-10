package iss.spring.web.services.interfaces.relations;

import java.util.Collection;

import iss.spring.web.entities.relations.manytoone.Worker;


public interface IWorkerWorkingPlaceService {
	
	public Collection<Worker> getAll();
	
	public void createWorkerAndWorkingPlace(String workerName, String wpName);
	
	public Collection<Worker> getAllByWorkingPlaceName(String wpName);

	public void deleteAll();

}
