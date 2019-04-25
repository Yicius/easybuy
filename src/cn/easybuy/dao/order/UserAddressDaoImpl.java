package cn.easybuy.dao.order;

import cn.easybuy.dao.BaseDaoImpl;
import cn.easybuy.entity.UserAddress;
import cn.easybuy.utils.EmptyUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserAddressDaoImpl extends BaseDaoImpl implements UserAddressDao{
    public UserAddressDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public List<UserAddress> queryUserAddressList(Integer userId) {
        List<Object> paramsList = new ArrayList<Object>();
        List<UserAddress> userAddressList = new ArrayList<UserAddress>();
        StringBuffer sql = new StringBuffer("SELECT id,userId,address,createTIme,isDefault,remark FROM easybuy_user_address WHERE 1=1");
        if(EmptyUtils.isNotEmpty(userId)){
            sql.append(" and userId = ?");
            paramsList.add(userId);
        }
        ResultSet resultSet = this.executeQuery(sql.toString(),paramsList.toArray());
            try {
                while (resultSet.next()){
                   UserAddress userAddress = new UserAddress();
                    userAddress.setId(resultSet.getInt("id"));
                    userAddress.setAddress(resultSet.getString("address"));
                    userAddress.setUserId(resultSet.getInt("userId"));
                    userAddress.setCreateTime(resultSet.getDate("createTime"));
                    userAddress.setRemark(resultSet.getString("remark"));
                    userAddressList.add(userAddress);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                this.closeResource();
                this.closeResource(resultSet);
            }
        return userAddressList;
    }
    public Integer saveUserAddress(UserAddress userAddress) {
        Integer id=0;
        String sql=" INSERT into easybuy_user_address(userId,address,createTime,isDefault,remark) VALUES(?,?,?,?,?) ";
        try {
            Object param[]=new Object[]{userAddress.getUserId(),userAddress.getAddress(),new Date(),userAddress.getIsDefault(),userAddress.getRemark()};
            id=this.executeInsert(sql,param);
            userAddress.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
        }
        return id;
    }

    @Override
    public UserAddress getUserAddressById(Integer addressId) {
        List<Object> paramsList=new ArrayList<Object>();
        StringBuffer sql=new StringBuffer(" select id,userId,address,createTime,isDefault,remark from easybuy_user_address  where id=? ");
        UserAddress userAddress =null;
        ResultSet resultSet = this.executeQuery(sql.toString(),new Object[]{addressId});
        try {
            while (resultSet.next()) {
                userAddress = new UserAddress();
                userAddress.setId(resultSet.getInt("id"));
                userAddress.setAddress(resultSet.getString("address"));
                userAddress.setUserId(resultSet.getInt("userId"));
                userAddress.setCreateTime(resultSet.getDate("createTime"));
                userAddress.setRemark(resultSet.getString("remark"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
            this.closeResource(resultSet);
        }
        return userAddress;
    }
}
