package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.OrderDto;
import at.technikum.springrestbackend.mapper.OrderMapper;
import at.technikum.springrestbackend.model.Order;
import at.technikum.springrestbackend.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {

    private final OrderMapper orderMapper;
    private final OrderService orderService;

    public OrderController(
            OrderMapper orderMapper,
            OrderService orderService
    ) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDto> readAll() {
        return orderService.findAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderDto read(@PathVariable String id) {
        Order order = orderService.find(id);

        return orderMapper.toDto(order);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody @Valid OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        order = orderService.save(order);

        return orderMapper.toDto(order);
    }

    @PutMapping("/{id}")
    public OrderDto update(@PathVariable String id) {
        return new OrderDto("1", "Vapiano");
    }

    @DeleteMapping("/{id}")
    public OrderDto delete(@PathVariable String id) {
        return new OrderDto("1", "Vapiano");
    }
}
