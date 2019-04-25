package cn.easybuy.service.User;

import cn.easybuy.entity.User;
import cn.easybuy.params.UserParam;

import java.util.List;

public interface UserService {
    //根据用户的账号获取用户记录
    public User findUser(String uname);
    public boolean sava(User user);
    public void update(User user);//更新用户信息

    public User findByLoginName(String loginName);//根据ID查询用户信息

    public boolean save(User user);//新增用户信息

    void delete(String id);//根据用户名删除用户

    public List<User> queryUserList(UserParam userParam);

    public int queryUserCount(UserParam params);

    public User queryUserById(Integer userId);
}
