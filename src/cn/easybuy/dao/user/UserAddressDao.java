package cn.easybuy.dao.user;

import java.util.List;

import cn.easybuy.dao.BaseDaoImpl;
import cn.easybuy.entity.UserAddress;
import cn.easybuy.params.UserAddressParam;


public interface UserAddressDao {
	
	public List<UserAddress> queryUserAddressList(UserAddressParam param);
	
	public Integer saveUserAddress(UserAddress userAddress);
	
	public UserAddress getUserAddressById(Integer addressId);

}
