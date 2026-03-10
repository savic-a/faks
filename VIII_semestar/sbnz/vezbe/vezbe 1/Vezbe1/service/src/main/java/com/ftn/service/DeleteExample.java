package com.ftn.service;

import java.util.Date;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.ftn.model.Coupon;
import com.ftn.util.DebugAgendaEventListener;
import com.ftn.util.KnowledgeSessionHelper;

public class DeleteExample {

	public static void main() {
		KieContainer kc = KnowledgeSessionHelper.createRuleBase();
		KieSession kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, "ksession-rules");
		
		kSession.addEventListener(new DebugAgendaEventListener());       
        
		Coupon c = new Coupon();
		c.setValidUntil(new Date(System.currentTimeMillis() - 1000000));
		
		kSession.insert(c);
		kSession.fireAllRules();
		
		
	}

}
