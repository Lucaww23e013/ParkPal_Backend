package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;

public class OrderDto {

    private String id;

    @NotBlank
    private String restaurant;

    public OrderDto() {
    }

    public OrderDto(String id, String restaurant) {
        this.id = id;
        this.restaurant = restaurant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }
}
