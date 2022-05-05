package DataAccess;

import Model.OrderItem;

public class OrderDataAccess extends AbstractDataAccess<OrderItem> {

    public int insert(OrderItem orderItem) {
        return super.insert(orderItem);
    }

}
