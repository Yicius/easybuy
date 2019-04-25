package cn.easybuy.service.order;

import cn.easybuy.entity.Order;
import cn.easybuy.entity.OrderDetail;
import cn.easybuy.entity.User;
import cn.easybuy.params.OrderDetailParam;
import cn.easybuy.params.OrderParams;
import cn.easybuy.utils.Pager;
import cn.easybuy.utils.ShoppingCart;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    //订单生成
    public Order payShoppingCart(User user, String address , ShoppingCart cart) throws SQLException;

    public int getOrderRowCount(OrderParams params);

    List<Order> queryOrderList(Pager pager);

    List<Order> queryOrderList(Integer id,Pager pager);

    List<OrderDetail> queryOrderDetailList(OrderDetailParam params);

}
