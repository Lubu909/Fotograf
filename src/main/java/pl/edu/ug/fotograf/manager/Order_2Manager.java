package pl.edu.ug.fotograf.manager;

import pl.edu.ug.fotograf.model.ORDER_2;

import java.util.List;

public interface Order_2Manager {
    public void addOrder_2(ORDER_2 order);
    public void delOrder_2(ORDER_2 order);
    public ORDER_2 getOrder_2(int id);
    public List<ORDER_2> getAllOrder_2();
}
