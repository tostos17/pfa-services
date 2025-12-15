package com.fowobi.controller;

import com.fowobi.model.Award;
import com.fowobi.service.AwardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/award")
public class AwardController {

    private final AwardService awardService;

    public AwardController(AwardService awardService) {
        this.awardService = awardService;
    }

    @GetMapping("/all")
    List<Award> getAll() {
        return awardService.getAllAwards();
    }

    @GetMapping("/find/{id}")
    Award getById(@PathVariable long id) {
        return awardService.getById(id);
    }
}
