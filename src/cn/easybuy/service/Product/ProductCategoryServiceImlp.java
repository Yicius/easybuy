package cn.easybuy.service.Product;

import cn.easybuy.dao.product.ProductCategoryDao;
import cn.easybuy.dao.product.ProductCategoryDaoImpl;
import cn.easybuy.entity.ProductCategory;
import cn.easybuy.params.ProductCategoryParam;
import cn.easybuy.utils.DataSourceUtil;
import cn.easybuy.utils.ProductCategoryVo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryServiceImlp implements ProductCategoryService {
    private Connection connection;
    private ProductCategoryDao pcDao;

    @Override
    public List<ProductCategory> queryAllProductCategory(String parentId) {

        List<ProductCategory> pcList = new ArrayList<ProductCategory>();
        try {
            connection= DataSourceUtil.openConnection();
            pcDao = new ProductCategoryDaoImpl(connection);
            if(parentId==null || "".equals(parentId)){
                parentId="0";
            }
            pcList = pcDao.queryAllProductCategory(parentId);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DataSourceUtil.closeConnection(connection);
        }
        return pcList;
    }

    @Override
    public List<ProductCategoryVo> queryAllProductCategory() {

        //首先一级分类的VO列表
        List<ProductCategoryVo> productCategoryVoList = new ArrayList<ProductCategoryVo>();
        //查询一份分类
        List<ProductCategory> pcList = queryAllProductCategory(null);
        //查询二级分类
        for (ProductCategory productCategory1: pcList
             ) {
           ProductCategoryVo pc1Vo = new ProductCategoryVo();
           pc1Vo.setProductCategory(productCategory1);
           //查询二级分类的VO列表
            List<ProductCategoryVo> pc2VoList = new ArrayList<ProductCategoryVo>();
            //查询二级分类
            List<ProductCategory> pc2List =queryAllProductCategory(productCategory1.getId().toString());
            for (ProductCategory productCategory2:pc2List
                 ) {
                ProductCategoryVo pc2Vo = new ProductCategoryVo();
                pc2Vo.setProductCategory(productCategory2);
                //查询三级分类的vo列表
                List<ProductCategoryVo> pc3VoList = new ArrayList<ProductCategoryVo>();
                //查询三级分类
                List<ProductCategory> pc3List = queryAllProductCategory(productCategory2.getId().toString());
                for (ProductCategory productCategory3: pc3List
                     ) {
                    ProductCategoryVo pc3Vo = new ProductCategoryVo();
                    pc3Vo.setProductCategory(productCategory3);
                    pc3VoList.add(pc3Vo);
                }
                pc2Vo.setProductCategoryVoList(pc3VoList);
                pc2VoList.add(pc2Vo);
            }
            pc1Vo.setProductCategoryVoList(pc2VoList);
            productCategoryVoList.add(pc1Vo);
        }
        return productCategoryVoList;
    }

    @Override
    public List<ProductCategory> queryProductCategoryList(ProductCategoryParam params) {
        Connection connection = null;
        List<ProductCategory> rtn = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            rtn = productCategoryDao.queryProductCategoryList(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return rtn;
    }

    @Override
    public int queryProductCategoryCount(ProductCategoryParam params) {
        Connection connection = null;
        int rtn = 0;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            rtn = productCategoryDao.queryProductCategoryCount(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return rtn;
    }

    @Override
    public List<ProductCategory> queryProductCategorylistBySql(ProductCategoryParam params) {
        Connection connection = null;
        List<ProductCategory> rtn = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            rtn = productCategoryDao.queryProductCategorylist(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return rtn;
    }

    @Override
    public ProductCategory getById(Integer id) {
        Connection connection = null;
        ProductCategory productCategory = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            productCategory =productCategoryDao.queryProductCategoryById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return productCategory;
    }

    @Override
    public void modifyProductCategory(ProductCategory productCategory) {
        Connection connection = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            productCategoryDao.update(productCategory);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }

    @Override
    public void addProductCategory(ProductCategory productCategory) {
        Connection connection = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            productCategoryDao.save(productCategory);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            productCategoryDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }


}
