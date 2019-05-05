package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Edition {

    private final String name;
    private final Integer year;
    private final String city;

}
