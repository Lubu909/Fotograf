package pl.edu.ug.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.ug.dao.OrderDao;
import pl.edu.ug.model.Order;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public void add(Order order) {
        orderDao.save(order);
    }

    @Override
    public void delete(Order order) {
        orderDao.delete(order);
    }

    @Override
    public void delete(Long id) {
        orderDao.delete(id);
    }

    @Override
    public Order get(Long id) {
        return orderDao.getOne(id);
    }
}
