package cn.easybuy.Servlet.pre;

import cn.easybuy.Servlet.AbstractServlet;
import cn.easybuy.entity.Product;
import cn.easybuy.params.ProductParam;
import cn.easybuy.service.Product.ProductCategoryService;
import cn.easybuy.service.Product.ProductCategoryServiceImlp;
import cn.easybuy.service.Product.ProductService;
import cn.easybuy.service.Product.ProductServiceImpl;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.Pager;
import cn.easybuy.utils.ProductCategoryVo;
import cn.easybuy.utils.ReturnResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet",urlPatterns = "/product")
public class ProductServlet extends AbstractServlet {
    private ProductService productService = new ProductServiceImpl();
    private ProductCategoryService productCategoryService = new ProductCategoryServiceImlp();

    @Override
    public Class getServletClass() {
        return ProductServlet.class;
    }

    public  String queryProductList(HttpServletRequest request, HttpServletResponse response) throws  Exception{
        String categoryId = request.getParameter("categoryId");
        String keyWord = request.getParameter("keyWord");
        // 第几页
        String currentPageStr = request.getParameter("currentPage");
        //页面容量
        String pageSizeStr = request.getParameter("pageSize");
        int rowPerPage = EmptyUtils.isNotEmpty(pageSizeStr)?Integer.parseInt(pageSizeStr): 8;
        int currentPage =EmptyUtils.isNotEmpty(currentPageStr)?Integer.parseInt(currentPageStr):1;
        ProductParam params = new ProductParam();
        params.setCategoryId(EmptyUtils.isNotEmpty(categoryId)?Integer.parseInt(categoryId):null);
        params.setKeyword(keyWord);
        params.openPage((currentPage-1)*rowPerPage,rowPerPage);
        int total = productService.queryProductCount(params);
        Pager pager=new Pager(total,rowPerPage,currentPage);
        pager.setUrl("product?action=queryProductList&categoryId"+(EmptyUtils.isNotEmpty(categoryId)?categoryId:null)+"&keyWord="+(EmptyUtils.isNotEmpty(keyWord)?keyWord:null));
        List<ProductCategoryVo> pcVoList = productCategoryService.queryAllProductCategory();
        List<Product> pList = productService.queryProductList(params);
        request.setAttribute("pList",pList);
        request.setAttribute("pager",pager);
        request.setAttribute("total",total);
        request.setAttribute("keyWord",keyWord);
        request.setAttribute("pcList",pcVoList);
        return "/pre/product/BrandList";


    }
    public String queryProductDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String id =request.getParameter("id");
        Product product = productService.findById(id);
        List<ProductCategoryVo> productCategoryVoList =productCategoryService.queryAllProductCategory();
        request.setAttribute("product",product);
        request.setAttribute("pcList",productCategoryVoList);
        return "/pre/product/productDeatil";
    }


}
