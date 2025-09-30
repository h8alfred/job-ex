package org.job.sbjobex.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class JobDescriptionDTO {
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "value")
    private String value;
}