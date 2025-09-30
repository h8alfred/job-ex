package org.job.sbjobex.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.job.sbjobex.dto.WorkzagJobsDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PersonioJobService {

    private static final String PERSONIO_JOBS_URL = "https://mrge-group-gmbh.jobs.personio.de/xml";

    public WorkzagJobsDTO fetchJobs() {
        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject(PERSONIO_JOBS_URL, String.class);

        try {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(xmlResponse, WorkzagJobsDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XML response", e);
        }
    }
}