package cn.easybuy.Servlet.pre;

import cn.easybuy.Servlet.AbstractServlet;
import cn.easybuy.entity.Order;
import cn.easybuy.entity.Product;
import cn.easybuy.entity.User;
import cn.easybuy.entity.UserAddress;
import cn.easybuy.service.Product.ProductCategoryService;
import cn.easybuy.service.Product.ProductCategoryServiceImlp;
import cn.easybuy.service.Product.ProductService;
import cn.easybuy.service.Product.ProductServiceImpl;
import cn.easybuy.service.order.*;
import cn.easybuy.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CartServlet",urlPatterns = "/cartServlet")
public class CartServlet extends AbstractServlet {
    private UserAddressService userAddressService = new UserAddressServiceImpl();
    private ProductService productService = new ProductServiceImpl();
    private CartService cartService = new CartServiceImpl();
    private OrderService orderService = new OrderServiceImpl();
    private ProductCategoryService productCategoryService =new ProductCategoryServiceImlp();
    @Override
    public Class getServletClass() {
        return CartServlet.class;
    }
    public ReturnResult addItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReturnResult result = new ReturnResult();
        String id = request.getParameter("entityId");
        String quantityStr = request.getParameter("quantity");
        Integer quantity = 1;
        if(EmptyUtils.isNotEmpty(quantity)){
            quantity=Integer.parseInt(quantityStr);
        }
        //查询出商品
        Product product = productService.findById(id);
        if(product.getStock()<quantity){
            return result.returnFail("商品数量不足");
        }
        //获取购物车
        ShoppingCart cart = getCartFromSession(request);
        //往购物车里添加商品
        result = cart.addItem(product,quantity);
        if(result.getStatus()== Constants.ReturnResult.SUCCESS){
            cart.setSum(EmptyUtils.isEmpty(cart.getSum())?0.0:cart.getSum() + (product.getPrice()*quantity*1.0));
        }
        return result;
    }
    //从session中获取购物车信息
    private ShoppingCart getCartFromSession(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if(EmptyUtils.isEmpty(cart)){
            cart = new ShoppingCart();
            session.setAttribute("cart",cart);
        }
        return cart;
    }

    public String refreshCart(HttpServletRequest request,  HttpServletResponse response) throws Exception{
        return "/common/pre/searchBar";
    }
    public String toSettlement(HttpServletRequest request,  HttpServletResponse response) throws Exception{
        List<ProductCategoryVo> pcList  = productCategoryService.queryAllProductCategory();
        request.setAttribute("pcList",pcList);
        return "/pre/settlement/toSettlement";
    }
    //跳转到购物车维护页面
    public String toSettlement1(HttpServletRequest request,  HttpServletResponse response) throws  Exception{
            return "/pre/settlement/settlement1";
    }

    public ReturnResult modifyCart(HttpServletRequest request,  HttpServletResponse response) throws  Exception{
        ReturnResult result = new ReturnResult();
        HttpSession session = request.getSession();
        String id = request.getParameter("entityId");
        String quantityStr = request.getParameter("quantity");
        if(EmptyUtils.isEmpty(id)||EmptyUtils.isEmpty(quantityStr)){
            return result.returnFail("参数不能为空");
        }
        Integer quantity = Integer.parseInt(quantityStr);
        ShoppingCart cart = getCartFromSession(request);
        Product product =productService.findById(id);
        if(quantity>product.getStock()){
            return result.returnFail("商品数量不足");
        }

        //修改购物车
        cart =cartService.modifyShoppingCart(id,quantity,cart);
        session.setAttribute("cart",cart);
        return result.returnSuccess();
    }
    public String settlement2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = getUserFromSession(request);
        List<UserAddress> userAddressList = userAddressService.queryUserAddressList(user.getId());
        request.setAttribute("userAddressList", userAddressList);
        return "/pre/settlement/settlement2";
    }

    private User getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        return user;
    }

    public String settlement1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShoppingCart cart = getCartFromSession(request);
        cart = cartService.calculate(cart);
        request.getSession().setAttribute("cart",cart);
        return "/pre/settlement/settlement1";
    }

    public Object settlement3(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ShoppingCart cart = getCartFromSession(request);
        //对购物车中商品的库存进行核对
        ReturnResult result = checkCart(request);
        if(result.getStatus()==Constants.ReturnResult.FAIL){
            return result;
        }
        User user = getUserFromSession(request);
        //新增地址
        String addressId = request.getParameter("addressId");
        String newAddress = request.getParameter("newAddress");
        String newRemark = request.getParameter("newRemark");

        UserAddress userAddress = new UserAddress();
        if("-1".equals(addressId)){
            userAddress.setAddress(newAddress);
            userAddress.setRemark(newRemark);
            userAddressService.addUserAddress(user.getId(),newAddress,newRemark);
        }else {
            userAddress = userAddressService.getUserAddressById(Integer.parseInt(addressId));
        }
        //生成订单
        Order order = orderService.payShoppingCart(user,userAddress.getAddress(),cart);
        request.setAttribute("order",order);
        clearCart(request,response);
        return "/pre/settlement/settlement3";
    }
    public ReturnResult checkCart(HttpServletRequest request) throws Exception{
        ReturnResult result = new ReturnResult();
        HttpSession session = request.getSession();
        ShoppingCart cart = getCartFromSession(request);
        for (ShoppingCartItem item : cart.getItems()){
            Product product  = productService.findById(item.getProduct().getId()+"");
            if(product.getStock()<item.getQuantity()){
                return result.returnFail(product.getName()+"商品数量不足");
            }
        }
        return result;
    }
    //清空购物车
    public ReturnResult clearCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReturnResult result = new ReturnResult();
        //结账后清空购物车
        request.getSession().removeAttribute("cart");
        result.returnSuccess(null);
        return result;
    }
}
