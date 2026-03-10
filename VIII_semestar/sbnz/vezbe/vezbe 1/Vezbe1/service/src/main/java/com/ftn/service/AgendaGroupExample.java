package com.ftn.service;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.ftn.model.Item;
import com.ftn.model.OrderLine;
import com.ftn.util.DebugAgendaEventListener;
import com.ftn.util.KnowledgeSessionHelper;

public class AgendaGroupExample {
    public static void main() {
      KieContainer kc = KnowledgeSessionHelper.createRuleBase();
      KieSession kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, "agendaRules");
      
	
		
		kSession.addEventListener(new DebugAgendaEventListener());
		
		Item item = new Item("pencil", 1.5, 2.0);
        OrderLine orderLine = new OrderLine();
        orderLine.setItem(item);
        orderLine.setQuantity(11);
        
        kSession.insert(orderLine);
        
        int firedRules = kSession.fireAllRules();
        
        System.out.println(firedRules);
        
        kSession.getAgenda().getAgendaGroup("promotions").setFocus();
        
        firedRules = kSession.fireAllRules();
        
        System.out.println(firedRules);
	}
}
