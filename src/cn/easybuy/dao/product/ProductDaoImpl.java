package cn.easybuy.dao.product;

import cn.easybuy.dao.BaseDaoImpl;
import cn.easybuy.entity.Product;
import cn.easybuy.params.ProductParam;
import cn.easybuy.utils.EmptyUtils;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends BaseDaoImpl implements ProductDao {
    public ProductDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public void updateStock(Integer id, Integer quantity) throws SQLException {
        try {
            Object[] params = new Object[] {quantity,id};
            String sql = " update easybuy_product set stock=? where id=? ";
            this.executeUpdate(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
        }
    }

    @Override
    public List<Product> queryProductList(ProductParam params) {
        List<Object> paramsList=new ArrayList<Object>();
        List<Product> productList=new ArrayList<Product>();
        StringBuffer sql=new StringBuffer("  select id,name,description,price,stock,categoryLevel1Id,categoryLevel2Id,categoryLevel3Id,fileName,isDelete from easybuy_product  where 1=1 ");
        ResultSet resultSet = null;
        try {
            if(EmptyUtils.isNotEmpty(params.getName())){
                sql.append(" and name = ? ");
                paramsList.add(params.getName());
            }
            if(EmptyUtils.isNotEmpty(params.getKeyword())){
                sql.append(" and name like ? ");
                paramsList.add("%"+params.getKeyword()+"%");
            }

            if(EmptyUtils.isNotEmpty(params.getCategoryId())){
                sql.append(" and (categoryLevel1Id = ? or categoryLevel2Id=? or categoryLevel3Id=? )");
                paramsList.add(params.getCategoryId());
                paramsList.add(params.getCategoryId());
                paramsList.add(params.getCategoryId());
            }

            if(EmptyUtils.isNotEmpty(params.getSort())){
                sql.append(" order by " + params.getSort()+" ");
            }

            if(params.isPage()){
                sql.append(" limit  " + params.getStartIndex() + "," + params.getPageSize());
            }
            resultSet=this.executeQuery(sql.toString(),paramsList.toArray());
            while (resultSet.next()) {
                Product product = this.tableToClass(resultSet);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource(resultSet);
            this.closeResource();
        }

        return productList;
    }

    @Override
    public Integer queryProductCount(ProductParam params) {
        List<Object> paramsList=new ArrayList<Object>();
        Integer count=0;
        StringBuffer sql=new StringBuffer("  select count(*) count from easybuy_product where 1=1 ");
        if(EmptyUtils.isNotEmpty(params.getName())){
            sql.append(" and name = ? ");
            paramsList.add(params.getName());
        }
        if(EmptyUtils.isNotEmpty(params.getKeyword())){
            sql.append(" and name like ? ");
            paramsList.add("%"+params.getKeyword()+"%");
        }
        ResultSet resultSet = this.executeQuery(sql.toString(),paramsList.toArray());
        try {
            while (resultSet.next()) {
                count=resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource(resultSet);
            this.closeResource();
        }
        return count;
    }

    @Override
    public Product findById(String id) {
        return null;
    }

    @Override
    public Product getProductById(Integer id) throws Exception {
        String sql = " select id,name,description,price,stock,categoryLevel1Id,categoryLevel2Id,categoryLevel3Id,fileName,isDelete from easybuy_product where id = ? ";
        ResultSet resultSet = null;
        Product product=null;
        try {
            Object params[] = new Object[] { id };
            resultSet = this.executeQuery(sql, params);
            while (resultSet.next()) {
                 product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getFloat("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setCategoryLevel1Id(resultSet.getInt("categoryLevel1Id"));
                product.setCategoryLevel2Id(resultSet.getInt("categoryLevel2Id"));
                product.setCategoryLevel3Id(resultSet.getInt("categoryLevel3Id"));
                product.setFileName(resultSet.getString("fileName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet);
            this.closeResource();
        }
        return product;
    }

    @Override
    public void save(Product product) throws Exception {
        Integer id=0;
        String sql=" insert into easybuy_product(name,description,price,stock,categoryLevel1Id,categoryLevel2Id,categoryLevel3Id,fileName,isDelete) values(?,?,?,?,?,?,?,?,?) ";
        try {
            Object param[]=new Object[]{product.getName(),product.getDescription(),product.getPrice(),product.getStock(),product.getCategoryLevel1Id(),product.getCategoryLevel2Id(),product.getCategoryLevel3Id(),product.getFileName(),0};
            id=this.executeInsert(sql,param);
            product.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
        }
    }

    @Override
    public void update(Product product) throws Exception {
        try {
            Object[] params = new Object[] {product.getName(),product.getFileName(),product.getCategoryLevel1Id(),product.getCategoryLevel2Id(),product.getCategoryLevel3Id(),product.getPrice(),product.getStock(),product.getId()};
            String sql = " update easybuy_product set name=?,fileName=?,categoryLevel1Id=?,categoryLevel3Id=?,categoryLevel3Id=?,price=?,stock=? where id=? ";
            this.executeUpdate(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
        }
    }

    @Override
    public void deleteById(Integer id) throws Exception {
        String sql = " delete from easybuy_product where id = ? ";
        Object params[] = new Object[] { id };
        try{
            this.executeUpdate(sql.toString(), params);
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
        }
    }

    public Product tableToClass(ResultSet rs) throws Exception {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getFloat("price"));
        product.setStock(rs.getInt("stock"));
        product.setCategoryLevel1Id(rs.getInt("categoryLevel1Id"));
        product.setCategoryLevel2Id(rs.getInt("categoryLevel2Id"));
        product.setCategoryLevel3Id(rs.getInt("categoryLevel3Id"));
        product.setFileName(rs.getString("fileName"));
        return product;
    }
}
