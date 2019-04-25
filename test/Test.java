import cn.easybuy.entity.User;
import cn.easybuy.service.User.UserService;
import cn.easybuy.service.User.UserServiceImpl;

public class Test {

    public void testregist(){
        UserService userService=new UserServiceImpl();
        User user= new User();
        user.setLoginName("qwe12r");
        user.setPassword("1234456");
        user.setUserName("qqqq");
        user.setSex(Integer.parseInt("0"));
        user.setIdentityCode("1111111111");
        user.setMobile("1234567");
        boolean b = userService.sava(user);
        if (b){
            System.out.println("success");
        }else {
            System.out.println("fail");
        }
    }


    public static void main(String[] args) {
        Test t = new Test();
        t.testregist();
    }


}
