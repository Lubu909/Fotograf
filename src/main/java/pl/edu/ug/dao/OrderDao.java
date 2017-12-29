package pl.edu.ug.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.ug.model.Order;

public interface OrderDao extends JpaRepository<Order, Long> {
}
