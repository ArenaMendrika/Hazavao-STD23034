package com.hei.school.endpoint.rest.controller;

import com.hei.school.service.HazavaoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class HazavaoController {

  private final HazavaoService hazavaoService;

  @GetMapping("/hazavao")
  public String hazavao(@RequestParam String teny) throws Exception {
    return hazavaoService.getDefinition(teny);
  }
}
