package com.geico.roadassistant.domain.assistant;

import java.util.Comparator;

import com.geico.roadassistant.domain.location.Geolocation;
import lombok.Data;

@Data
public class Assistant implements Comparator<Assistant> {
    private String name;
    private Geolocation location;

    public Assistant(String name, Geolocation location) {
        this.name = name;
        this.location = location;
    }

    @Override
    public int compare(Assistant o1, Assistant o2) {
        throw new UnsupportedOperationException("Unimplemented method 'compare'");
    }
}


