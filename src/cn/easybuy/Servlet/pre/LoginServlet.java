package cn.easybuy.Servlet.pre;

import cn.easybuy.entity.User;
import cn.easybuy.service.User.UserService;
import cn.easybuy.service.User.UserServiceImpl;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.ReturnResult;
import cn.easybuy.utils.SecurityUtils;
import cn.easybuy.Servlet.AbstractServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Login",urlPatterns = "/Login")
public class LoginServlet extends AbstractServlet {
     //注入用户业务类
    private UserService userService;
    public void init(){
        userService = new UserServiceImpl();
    }

    @Override
    public Class getServletClass() {
        return LoginServlet.class;
    }

    public String toLogin(HttpServletRequest request, HttpServletResponse response)throws Exception{
        return "/pre/login";
    }
    public ReturnResult login(HttpServletRequest request, HttpServletResponse response) throws  Exception{
        ReturnResult returnResult = new ReturnResult();
        //参数获取
        String loginName=request.getParameter("loginName");
        String password = request.getParameter("password");
        User user = userService.findUser(loginName);
        if(EmptyUtils.isEmpty(user)){
            returnResult.returnFail("用户不存在");
        }else {

            if(user.getPassword().equals(SecurityUtils.md5Hex(password))){
                request.getSession().setAttribute("loginUser",user);
                returnResult.returnSuccess("登陆成功");
            }else {
                returnResult.returnFail("密码错误");
            }
        }
        Cookie cookie1=new Cookie("id",loginName);
        Cookie cookie2=new Cookie("password",password);
        cookie1.setMaxAge(5*60);
        cookie2.setMaxAge(5*60);
        response.addCookie(cookie1);
        response.addCookie(cookie2);

        return returnResult;
    }

    public String loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ReturnResult result = new ReturnResult();
        request.getSession().removeAttribute("loginUser");
        result.returnSuccess("注销成功");
        return "/pre/login";
    }
}
