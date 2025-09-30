package org.job.sbjobex.controller;

import org.job.sbjobex.dto.WorkzagJobsDTO;
import org.job.sbjobex.service.PersonioJobService;
import org.job.sbjobex.service.JobListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobListingController {

    private final PersonioJobService personioJobService;
    private final JobListingService jobListingService;

    public JobListingController(PersonioJobService personioJobService, JobListingService jobListingService) {
        this.personioJobService = personioJobService;
        this.jobListingService = jobListingService;
    }

    @GetMapping("/job-listings")
    public String displayJobListings(ModelMap model) {
        WorkzagJobsDTO workzagJobsDTO = personioJobService.fetchJobs();
        ModelMap jobModel = jobListingService.getModelMap(workzagJobsDTO);
        model.addAllAttributes(jobModel);
        return "job-listing";
    }
}