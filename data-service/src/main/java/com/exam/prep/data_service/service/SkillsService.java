package com.exam.prep.data_service.service;

import com.exam.prep.data_service.entity.UserInformation;
import com.exam.prep.data_service.jpa.UserInformationJpa;
import com.exam.prep.data_service.model.FetchSkillsRequest;
import com.exam.prep.data_service.model.FetchSkillsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.InternalServerErrorException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class SkillsService {
    private final UserInformationJpa userInformationJpa;
    @Value("${resume.parsing.skills:siklls}")
    private List<String> skillLabels;
    @Value("${resume.parsing.experience:experience}")
    private List<String> experienceLabels;
    @Value("${resume.parsing.education:education}")
    private List<String> educationLabels;
    @Value("${resume.parsing.projects:projects}")
    private List<String> projectsLabels;

    public SkillsService(UserInformationJpa userInformationJpa) {
        this.userInformationJpa = userInformationJpa;
    }

    public ResponseEntity<FetchSkillsResponse> fetchSkills(FetchSkillsRequest fetchSkillsRequest) {
        FetchSkillsResponse response = new FetchSkillsResponse();
        UserInformation userInformation = userInformationJpa.findById(fetchSkillsRequest.getResumeId().longValue()).orElseThrow(
                () -> new RuntimeException("Resume not found with ID: " + fetchSkillsRequest.getResumeId())
        );

        extractSkills(userInformation, response);
        return ResponseEntity.ok(response);
    }

    private void extractSkills(UserInformation userInformation, FetchSkillsResponse response) {
        byte[] resume = userInformation.getResume();
        parseResume(resume, response);
    }

    private void parseResume(byte[] resume, FetchSkillsResponse response) {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(resume))) {
//            Tika tika = new Tika();

            log.info("Resume size: {}", resume.length);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String extractedText = pdfTextStripper.getText(document);
//            String extractedText = tika.parseToString(new ByteArrayInputStream(resume));
            if (extractedText == null || extractedText.isEmpty()) {
                log.error("No text found in the resume");
                throw new InternalServerErrorException("No text found in the resume");
            }
            String educationRegex = buildDynamicRegex(educationLabels, getNextSectionLabels());
            String skillsRegex = buildDynamicRegex(skillLabels, getNextSectionLabels());
            String projectsRegex = buildDynamicRegex(projectsLabels, getNextSectionLabels());
            String experienceRegex = buildDynamicRegex(experienceLabels, getNextSectionLabels());

            String education = extractSection(extractedText, educationRegex);
            String skills = extractSection(extractedText, skillsRegex);
            String projects = extractSection(extractedText, projectsRegex);
            String experience = extractSection(extractedText, experienceRegex);

            log.info("Extracted education: {}", education);
            log.info("Extracted skills: {}", skills);
            log.info("Extracted projects: {}", projects);
            log.info("Extracted experience: {}", experience);

            response.set(List.of(education.split("\\n")));
            response.setSkills(List.of(skills.split("\\n")));
            response.setProjects(List.of(projects.split("\\n")));
            response.setExperience(List.of(experience.split("\\n")));
        } catch (Exception e) {
            throw new InternalServerErrorException("Error parsing resume file: " + e.getMessage(), e);
        }
    }

    private String buildDynamicRegex(List<String> currentSectionLabels, List<String> nextSectionLabels) {
        String currentSectionPattern = String.join("|", currentSectionLabels);
        String nextSectionPattern = String.join("|", nextSectionLabels);
        return "(?i)(?<=(" + currentSectionPattern + ")\\n).*?(?=(" + nextSectionPattern + ")|$)";
    }

    private String extractSection(String text, String pattern) {
        Pattern regex = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher matcher = regex.matcher(text);
        if (matcher.find()) {
            return matcher.group().trim();
        }
        return "No data found";
    }

    private List<String> getNextSectionLabels() {
        // Combine all possible labels for subsequent sections
        return List.of("achievements", "awards", "certifications", "skills", "education", "experience", "projects");
    }
}