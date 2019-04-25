package cn.easybuy.Servlet.pre;

import cn.easybuy.Servlet.AbstractServlet;
import cn.easybuy.entity.Product;
import cn.easybuy.entity.User;
import cn.easybuy.service.Product.ProductService;
import cn.easybuy.service.Product.ProductServiceImpl;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.MemcachedUtils;
import cn.easybuy.utils.ReturnResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "FavoriteServlet" ,urlPatterns = "/favorite")
public class FavoriteServlet extends AbstractServlet {
    private ProductService productService = new ProductServiceImpl();
    @Override
    public Class getServletClass() {
        return FavoriteServlet.class;
    }
        //添加收藏
    public ReturnResult addFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReturnResult result = new ReturnResult();
        String id = request.getParameter("id");
        Product product = productService.findById(id);
        List<Product> favoritlList = queryFavoriteList(request);
        //判断是否满了
        if(favoritlList.size()>0&& favoritlList.size()>=5){
            favoritlList.remove(0);
        }
        boolean flag=false;
        for (int i = 0; i <favoritlList.size() ; i++) {
            if(favoritlList.get(i).getId().equals(product.getId())){
                flag = true;
                break;
            }
        }
        if(!flag){
            favoritlList.add(favoritlList.size(),product);
        }
        String key = getFavoriteKey(request);
        MemcachedUtils.add(key,favoritlList);
        System.out.println("1111111111111"+result.returnSuccess());
        return result.returnSuccess();
    }

    //从Memcached获取已经收藏的product
    public List<Product> queryFavoriteList(HttpServletRequest request) throws Exception{
        String key = getFavoriteKey(request);
        List<Product> products = (List<Product>) MemcachedUtils.get(key);
        if(EmptyUtils.isEmpty(products)){
            products = new ArrayList<Product>();
        }
        return  products;
    }

    //获取Memcached的key值
    public String getFavoriteKey(HttpServletRequest request) throws Exception{
        HttpSession session =request.getSession();
        User user = (User) session.getAttribute("loginUser");
        //判断用户是否登陆
        String key = EmptyUtils.isEmpty(user)?session.getId():user.getLoginName();
        return key;
    }
    public String favouriteList(HttpServletRequest request, HttpServletResponse response)throws Exception{
        List<Product> favoriteList = queryFavoriteList(request);
        request.setAttribute("favoriteList",favoriteList);
        return "/pre/product/favoriteList";
    }
}
