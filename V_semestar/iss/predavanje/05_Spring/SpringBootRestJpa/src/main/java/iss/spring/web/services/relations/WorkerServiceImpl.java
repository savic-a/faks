package iss.spring.web.services.relations;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.spring.web.entities.relations.onetoone.InsurancePolicy;
import iss.spring.web.entities.relations.onetoone.Worker;
import iss.spring.web.repositories.relations.WorkerRepository;
import iss.spring.web.services.interfaces.relations.IWorkerPolicyService;

@Service
public class WorkerServiceImpl implements IWorkerPolicyService {

	@Autowired
	WorkerRepository allWorkers;
	
	@Override
	public Collection<Worker> getAll() {
		return allWorkers.findAll();
	}

	@Override
	public void createWorker(String workerName, String policyName) {
		Worker w = new Worker(workerName);
	    InsurancePolicy pol = new InsurancePolicy(policyName);
	    w.setPolicy(pol);
	    allWorkers.save(w);
	}

	@Override
	public void deleteAll() {
		allWorkers.deleteAll();
	}

}
