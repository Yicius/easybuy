package cn.easybuy.dao.user;

import cn.easybuy.entity.User;
import cn.easybuy.params.UserParam;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    //根据用户的账号获取用户记录
    public User findUser(String uname) throws Exception;

    //注册
    public int save(User user);

    User findByLoginName(String loginName) throws Exception;//根据ID查询用户信息


    void update(User user) throws Exception;//更新用户信息

    public void deleteById(String id) throws Exception;

    public List<User> queryUserList(UserParam params)throws Exception;

    public Integer queryUserCount(UserParam params) throws Exception;

    public User queryUserById(Integer id) throws Exception;
}
