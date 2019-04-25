package cn.easybuy.dao.order;

import cn.easybuy.entity.News;
import cn.easybuy.entity.OrderDetail;
import cn.easybuy.params.OrderDetailParam;

import java.sql.SQLException;
import java.util.List;

/**
 * 订单详细
 * Created by bdqn on 2016/5/8.
 */
public interface OrderDetailDao {

    public void saveOrderDetail(OrderDetail detail, int orderId) throws SQLException;

	public void deleteById(OrderDetail detail) throws Exception;
	
	public OrderDetail getOrderDetailById(Integer id)throws Exception; 
	
	public List<OrderDetail> queryOrderDetailList(OrderDetailParam params)throws Exception;
	
	public Integer queryOrderDetailCount(OrderDetailParam params)throws Exception; 
}
