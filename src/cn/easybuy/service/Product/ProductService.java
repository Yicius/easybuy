package cn.easybuy.service.Product;

import cn.easybuy.entity.Product;
import cn.easybuy.params.ProductParam;

import java.util.List;

public interface ProductService {
    public List<Product> queryProductList(ProductParam params);
    //根据条件查询商品数量
    public int queryProductCount(ProductParam params);
    public Product findById(String id);

    /**
     * 根据分类id查询数目
     * @param categoryId
     * @return
     */
    public int getProductCountBycategory(Integer categoryId);
    //查询商品数目
    public int getProductRowCount(ProductParam params);
    //查询商品列表
   public List<Product> queryProductsList(ProductParam params);
//保存商品返回id
    public Integer saveOrUpdate(Product product);//保存一款商品

    /**
     * 根据id删除商品
     * @param id
     */
    public void deleteById(Integer id);

}
