package com.exam.prep.data_service.resource;

import com.exam.prep.data_service.api.UploadApi;
import com.exam.prep.data_service.controller.UploadResumeController;

import com.exam.prep.data_service.model.UploadResumeResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
public class UploadResumeResource implements UploadApi {
    private final UploadResumeController uploadResumeController;

    public UploadResumeResource(UploadResumeController uploadResumeController) {
        this.uploadResumeController = uploadResumeController;
    }
    public ResponseEntity<UploadResumeResponse> uploadResumeQueryPost(
            @Parameter(name = "username", description = "") @Valid @RequestParam(value = "username", required = false) String username,
            @Parameter(name = "userid", description = "") @Valid @RequestParam(value = "userid", required = false) String userid,
            @Parameter(name = "email", description = "") @Valid @RequestParam(value = "email", required = false) String email,
            @Parameter(name = "file", description = "") @RequestPart(value = "file", required = false) MultipartFile file){
                return uploadResumeController.uploadResume(
                        userid,
                        username,
                        email,
                        file
                );
    }


}