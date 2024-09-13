package com.wc.single.sky.disclosures.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "disclosure_number")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisclosureNumberDto {

    @Id
    private Long id;

    private String dateTime;

    private String number;

    private String type;

    private String version;

    public DisclosureNumberDto(String dateTime, String number, String type, String version) {
        this.dateTime = dateTime;
        this.number = number;
        this.type = type;
        this.version = version;
    }
}
