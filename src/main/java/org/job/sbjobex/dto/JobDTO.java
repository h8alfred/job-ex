package org.job.sbjobex.dto;

import lombok.Data;

@Data
public class JobDTO {
    private Long id;
    private String title;
    private String description;
    private String email;
}