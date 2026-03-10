package com.ftn.sbnz.service.tests;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.ftn.sbnz.model.models.Auto;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.concurrent.TimeUnit;

public class CEPConfigTest {

    @Test
    public void testCEP() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer(); 
        KieSession ksession = kContainer.newKieSession("cepKsession");
        
        runPseudoClockExample(ksession);
    }

    private void runPseudoClockExample(KieSession ksession) {
        SessionPseudoClock clock = ksession.getSessionClock();
        for (int index = 0; index < 100; index++) {
            Auto auto = new Auto(1, 10, 12, 11);
            ksession.insert(auto);
            clock.advanceTime(1, TimeUnit.SECONDS);
            int ruleCount = ksession.fireAllRules();
            // ne treba da se izvrsi pravilo
            assertThat(ruleCount, equalTo(0));
        }
        Auto auto1 = new Auto(1, 13, 12, 11);
        ksession.insert(auto1);
        Auto auto2 = new Auto(1, 12, 12, 11);
        ksession.insert(auto2);
        clock.advanceTime(1, TimeUnit.MINUTES);
        int ruleCount = ksession.fireAllRules();
        assertThat(ruleCount, equalTo(1));
    }
}
