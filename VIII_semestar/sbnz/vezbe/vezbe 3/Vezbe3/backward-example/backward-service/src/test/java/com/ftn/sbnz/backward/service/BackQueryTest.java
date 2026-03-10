package com.ftn.sbnz.backward.service;

import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.ftn.sbnz.backward.model.models.Location;

public class BackQueryTest {

    // public void getChildren(String parent, KieSession kSession) {
    //     var results = kSession.getQueryResults("findAllChildren", parent);
    //     for (var result : results) {
    //         var name = ((Location)result.get("Location")).getIme();
    //         System.out.println(name);
    //         getChildren(name, kSession);
    //     }
    // }

    @Test
    public void test() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("bwKsession");

        // kSession.insert( new Location("Golosemenice", "Biljka") );
		// kSession.insert( new Location("Skrivenosemenice", "Biljka") );
		// kSession.insert( new Location("Alge", "Golosemenice") );
		// kSession.insert( new Location("Kombu alga", "Alga") );
		// kSession.insert( new Location("Nori alga", "Alga") );
		// kSession.insert( new Location("Agar agar alga", "Alga") );
		// kSession.insert( new Location("Mahovine", "Golosemenice") );
		// kSession.insert( new Location("Plutajuća Riccia", "Mahovine") );
		// kSession.insert( new Location("Javanska mahovina", "Mahovine") );
		// kSession.insert( new Location("Paprati", "Golosemenice") );
		// kSession.insert( new Location("Dicksonia antarctica", "Paprati") );
		// kSession.insert( new Location("Blechnum nudum", "Paprati") );
		// kSession.insert( new Location("Sa pupoljkom", "Skrivenosemenice") );
		// kSession.insert( new Location("Ljiljan", "Sa pupoljkom") );
		// kSession.insert( new Location("Orhideja", "Sa pupoljkom") );
		// kSession.insert( new Location("Ruza", "Sa pupoljkom") );
		// kSession.insert( new Location("Sa pupoljkom", "Skrivenosemenice") );
		// kSession.insert( new Location("Drvo zivota", "Bez pupoljka") );
		// kSession.insert( new Location("Asparagus", "Bez pupoljka") );
		// kSession.insert( new Location("Hrizantema", "Bez pupoljka") );

        kSession.insert(new Location("S serija", "Samsung"));
        kSession.insert(new Location("A serija", "Samsung"));
        kSession.insert(new Location("Galaxy S10", "S serija"));
        kSession.insert(new Location("S10e", "Galaxy S10"));
        kSession.insert(new Location("S10 +", "Galaxy S10"));
        kSession.insert(new Location("S10 Lite", "Galaxy S10"));
        kSession.insert(new Location("Galaxy S20", "S serija"));
        kSession.insert(new Location("S20", "Galaxy S20"));
        kSession.insert(new Location("S20 +", "Galaxy S20"));
        kSession.insert(new Location("S20 FE", "Galaxy S20"));
        kSession.insert(new Location("Galaxy Ax0", "A serija"));
        kSession.insert(new Location("A10", "Galaxy Ax0"));
        kSession.insert(new Location("A20", "Galaxy Ax0"));
        kSession.insert(new Location("Galaxy Ax4", "A serija"));
        kSession.insert(new Location("A04e", "Galaxy Ax4"));
        kSession.insert(new Location("A23", "Galaxy Ax4"));

        // getChildren("S serija",kSession);

        kSession.insert( "go4" );
        kSession.fireAllRules();
        System.out.println("---");


    }

}
