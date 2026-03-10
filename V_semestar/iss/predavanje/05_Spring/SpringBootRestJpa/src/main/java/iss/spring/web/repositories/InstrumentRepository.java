package iss.spring.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.spring.web.entities.inheritance.Instrument;

public interface InstrumentRepository  extends JpaRepository<Instrument, Long>{

}
