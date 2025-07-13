package com.exam.prep.data_service.service;


import com.exam.prep.data_service.entity.UserInformation;
import com.exam.prep.data_service.jpa.UserInformationJpa;

import com.exam.prep.data_service.model.UploadResumeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.UUID;

@Service
public class UploadResumeService {
    private final UserInformationJpa userInformationJpa;

    public UploadResumeService(UserInformationJpa userInformationJpa) {
        this.userInformationJpa = userInformationJpa;
    }

    public ResponseEntity<UploadResumeResponse> uploadResume(String username, String userId, String email, MultipartFile resume){
    try {
            UserInformation userInformation = new UserInformation();
            userInformation.setUserEmail(email);
            userInformation.setUserName(username);
            userInformation.setUserId(userId);
            userInformation.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")).toString());
//            userInformation.setResumeId("Resume_" + username);

            byte[] fileContent = resume.getBytes();

            userInformation.setResume(fileContent);

            // Save to database
            UserInformation savedInfo= userInformationJpa.save(userInformation);

            UploadResumeResponse response = new UploadResumeResponse();
            response.setMessage("Resume uploaded successfully!");
            response.setResumeId(BigDecimal.valueOf(savedInfo.getResumeId()));
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            UploadResumeResponse response = new UploadResumeResponse();
            response.setMessage("Failed to upload resume: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    public byte[] getFileStream(String location) throws IOException {
        Path filePath = Path.of(location);

        // Read the file content
        byte[] fileContent = Files.readAllBytes(filePath);

        // Encode the file content to Base64
        String base64EncodedPdf = Base64.getEncoder().encodeToString(fileContent);

        // Print the Base64 encoded content
        System.out.println(base64EncodedPdf);
        return base64EncodedPdf.getBytes();
    }
}