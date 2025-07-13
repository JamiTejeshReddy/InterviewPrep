package com.exam.prep.data_service.controller;

import com.exam.prep.data_service.model.FetchSkillsRequest;
import com.exam.prep.data_service.model.FetchSkillsResponse;
import com.exam.prep.data_service.service.SkillsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class SkillsController {
    private final SkillsService skillsService;

    public SkillsController(SkillsService skillsService) {
        this.skillsService = skillsService;
    }

    public ResponseEntity<FetchSkillsResponse> getSkills(FetchSkillsRequest fetchSkillsRequest) {
        return skillsService.fetchSkills(fetchSkillsRequest);
    }
}
