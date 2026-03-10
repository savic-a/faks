package com.ftn.sbnz.service.tests;

import com.ftn.sbnz.model.models.User;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class BackQueryTest {

    public void getChildren(String parent, KieSession ksession) {
        var results = ksession.getQueryResults("findAllChildren", parent);
        for (var result : results) {
            var name = ((User)result.get("user")).getPotomak();
            if(!name.equals("David"))
                System.out.println(name);
            getChildren(name, ksession);
        }
    }

    @Test
    public void test() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession ksession = kContainer.newKieSession("bwKsession");

        ksession.insert(new User("Mika", "Peric"));
        ksession.insert(new User("Jova", "Jovic"));
        ksession.insert(new User("Kosta", "Jovic"));
        ksession.insert(new User("Petar", "Mika"));
        ksession.insert(new User("Stevan", "Mika"));
        ksession.insert(new User("Jovan", "Mika"));
        ksession.insert(new User("Jovan", "Jova"));
        ksession.insert(new User("Milana", "Jova"));
        ksession.insert(new User("Marko", "Jova"));
        ksession.insert(new User("David", "Kosta"));
        ksession.insert(new User("David", "Marko"));
        ksession.insert(new User("Nemanja", "Marko"));
        ksession.insert(new User("Jovana", "Marko"));
        ksession.insert(new User("Marija", "Milana"));
        ksession.insert(new User("Petra", "Jovan"));

        //getChildren("Jovic",ksession);

        ksession.fireAllRules();
        System.out.println("---");
    }

}
