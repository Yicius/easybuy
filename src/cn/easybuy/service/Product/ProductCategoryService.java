package cn.easybuy.service.Product;

import cn.easybuy.entity.ProductCategory;
import cn.easybuy.params.ProductCategoryParam;
import cn.easybuy.utils.ProductCategoryVo;

import java.util.List;

public interface ProductCategoryService {
    //根据id查询所有的商品分类信息
    public List<ProductCategory> queryAllProductCategory(String parentId);
    //查询所有的商品分类信息
    public List<ProductCategoryVo> queryAllProductCategory();

    public List<ProductCategory> queryProductCategoryList(ProductCategoryParam params);

    //查询数目
    public int queryProductCategoryCount(ProductCategoryParam params);

    //根据sql查询商品分类
    public List<ProductCategory> queryProductCategorylistBySql(ProductCategoryParam params);

    public ProductCategory getById(Integer id);
    //修改商品分
    public void modifyProductCategory(ProductCategory productCategory);
    //添加商品分类
    public void addProductCategory(ProductCategory productCategory);
    //根据id删除
    public void deleteById(Integer id);


}
