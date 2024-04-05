package at.technikum.parkpalbackend.model;

import jakarta.validation.constraints.NotNull;

@NotNull
public enum Salutation {
    OTHER,

    FEMALE,

    MALE,
}
