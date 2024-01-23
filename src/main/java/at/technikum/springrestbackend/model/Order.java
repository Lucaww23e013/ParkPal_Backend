package at.technikum.springrestbackend.model;

public class Order {

    private String id;

    private String restaurant;

    public Order(String id, String restaurant) {
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
