package pl.edu.ug.service;

import pl.edu.ug.model.Order;

public interface OrderService {
    void add(Order order);
    void delete(Order order);
    void delete(Long id);
    Order get(Long id);
    //List<Order> getByUser(User fotograf);
}
