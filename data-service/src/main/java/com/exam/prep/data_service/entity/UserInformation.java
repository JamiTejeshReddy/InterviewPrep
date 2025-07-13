package com.exam.prep.data_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "user_information")
@Data
public class UserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resume_seq")
    @SequenceGenerator(name = "resume_seq", sequenceName = "resume_sequence", allocationSize = 1)
    private Long resumeId;

    public String userId;
    public String userName;
    public String userEmail;

    public String creationDate;
    @Lob
    private byte[] resume;
}
