package cn.easybuy.utils;

import cn.easybuy.entity.ProductCategory;

import java.util.List;

//与前台交互
public class ProductCategoryVo {
    private ProductCategory productCategory;
    private List<ProductCategoryVo> productCategoryVoList;


    public List<ProductCategoryVo> getProductCategoryVoList() {
        return productCategoryVoList;
    }

    public void setProductCategoryVoList(List<ProductCategoryVo> productCategoryVoList) {
        this.productCategoryVoList = productCategoryVoList;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
}
