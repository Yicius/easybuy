package cn.easybuy.dao.product;

import cn.easybuy.dao.BaseDaoImpl;
import cn.easybuy.entity.ProductCategory;
import cn.easybuy.params.ProductCategoryParam;
import cn.easybuy.params.ProductParam;
import cn.easybuy.utils.EmptyUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCategoryDaoImpl extends BaseDaoImpl implements ProductCategoryDao {


    public ProductCategoryDaoImpl(Connection connection) {
        super(connection);
    }

    public ProductCategory tableToClass(ResultSet resultSet) throws SQLException {
        ProductCategory pc = new ProductCategory();
        pc.setId(resultSet.getInt("id"));
        pc.setName(resultSet.getString("name"));
        pc.setParentId(resultSet.getInt("parentId"));
        pc.setType(resultSet.getInt("type"));
        pc.setIconClass(resultSet.getString("iconClass"));
        pc.setParentName(queryParentName(pc.getParentId()));
        return pc;
    }

    public String queryParentName(Integer id) throws SQLException {
        String name = null;
        ResultSet resultSet = null;
        List<Integer> params = new ArrayList<>();
        params.add(id);
        String sql = "SELECT NAME FROM easybuy_product_category WHERE id = ? ";
        resultSet=this.executeQuery(sql, params.toArray());
        while (resultSet.next()) {
            name = resultSet.getString("name");
        }
        return name;
    }

    @Override
    public List<ProductCategory> queryAllProductCategory(String parentId) {
        List<ProductCategory> pcList = new ArrayList<ProductCategory>();
        StringBuffer sql = new StringBuffer("SELECT id,name,parentId,type,iconClass FROM easybuy_product_category WHERE 1=1");
        List<Object> params = new ArrayList<Object>();
        ResultSet resultSet= null;
        if(parentId!=null && !"".equals(parentId)){
            sql.append(" and parentId=?");
            params.add(parentId);
        }
        try {
         /*   preparedStatement=connection.prepareStatement(sql.toString());
            if(parentId!=null && "".equals(parentId)){
                preparedStatement.setObject(1,parentId);
            }
            resultSet = preparedStatement.executeQuery();*/
            resultSet = this.executeQuery(sql.toString(),params.toArray());
            while (resultSet.next()){
                ProductCategory pc = tableToClass(resultSet);
                pcList.add(pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
               this.closeResource();
               this.closeResource(resultSet);
            }

        return pcList;
    }



    @Override
    public List<ProductCategory> queryProductCategoryList(ProductCategoryParam param) {
        List<Object> paramsList=new ArrayList<Object>();
        List<ProductCategory> productList=new ArrayList<ProductCategory>();
        StringBuffer sql=new StringBuffer("SELECT id,name,parentId,type,iconClass  FROM easybuy_product_category where 1=1 ");
        ResultSet resultSet=null;
        try {
            if(EmptyUtils.isNotEmpty(param.getName())){
                sql.append(" and name like ? ");
                paramsList.add("%"+param.getName()+"%");
            }
            if(EmptyUtils.isNotEmpty(param.getParentId())){
                sql.append(" and parentId = ? ");
                paramsList.add(param.getParentId());
            }
            if(EmptyUtils.isNotEmpty(param.getType())){
                sql.append(" and type = ? ");
                paramsList.add(param.getType());
            }
            if(param.isPage()){
                sql.append(" limit  " + param.getStartIndex() + "," + param.getPageSize());
            }
            resultSet=this.executeQuery(sql.toString(), paramsList.toArray());
            while (resultSet.next()) {
                ProductCategory productCategory = this.tableToClass(resultSet);
                productList.add(productCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
            this.closeResource(resultSet);
        }
        return productList;
    }

    @Override
    public Integer queryProductCategoryCount(ProductCategoryParam param) {
        List<Object> paramsList = new ArrayList<Object>();
        Integer count = 0;
        StringBuffer sql = new StringBuffer("SELECT count(*) count FROM easybuy_product_category where 1=1 ");
        if (EmptyUtils.isNotEmpty(param.getName())) {
            sql.append(" and name like ? ");
            paramsList.add("%" + param.getName() + "%");
        }
        if (EmptyUtils.isNotEmpty(param.getParentId())) {
            sql.append(" and parentId = ? ");
            paramsList.add(param.getParentId());
        }
        ResultSet resultSet = this.executeQuery(sql.toString(), paramsList.toArray());
        try {
            while (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeResource();
            this.closeResource(resultSet);
        }
        return count;
    }

    @Override
    public List<ProductCategory> queryProductCategorylist(ProductCategoryParam param) {
        List<Object> paramsList=new ArrayList<Object>();
        List<ProductCategory> productList=new ArrayList<ProductCategory>();
        StringBuffer sql=new StringBuffer("SELECT id,name,parentId,type,iconClass  FROM easybuy_product_category where 1=1 ");
        ResultSet resultSet=null;
        try {
            if(EmptyUtils.isNotEmpty(param.getName())){
                sql.append(" and name like ? ");
                paramsList.add("%"+param.getName()+"%");
            }
            if(EmptyUtils.isNotEmpty(param.getParentId())){
                sql.append(" and parentId = ? ");
                paramsList.add(param.getParentId());
            }
            if(EmptyUtils.isNotEmpty(param.getType())){
                sql.append(" and type = ? ");
                paramsList.add(param.getType());
            }
            if(param.isPage()){
                sql.append(" limit  " + param.getStartIndex() + "," + param.getPageSize());
            }
            resultSet=this.executeQuery(sql.toString(), paramsList.toArray());
            while (resultSet.next()) {
                ProductCategory productCategory = this.tableToClass(resultSet);
                productList.add(productCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
            this.closeResource(resultSet);
        }
        return productList;
    }

    @Override
    public ProductCategory queryProductCategoryById(Integer id) {
        List<Object> paramsList=new ArrayList<Object>();
        ProductCategory productCategory=null;
        StringBuffer sql=new StringBuffer("SELECT id,name,parentId,type,iconClass  FROM easybuy_product_category where id = ? ");
        ResultSet resultSet=this.executeQuery(sql.toString(),new Object[]{id});
        try {
            while (resultSet.next()) {
                productCategory = this.tableToClass(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
            this.closeResource(resultSet);
        }
        return productCategory;    }

    @Override
    public void update(ProductCategory productCategory) {
        try {
            Object[] params = new Object[] {productCategory.getName(),productCategory.getParentId(),productCategory.getType(),productCategory.getIconClass(),productCategory.getId()};
            String sql = " UPDATE easybuy_product_category SET name=?,parentId=?,type=?,iconClass=? WHERE id =?  ";
            this.executeUpdate(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
        }
    }

    @Override
    public Integer save(ProductCategory productCategory) {
        Integer id=0;
        try {
            String sql=" INSERT into easybuy_product_category(name,parentId,type,iconClass) values(?,?,?,?) ";
            Object param[]=new Object[]{productCategory.getName(),productCategory.getParentId(),productCategory.getType(),productCategory.getIconClass()};
            id=this.executeInsert(sql,param);
            productCategory.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
        }
        return id;
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String sql = " delete from easybuy_product_category where id = ? ";
        Object params[] = new Object[] { id };
        this.executeUpdate(sql, params);
    }

    @Override
    public List<ProductCategory> queryAllProductCategorylist(ProductCategoryParam params) {
        List<ProductCategory> list=new ArrayList<ProductCategory>();
        List<Object> paramsList=new ArrayList<Object>();
        StringBuffer sqlBuffer=new StringBuffer("SELECT epc1.*,epc2.name as parentName FROM easybuy_product_category epc1 LEFT JOIN easybuy_product_category epc2 ON epc1.parentId=epc2.id where 1=1 ");
        ResultSet resultSet=null;
        try{
            if(EmptyUtils.isNotEmpty(params.getName())){
                sqlBuffer.append(" and name like ? ");
                paramsList.add("%"+params.getName()+"%");
            }
            if(EmptyUtils.isNotEmpty(params.getParentId())){
                sqlBuffer.append(" and parentId = ? ");
                paramsList.add(params.getParentId());
            }
            if(EmptyUtils.isNotEmpty(params.getSort())){
                sqlBuffer.append(" order by " + params.getSort()+" ");
            }
            if(params.isPage()){
                sqlBuffer.append(" limit  " + params.getStartIndex() + "," + params.getPageSize());
            }
            resultSet=this.executeQuery(sqlBuffer.toString(),paramsList.toArray());
            ResultSetMetaData md=resultSet.getMetaData();
            Map<String,Object> rowData=new HashMap<String,Object>();
            int count=md.getColumnCount();
            for(int i=1;i<=count;i++){
                rowData.put(md.getColumnLabel(i),resultSet.getObject(i));
            }
            list.add(mapToClass(rowData));
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
            this.closeResource(resultSet);
        }


        return list;
    }

    public ProductCategory mapToClass(Map map) throws Exception {
        ProductCategory productCategory = new ProductCategory();
        Object idObject=map.get("id");
        Object nameObject=map.get("name");
        Object parentIdObject=map.get("parentId");
        Object typeObject=map.get("type");
        Object iconClassObject=map.get("iconClass");
        Object parentNameObject=map.get("parentName");
        productCategory.setId(EmptyUtils.isEmpty(idObject)?null:(Integer)idObject);
        productCategory.setName(EmptyUtils.isEmpty(nameObject)?null:(String)nameObject);
        productCategory.setParentId(EmptyUtils.isEmpty(parentIdObject)?null:(Integer)parentIdObject);
        productCategory.setType(EmptyUtils.isEmpty(typeObject)?null:(Integer)typeObject);
        productCategory.setIconClass(EmptyUtils.isEmpty(iconClassObject)?null:(String)iconClassObject);
        productCategory.setParentName(EmptyUtils.isEmpty(parentNameObject)?null:(String)parentNameObject);
        return productCategory;
    }
}
