package org.job.sbjobex.dto;

import lombok.Data;

@Data
public class JobNotificationDTO {
    private Long id;
    private String title;
    private String description;
    private String email;
}