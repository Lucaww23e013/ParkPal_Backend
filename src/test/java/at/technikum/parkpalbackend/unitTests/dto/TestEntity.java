package at.technikum.parkpalbackend.unitTests.dto;

import at.technikum.parkpalbackend.dto.UpperLimit;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TestEntity {
    @UpperLimit(years = 1, message = "Date exceeds the upper limit of 1 year")
    @NotNull
    private LocalDateTime date;

    public TestEntity(LocalDateTime date) {
        this.date = date;
    }
}
