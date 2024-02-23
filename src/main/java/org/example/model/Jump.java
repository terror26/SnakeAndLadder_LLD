package org.example.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Jump {
    private int from;
    private int to;

    @Override
    public String toString() {
        return "Jump{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
