package org.job.sbjobex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore unrecognized fields
public class PositionDTO {
    @JacksonXmlProperty(localName = "id")
    private Long id;

    @JacksonXmlProperty(localName = "subcompany")
    private String subcompany;

    @JacksonXmlProperty(localName = "office")
    private String office;

    @JacksonXmlElementWrapper(localName = "additionalOffices")
    @JacksonXmlProperty(localName = "additionalOffice")
    private List<String> additionalOffices;

    @JacksonXmlProperty(localName = "department")
    private String department;

    @JacksonXmlProperty(localName = "recruitingCategory")
    private String recruitingCategory;

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlElementWrapper(localName = "jobDescriptions")
    @JacksonXmlProperty(localName = "jobDescription")
    private List<JobDescriptionDTO> jobDescriptions;

    @JacksonXmlProperty(localName = "employmentType")
    private String employmentType;

    @JacksonXmlProperty(localName = "seniority")
    private String seniority;

    @JacksonXmlProperty(localName = "schedule")
    private String schedule;

    @JacksonXmlProperty(localName = "yearsOfExperience")
    private String yearsOfExperience;

    @JacksonXmlProperty(localName = "keywords")
    private String keywords;

    @JacksonXmlProperty(localName = "occupation")
    private String occupation;

    @JacksonXmlProperty(localName = "occupationCategory")
    private String occupationCategory;

    @JacksonXmlProperty(localName = "createdAt")
    private String createdAt;
}