package com.geico.roadassistant.domain.assistant;

import com.geico.roadassistant.domain.location.Geolocation;
import lombok.Data;

@Data
public class Assistant implements Comparable {
    private String name;
    private Geolocation location;

    public Assistant(String name, Geolocation location) {
        this.name = name;
        this.location = location;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Assistant) {
            return name.compareTo(((Assistant)o).getName());
        }
        return 0;
    }
}


