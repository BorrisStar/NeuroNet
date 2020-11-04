package com.borris.nn.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Pic {
    String fileName;
    Integer digit;
    LocalDate date = LocalDate.now();
}
