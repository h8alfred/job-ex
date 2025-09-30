package org.job.sbjobex.controller;

import lombok.extern.slf4j.Slf4j;
import org.job.sbjobex.dto.WorkzagJobsDTO;
import org.job.sbjobex.service.PersonioJobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/job-listings")
public class JobListingController {

    private final PersonioJobService personioJobService;

    public JobListingController(PersonioJobService personioJobService) {
        this.personioJobService = personioJobService;
    }

    @GetMapping
    public ResponseEntity<WorkzagJobsDTO> getJobs() {
        try {
            WorkzagJobsDTO jobs = personioJobService.fetchJobs();
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            log.error("Error fetching job listings", e);
            return ResponseEntity.status(500).body(null);
        }
    }
}