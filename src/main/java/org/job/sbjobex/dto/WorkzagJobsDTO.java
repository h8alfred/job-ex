package org.job.sbjobex.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class WorkzagJobsDTO {
    @JacksonXmlElementWrapper(localName = "workzag-jobs", useWrapping = false)
    @JacksonXmlProperty(localName = "position")
    private List<PositionDTO> positions;
}