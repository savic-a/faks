package com.ftn.sbnz.backward.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.backward.service.services.ActivateBackwardRulesService;

@RestController
@RequestMapping("/backward-example")
public class ActivateBackwardRulesController {
  private ActivateBackwardRulesService backService;

  @Autowired
  public ActivateBackwardRulesController(ActivateBackwardRulesService backService) {
    this.backService = backService;
  }

  @GetMapping("")
  public ResponseEntity<Void> fireAllRules() {
    backService.fireRules();
    return ResponseEntity.ok().build();
  }
}
