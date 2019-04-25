package cn.easybuy.service.order;

import cn.easybuy.dao.order.OrderDao;
import cn.easybuy.dao.order.OrderDaoImpl;
import cn.easybuy.dao.order.OrderDetailDao;
import cn.easybuy.dao.order.OrderDetailDaoImpl;
import cn.easybuy.dao.product.ProductDao;
import cn.easybuy.dao.product.ProductDaoImpl;
import cn.easybuy.entity.Order;
import cn.easybuy.entity.OrderDetail;
import cn.easybuy.entity.User;
import cn.easybuy.params.OrderDetailParam;
import cn.easybuy.params.OrderParams;
import cn.easybuy.utils.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private Connection connection;
    private OrderDao orderDao;
    private OrderDetailDao orderDetailDao;
    private ProductDao productDao;

    //订单的生成，订单添加，订单详情添加，库存修改，注意：事务的控制
    @Override
    public Order payShoppingCart(User user, String address, ShoppingCart cart) throws SQLException {
       Order order = new Order();
        try {
            connection = DataSourceUtil.openConnection();
            orderDao = new OrderDaoImpl(connection);
            orderDetailDao = new OrderDetailDaoImpl(connection);
            productDao = new ProductDaoImpl(connection);
            //开启事务的控制，整体进行数据库的提交
            connection.setAutoCommit(false);
            //订单生成
            order.setUserId(user.getId());
            order.setUserAddress(address);
            order.setCreateTime(new Date());
            order.setCost(cart.getTotalCost());
            order.setSerialNumber(StringUtils. randomUUID());
            order.setLoginName(user.getLoginName());
            orderDao.saveOrder(order);
            for (ShoppingCartItem item:cart.getItems()){
                //添加订单详情
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(order.getId());
                orderDetail.setCost(item.getCost());
                orderDetail.setProduct(item.getProduct());
                orderDetail.setQuantity(item.getQuantity());
                orderDetailDao.saveOrderDetail(orderDetail,order.getId());
                //修改库存
                productDao.updateStock(item.getProduct().getId(),item.getQuantity());
            }
            connection.commit();
        }catch (SQLException e){
            connection.rollback();
            order = null;
            e.printStackTrace();
        }finally {
            DataSourceUtil.closeConnection(connection);
            return order;
        }

    }

    @Override
    public int getOrderRowCount(OrderParams params) {
        Connection connection = null;
        int rtn = 0;
        try {
            connection = DataSourceUtil.openConnection();
            OrderDao orderDao = new OrderDaoImpl(connection);
            rtn = orderDao.queryOrderCount(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return rtn;
    }

    @Override
    public List<Order> queryOrderList(Pager pager) {
        Connection connection = null;
        List<Order> orderList = null;
        try {
            connection = DataSourceUtil.openConnection();
            OrderDao orderDao = new OrderDaoImpl(connection);
            OrderParams params = new OrderParams();
            params.openPage((pager.getCurrentPage() - 1) * pager.getRowPerPage(), pager.getRowPerPage());
            orderList=orderDao.queryOrderList(params);
            for (int i = 0; i < orderList.size(); i++) {
                Order order = orderList.get(i);
                OrderDetailParam orderDetailParam=new OrderDetailParam();
                orderDetailParam.setOrderId(order.getId());
                order.setOrderDetailList(queryOrderDetailList(orderDetailParam));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return orderList;
    }

    @Override
    public List<Order> queryOrderList(Integer id, Pager pager) {
        Connection connection = null;
        List<Order> orderList = null;
        try {
            connection = DataSourceUtil.openConnection();
            OrderDao orderDao = new OrderDaoImpl(connection);
            OrderParams params = new OrderParams();
            params.setUserId(id);
            params.setStartIndex((pager.getCurrentPage() - 1) * pager.getRowPerPage());
            params.setPageSize(pager.getRowPerPage());
            params.setSort(" createTime desc ");
            orderList = orderDao.queryOrderList(params);
            for (int i = 0; i < orderList.size(); i++) {
                Order order = orderList.get(i);
                OrderDetailParam orderDetailParam=new OrderDetailParam();
                orderDetailParam.setOrderId(order.getId());
                order.setOrderDetailList(queryOrderDetailList(orderDetailParam));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return orderList;
    }

    @Override
    public List<OrderDetail> queryOrderDetailList(OrderDetailParam params) {
        Connection connection = null;
        List<OrderDetail> rtn = null;
        try {
            connection = DataSourceUtil.openConnection();
            OrderDetailDao orderDetailDao = new OrderDetailDaoImpl(connection);
            rtn = orderDetailDao.queryOrderDetailList(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return rtn;
    }


}
