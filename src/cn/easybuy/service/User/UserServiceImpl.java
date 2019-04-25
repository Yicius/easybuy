package cn.easybuy.service.User;

import cn.easybuy.dao.user.UserDao;
import cn.easybuy.dao.user.UserDaoImpl;
import cn.easybuy.entity.User;
import cn.easybuy.params.UserParam;
import cn.easybuy.utils.DataSourceUtil;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private Connection connection;
    private UserDao userDao;
    //根据用户的账号获取用户记录
    public User findUser(String uname) {
        User user = new User();

        try {
            connection = DataSourceUtil.openConnection();
            userDao =new UserDaoImpl(connection);
             user = userDao.findUser(uname);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DataSourceUtil.closeConnection(connection);
        }
        return user;
    }

    @Override
    public boolean sava(User user) {
        boolean flag=true;
        try {
            connection = DataSourceUtil.openConnection();
            userDao = new UserDaoImpl(connection);
            int count = userDao.save(user);
            flag = count > 0;
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }finally {
            DataSourceUtil.closeConnection(connection);
        }
        return flag;
    }

    @Override
    public void update(User user) {
        Connection connection = null;
        try {
            connection = DataSourceUtil.openConnection();
            UserDao userDao = new UserDaoImpl(connection);
            userDao.update(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }

    @Override
    public User findByLoginName(String loginName) {
        Connection connection = null;
        User user = null;
        try {
            connection = DataSourceUtil.openConnection();
            UserDao userDao = new UserDaoImpl(connection);
            user = userDao.findByLoginName(loginName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return user;
    }

    @Override
    public boolean save(User user) {
        boolean flag=false;
        Connection connection = null;
        try {
            connection = DataSourceUtil.openConnection();
            UserDao userDao = new UserDaoImpl(connection);
            int count=userDao.save(user);
            flag=count>0;
        } finally {
            DataSourceUtil.closeConnection(connection);
            return flag;
        }
    }

    @Override
    public void delete(String id) {
        Connection connection = null;
        try {
            connection = DataSourceUtil.openConnection();
            UserDao userDao = new UserDaoImpl(connection);
            userDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }

    @Override
    public List<User> queryUserList(UserParam userParam) {
        Connection connection = null;
        List<User> userList = null;
        try {
            connection = DataSourceUtil.openConnection();
            UserDao userDao = new UserDaoImpl(connection);
            userList = userDao.queryUserList(userParam);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return userList;
    }

    @Override
    public int queryUserCount(UserParam params) {
        Connection connection = null;
        int count=0;
        try {
            connection = DataSourceUtil.openConnection();
            UserDao userDao = new UserDaoImpl(connection);
            count = userDao.queryUserCount(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return count;
    }

    @Override
    public User queryUserById(Integer userId) {
        Connection connection = null;
        User user = null;
        try {
            connection = DataSourceUtil.openConnection();
            UserDao userDao = new UserDaoImpl(connection);
            user = (User) userDao.queryUserById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return user;
    }
}
