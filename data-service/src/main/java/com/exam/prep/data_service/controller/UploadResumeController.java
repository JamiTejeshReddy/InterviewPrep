package com.exam.prep.data_service.controller;


import com.exam.prep.data_service.model.UploadResumeResponse;
import com.exam.prep.data_service.service.UploadResumeService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
public class UploadResumeController {
    private final UploadResumeService uploadResumeService;

    public UploadResumeController(UploadResumeService uploadResumeService) {
        this.uploadResumeService = uploadResumeService;
    }


    public ResponseEntity<UploadResumeResponse> uploadResume(@Valid String userid, @Valid String username, @Valid String email, MultipartFile file) {
        return uploadResumeService.uploadResume(userid, username, email, file);
    }
}
