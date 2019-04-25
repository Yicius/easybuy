package cn.easybuy.dao.product;

import cn.easybuy.entity.ProductCategory;
import cn.easybuy.params.ProductCategoryParam;
import cn.easybuy.params.ProductParam;

import java.sql.SQLException;
import java.util.List;

public interface ProductCategoryDao {

    //查询所有的商品分类信息
    public List<ProductCategory> queryAllProductCategory(String parentId);

    //分页查询所有的商品分类
    public List<ProductCategory> queryProductCategoryList (ProductCategoryParam param);

    public Integer queryProductCategoryCount(ProductCategoryParam param);

    public List<ProductCategory> queryProductCategorylist(ProductCategoryParam param);


    public ProductCategory queryProductCategoryById(Integer id);

    public void update(ProductCategory productCategory) ;

    public Integer save(ProductCategory productCategory) ;

    void deleteById(Integer parseLong) throws SQLException;//删除商品分类
    public List<ProductCategory> queryAllProductCategorylist(ProductCategoryParam params);
}
