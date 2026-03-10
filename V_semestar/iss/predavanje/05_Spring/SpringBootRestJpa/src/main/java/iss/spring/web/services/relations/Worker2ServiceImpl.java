package iss.spring.web.services.relations;

import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import iss.spring.web.entities.relations.manytoone.Worker;
import iss.spring.web.entities.relations.manytoone.WorkingPlace;
import iss.spring.web.repositories.relations.Worker2Repository;
import iss.spring.web.repositories.relations.WorkingPlaceRepository;
import iss.spring.web.services.interfaces.relations.IWorkerWorkingPlaceService;

@Service
public class Worker2ServiceImpl implements IWorkerWorkingPlaceService {

	@Autowired
	Worker2Repository allWorkers;
	
	@Autowired
	WorkingPlaceRepository allWorkingPlaces;

	ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", LocaleContextHolder.getLocale());
	
	@Override
	public Collection<Worker> getAll() {
		return allWorkers.findAll();
	}

	@Override
	public void createWorkerAndWorkingPlace(String workerName, String wpName) {
		Worker w = new Worker(workerName);
		Optional<WorkingPlace> places = allWorkingPlaces.findByName(wpName);
		WorkingPlace place = null;
		if (places.isEmpty()) {
			// WorkingPlace ne postoji, kreiramo ga
			place = new WorkingPlace(wpName);
			allWorkingPlaces.save(place);
		} else {
			place = places.get();
		}

		w.setWorkingPlace(place);
		allWorkers.save(w);
	}

	@Override
	public Collection<Worker> getAllByWorkingPlaceName(String wpName) {
		Optional<WorkingPlace> places = allWorkingPlaces.findByName(wpName);
		if (!places.isEmpty()) {
			WorkingPlace place = places.get();
			return place.getWorkers();
		}
		String value = bundle.getString("WorkingPlace.notFound");
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
	}

	
	@Override
	public void deleteAll() {
		allWorkingPlaces.deleteAll();
		allWorkers.deleteAll();
	}


}
