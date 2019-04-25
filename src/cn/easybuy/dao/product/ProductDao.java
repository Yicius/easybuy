package cn.easybuy.dao.product;

import cn.easybuy.entity.Product;
import cn.easybuy.params.ProductParam;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    public void updateStock(Integer id, Integer quantity) throws SQLException;
    //根据条件查询商品列表
    public List<Product> queryProductList(ProductParam params);
    //根据条件查询商品数量
    public Integer queryProductCount(ProductParam params);
    //根据id查询商品
    public Product findById(String id);

    public Product getProductById(Integer id)throws Exception;

    public void save(Product product) throws Exception;

    public void update(Product product) throws Exception;

    public void deleteById(Integer id) throws Exception;
}
