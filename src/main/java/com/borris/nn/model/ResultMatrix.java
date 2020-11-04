package com.borris.nn.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ResultMatrix {
    List<String> statistcs;
    LocalDate date = LocalDate.now();
}
