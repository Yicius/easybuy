package cn.easybuy.service.Product;


import cn.easybuy.dao.product.ProductCategoryDao;
import cn.easybuy.dao.product.ProductDao;
import cn.easybuy.dao.product.ProductDaoImpl;
import cn.easybuy.entity.Product;
import cn.easybuy.params.ProductParam;
import cn.easybuy.utils.DataSourceUtil;
import cn.easybuy.utils.EmptyUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl  implements ProductService{
    private Connection connection;
    private ProductDao productDao;
    @Override
    public List<Product> queryProductList(ProductParam params) {
        List<Product> pList = new ArrayList<Product>();
        try {
            connection= DataSourceUtil.openConnection();
            productDao = new ProductDaoImpl(connection);
            pList = productDao.queryProductList(params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return pList;
    }

    @Override
    public int queryProductCount(ProductParam params) {
        int result = 0;
        try {
            connection= DataSourceUtil.openConnection();
            productDao = new ProductDaoImpl(connection);
            result=productDao.queryProductCount(params);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return result;
    }
    public Product findById(String id) {//根据ID查询商品
        Connection connection = null;
        Product product = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductDao productDao = new ProductDaoImpl(connection);
            product = productDao.getProductById(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return product;
    }

    @Override
    public int getProductCountBycategory(Integer categoryId) {
        Connection connection = null;
        int count = 0;
        try {
            connection = DataSourceUtil.openConnection();
            ProductDao productDao = new ProductDaoImpl(connection);
            ProductParam param=new ProductParam();
            param.setCategoryLevel1Id(categoryId);
            count = productDao.queryProductCount(param);
            if(count>0) return count;
            param.setCategoryLevel2Id(categoryId);
            count = productDao.queryProductCount(param);
            if(count>0) return count;
            param.setCategoryLevel3Id(categoryId);
            count = productDao.queryProductCount(param);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return count;
    }
    @Override
    public int getProductRowCount(ProductParam params) {
        Connection connection = null;
        int count = 0;
        try {
            connection = DataSourceUtil.openConnection();
            ProductDao productDao = new ProductDaoImpl(connection);
            count = productDao.queryProductCount(params);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            DataSourceUtil.closeConnection(connection);
        }
        return count;
    }

    @Override
    public List<Product> queryProductsList(ProductParam params) {
        Connection connection = null;
        List<Product> rtn = new ArrayList<Product>();
        try {
            connection = DataSourceUtil.openConnection();
            ProductDao productDao = new ProductDaoImpl(connection);
            //设置查询参数
            rtn = productDao.queryProductList(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return rtn;
    }

    @Override
    public Integer saveOrUpdate(Product product) {
        Connection connection = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductDao productDao = new ProductDaoImpl(connection);
            ProductParam params = new ProductParam();
            if(EmptyUtils.isEmpty(product.getId())){
                productDao.save(product);
            } else {
                productDao.update(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductDao productDao = new ProductDaoImpl(connection);
            //设置查询参数
            productDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }

}
