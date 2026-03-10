package tests;

import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.ftn.model.Order;
import com.ftn.util.KnowledgeSessionHelper;

public class CollectTest {

    protected final String ksessionName = "collectKsession";

    @Test
    public void testInsertModifyAndDelete() {
    	KieContainer kc = KnowledgeSessionHelper.createRuleBase();
        KieSession ksession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, ksessionName);
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        ksession.insert(order1);
        ksession.insert(order2);
        ksession.insert(order3);
        int firedRules = ksession.fireAllRules();
        MatcherAssert.assertThat(1, equalTo(firedRules));
        
        ksession.insert(new Order());
        firedRules = ksession.fireAllRules();
        MatcherAssert.assertThat(1, equalTo(firedRules));
    }
}
