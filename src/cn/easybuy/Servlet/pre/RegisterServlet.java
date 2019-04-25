package cn.easybuy.Servlet.pre;

import cn.easybuy.entity.User;
import cn.easybuy.service.User.UserService;
import cn.easybuy.service.User.UserServiceImpl;
import cn.easybuy.utils.*;
import cn.easybuy.Servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet" ,urlPatterns = "/register")
public class RegisterServlet extends AbstractServlet {
    private UserService userService;
    public void init(){
        userService = new UserServiceImpl();
    }

    @Override
    public Class getServletClass() {
        return RegisterServlet.class;
    }
        //跳转到注册页面
    public String toRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        return "/pre/register";
    }
    public ReturnResult saveUserToDatabase (HttpServletRequest request, HttpServletResponse response)throws Exception{
        ReturnResult result = new ReturnResult();
        User user= new User();
        String loginName = request.getParameter("loginName");
        User olderUser = userService.findUser(loginName);
        //判断oldUser是不为空
        if(EmptyUtils.isNotEmpty(olderUser)){
            result.returnFail("用户已经存在");
            return result;

        }
        String password = request.getParameter("password");
        String userName = request.getParameter("userName");
        String identityCode = request.getParameter("identityCode");
        String email = request.getParameter("email");
        String sex = request.getParameter("sex");
        String mobile = request.getParameter("mobile");
        user.setLoginName(loginName);
        user.setType(Constants.UserType.PRE);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setIdentityCode(identityCode);
        user.setSex(EmptyUtils.isEmpty(sex)?1:0);
        user.setPassword(SecurityUtils.md5Hex(password));
        user.setUserName(userName);
        result = checkUser(user);
        if(result.getStatus()==Constants.ReturnResult.SUCCESS){
            boolean flag = userService.sava(user);
            if(!flag){
                return  result.returnFail("注册失败");
            }else {
                return result.returnSuccess("注册成功");
            }
        }
        return  result;
    }

    public ReturnResult checkUser(User user){
        ReturnResult result = new ReturnResult();
        if(EmptyUtils.isNotEmpty(user.getMobile())){
            if (!RegUtils.checkMobile(user.getMobile())){
                return result.returnFail("手机号码格式不对");
            }
        }
        if(EmptyUtils.isNotEmpty(user.getIdentityCode())){
            if (!RegUtils.checkIdentityCodeReg(user.getIdentityCode())){
                return result.returnFail("身份证号码格式不对");
            }
        }
        if(EmptyUtils.isNotEmpty(user.getEmail())){
            if (!RegUtils.checkEmail(user.getEmail())){
                return result.returnFail("邮箱格式不对");
            }
        }
        return result.returnSuccess();
    }

}
