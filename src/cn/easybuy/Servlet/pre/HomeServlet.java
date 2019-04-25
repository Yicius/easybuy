package cn.easybuy.Servlet.pre;

import cn.easybuy.entity.News;
import cn.easybuy.entity.ProductCategory;
import cn.easybuy.service.News.NewsService;
import cn.easybuy.service.News.NewsServiceImpl;
import cn.easybuy.service.Product.ProductCategoryService;
import cn.easybuy.service.Product.ProductCategoryServiceImlp;
import cn.easybuy.Servlet.AbstractServlet;
import cn.easybuy.utils.ProductCategoryVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "HomeServlet",urlPatterns = "/Home")
public class HomeServlet extends AbstractServlet {
    private ProductCategoryService pcService;


    public void init() throws ServletException{
        pcService = new ProductCategoryServiceImlp();
    }
/*    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       pcService = new ProductCategoryServiceImlp();
        List<ProductCategory> pcList = pcService.queryAllProductCategory("0");
        request.setAttribute("pcList",pcList);
        request.getRequestDispatcher("/pre/index.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }*/
    public String index(HttpServletRequest request, HttpServletResponse response) throws Exception{
        NewsService newsService = new NewsServiceImpl();
        List<News> newsList = newsService.queryAll();

        List<ProductCategoryVo> pcList = pcService.queryAllProductCategory( );
        request.setAttribute("pcList",pcList);
        request.setAttribute("newsList",newsList);
        return  "/pre/index";
    }



    @Override
    public Class getServletClass() {
        return HomeServlet.class;
    }
}
