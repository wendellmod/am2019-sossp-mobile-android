package br.com.sossp.sosspapp.models;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Occurrence {

    private Long occurrenceId;
    private String typeOccurrence;
    private LocalDate dateOccurrence;
    private LocalDate currentDate;
    private boolean status;
    private long longitude;
    private long latitude;

}
