package iss.spring.web.services.interfaces.relations;

import java.util.Collection;

import iss.spring.web.entities.relations.onetoone.Worker;

public interface IWorkerPolicyService {
	
	public Collection<Worker> getAll();
	
	public void createWorker(String workerName, String policyName);

	public void deleteAll();

}
