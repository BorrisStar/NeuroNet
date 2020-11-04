package com.borris.nn.model;

import lombok.Data;

import java.util.List;

@Data
public class PicsResponseBody {
    List<Pic> pics;
    String msg;
}
