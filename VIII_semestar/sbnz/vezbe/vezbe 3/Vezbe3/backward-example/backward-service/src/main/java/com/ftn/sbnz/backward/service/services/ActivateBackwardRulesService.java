package com.ftn.sbnz.backward.service.services;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ftn.sbnz.backward.model.models.Location;

@Service
public class ActivateBackwardRulesService {
  private final KieContainer kieContainer;

  @Autowired
  public ActivateBackwardRulesService(KieContainer kieContainer) {
    this.kieContainer = kieContainer;
  }

  public void fireRules() {
    KieSession kSession = kieContainer.newKieSession();
    kSession.insert( new Location("Golosemenice", "Biljka") );
		kSession.insert( new Location("Skrivenosemenice", "Biljka") );
		kSession.insert( new Location("Alge", "Golosemenice") );
		kSession.insert( new Location("Kombu alga", "Alga") );
		kSession.insert( new Location("Nori alga", "Alga") );
		kSession.insert( new Location("Agar agar alga", "Alga") );
		kSession.insert( new Location("Mahovine", "Golosemenice") );
		kSession.insert( new Location("Plutajuća Riccia", "Mahovine") );
		kSession.insert( new Location("Javanska mahovina", "Mahovine") );
		kSession.insert( new Location("Paprati", "Golosemenice") );
		kSession.insert( new Location("Dicksonia antarctica", "Paprati") );
		kSession.insert( new Location("Blechnum nudum", "Paprati") );
		kSession.insert( new Location("Sa pupoljkom", "Skrivenosemenice") );
		kSession.insert( new Location("Ljiljan", "Sa pupoljkom") );
		kSession.insert( new Location("Orhideja", "Sa pupoljkom") );
		kSession.insert( new Location("Ruza", "Sa pupoljkom") );
		kSession.insert( new Location("Sa pupoljkom", "Skrivenosemenice") );
		kSession.insert( new Location("Drvo zivota", "Bez pupoljka") );
		kSession.insert( new Location("Asparagus", "Bez pupoljka") );
		kSession.insert( new Location("Hrizantema", "Bez pupoljka") );

    // kSession.insert( "go1" );
    // kSession.fireAllRules();
    // System.out.println("---");

    // kSession.insert( "go2" );
    // kSession.fireAllRules();
    // System.out.println("---");

    // kSession.insert( "go3" );
    // kSession.fireAllRules();
    // System.out.println("---");

    // kSession.insert( new Location("Key", "Draw") );
    // kSession.fireAllRules();
    // System.out.println("---");

    kSession.insert( "go4" );
    kSession.fireAllRules();
    System.out.println("---");

    // kSession.insert( "go5" );
    // kSession.fireAllRules();
  }
}
