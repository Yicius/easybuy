package cn.easybuy.service.order;

import cn.easybuy.dao.order.UserAddressDao;
import cn.easybuy.dao.order.UserAddressDaoImpl;
import cn.easybuy.entity.UserAddress;
import cn.easybuy.utils.DataSourceUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserAddressServiceImpl implements UserAddressService {
    public List<UserAddress> queryUserAddressList(Integer userId){
        Connection connection = null;
        List<UserAddress> userAddressList =null;
        try {
            connection = DataSourceUtil.openConnection();
            UserAddressDao userAddressDao = new UserAddressDaoImpl(connection);
            userAddressList = userAddressDao.queryUserAddressList(userId);
        }catch (Exception e){
            e.printStackTrace();;
        }finally {
            DataSourceUtil.closeConnection(connection);
        }
        return userAddressList;
    }
    public Integer addUserAddress(Integer id, String address,String remark) {
        Connection connection = null;
        Integer userAddressId = null;
        try {
            connection = DataSourceUtil.openConnection();
            UserAddressDao userAddressDao = new UserAddressDaoImpl(connection);
            UserAddress userAddress=new UserAddress();
            userAddress.setUserId(id);
            userAddress.setAddress(address);
            userAddress.setRemark(remark);
            userAddressId = userAddressDao.saveUserAddress(userAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            DataSourceUtil.closeConnection(connection);
        }
        return userAddressId;
    }

    public UserAddress getUserAddressById(Integer id) {
        Connection connection = null;
        UserAddress userAddress= null;
        try {
            connection = DataSourceUtil.openConnection();
            UserAddressDao userAddressDao = new UserAddressDaoImpl(connection);
            userAddress = (UserAddress) userAddressDao.getUserAddressById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DataSourceUtil.closeConnection(connection);
            return  userAddress;
        }
    }
}
