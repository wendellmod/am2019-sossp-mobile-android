package br.com.sossp.sosspapp.models;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Occurrence {

    private Long occurrenceId;
    private String typeOccurrence;
    private String dateOccurrence;
    private String currentDate;
    private boolean status;
    private double longitude;
    private double latitude;

}
