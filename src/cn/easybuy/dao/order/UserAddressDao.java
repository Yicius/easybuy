package cn.easybuy.dao.order;

import cn.easybuy.entity.UserAddress;

import java.util.List;

public interface UserAddressDao {
    //根据userid来查询出地址列表
    public List<UserAddress> queryUserAddressList(Integer userId);
    public Integer saveUserAddress(UserAddress userAddress);
    public UserAddress getUserAddressById(Integer addressId);
}
