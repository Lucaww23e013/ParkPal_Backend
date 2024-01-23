package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.OrderDto;
import at.technikum.springrestbackend.model.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderMapper {

    public OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setRestaurant(order.getRestaurant());

        return orderDto;
    }

    public Order toEntity(OrderDto orderDto) {
        if (orderDto.getId() == null) {
            return new Order(
                    UUID.randomUUID().toString(),
                    orderDto.getRestaurant()
            );
        }

        return new Order(
                orderDto.getId(),
                orderDto.getRestaurant()
        );
    }
}
