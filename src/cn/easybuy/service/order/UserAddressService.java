package cn.easybuy.service.order;

import cn.easybuy.entity.UserAddress;

import java.util.List;

public interface UserAddressService {
    public List<UserAddress> queryUserAddressList(Integer userId);
    public Integer addUserAddress(Integer id, String address,String remark);
    public UserAddress getUserAddressById(Integer id);

}
