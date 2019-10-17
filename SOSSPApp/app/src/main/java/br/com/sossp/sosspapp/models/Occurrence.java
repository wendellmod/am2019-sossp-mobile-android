package br.com.sossp.sosspapp.models;

import lombok.Data;

@Data
public class Occurrence {

    private Long occurrenceId;
    private String typeOccurrence;
    private String dateOccurrence;
    private String currentDate;
    private boolean status;
    private String longitude;
    private String latitude;

}
