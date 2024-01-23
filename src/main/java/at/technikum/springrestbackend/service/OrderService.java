package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    public List<Order> findAll() {
        return List.of(new Order("1", "Vapiano"));
    }

    public Order find(String id) {
        return new Order(id, "Vapiano");
    }

    public Order save(Order order) {
        return order;
    }
}
