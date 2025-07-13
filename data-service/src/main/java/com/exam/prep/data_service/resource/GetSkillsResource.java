package com.exam.prep.data_service.resource;


import com.exam.prep.data_service.api.SkillsApi;
import com.exam.prep.data_service.controller.SkillsController;
import com.exam.prep.data_service.model.FetchSkillsRequest;
import com.exam.prep.data_service.model.FetchSkillsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetSkillsResource implements SkillsApi {
    private final SkillsController skillsController;

    public GetSkillsResource(SkillsController skillsController) {
        this.skillsController = skillsController;
    }

    @Override
    public ResponseEntity<FetchSkillsResponse> skillsQueryPost(FetchSkillsRequest fetchSkillsRequest
    ) {
        return skillsController.getSkills(fetchSkillsRequest);
    }
}
