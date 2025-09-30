package org.job.sbjobex.service;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.job.sbjobex.dto.WorkzagJobsDTO;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class JobListingService {

    public ModelMap getModelMap(WorkzagJobsDTO workzagJobsDTO) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("title", "Job Listing Application");
        modelMap.addAttribute("positions", workzagJobsDTO.getPositions());
        return modelMap;
    }

    public String renderJobListingTemplate(WorkzagJobsDTO workzagJobsDTO) {
        ModelMap modelMap = getModelMap(workzagJobsDTO);

        try (InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream("/templates/job-listing.mustache")), StandardCharsets.UTF_8)) {
            Mustache.Compiler compiler = Mustache.compiler();
            Template template = compiler.compile(reader);
            return template.execute(modelMap);
        } catch (Exception e) {
            throw new RuntimeException("Failed to render job-listing.mustache template", e);
        }
    }
}