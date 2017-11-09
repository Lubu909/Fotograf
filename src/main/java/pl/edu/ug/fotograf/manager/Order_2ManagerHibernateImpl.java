package pl.edu.ug.fotograf.manager;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.ug.fotograf.model.ORDER_2;

import java.util.List;

//@Component
//@Transactional
public class Order_2ManagerHibernateImpl implements Order_2Manager {


    //@Autowired
    //private SessionFactory sessionFactory;

    @Override
    public void addOrder_2(ORDER_2 order) {
        //this.sessionFactory.getCurrentSession().persist(order);
    }

    @Override
    public void delOrder_2(ORDER_2 order) {
        //order = (ORDER_2) this.sessionFactory.getCurrentSession().get(ORDER_2.class, order.getId());
        //this.sessionFactory.getCurrentSession().delete(order);
    }

    @Override
    public ORDER_2 getOrder_2(int id) {
        return null;
    }

    @Override
    public List<ORDER_2> getAllOrder_2() {
        return null;
    }

    /*
    @Override
    public ORDER_2 getOrder_2(int id) {
        //return this.sessionFactory.getCurrentSession().get(ORDER_2.class, id);
    }

    @Override
    public List<ORDER_2> getAllOrder_2() {
        //return this.sessionFactory.getCurrentSession().getNamedQuery("order_2.all").list();
    }
    */
}
