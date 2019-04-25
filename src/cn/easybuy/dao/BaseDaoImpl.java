package cn.easybuy.dao;

import cn.easybuy.utils.DataSourceUtil;
import cn.easybuy.utils.EmptyUtils;

import java.sql.*;

public class BaseDaoImpl {
    private Connection connection;
    private PreparedStatement pstm;
    private ResultSet resultSet;

    public BaseDaoImpl(Connection connection) {
        this.connection = connection;
    }

    //增删改
    protected int executeUpdate(String sql, Object... params) throws SQLException {
        int result = 0;
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
//            DataSourceUtil.closeConnection(connection);
        }
        return result;
    }


    public ResultSet executeQuery(String sql, Object[] params){
        resultSet=null;
        try {
            pstm=connection.prepareStatement(sql);
            if(!EmptyUtils.isEmpty(params)){
                for (int i = 0; i < params.length; i++) {
                    pstm.setObject(i+1,params[i]);
                }
            }
            resultSet = pstm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //释放资源
    public boolean closeResource(){
        if(pstm!=null){
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    public boolean closeResource(ResultSet resultSet){
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public int executeInsert(String sql,Object[] params) {
        Long id = 0L;
        try {
            pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                pstm.setObject(i + 1, params[i]);
            }
            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
                System.out.println("数据主键：" + id);
            }

        } catch (Exception e) {
            e.printStackTrace();
            id = null;
        }

        return id.intValue();

    }

}
