package cn.easybuy.Servlet;

import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.ReturnResult;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@WebServlet(name = "AbstractServlet")
public abstract class AbstractServlet extends HttpServlet {
    public abstract Class getServletClass();//获取到继承了这个抽象类的子类的class实例


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        Method method = null;//反射的是
        Object result = null;
        if(EmptyUtils.isEmpty(action)){
            request.getRequestDispatcher("/pre/index.jsp").forward(request,response);
        }else {
            try {
                method =getServletClass().getDeclaredMethod(action,HttpServletRequest.class,HttpServletResponse.class);
                result = method.invoke(this,request,response);//this是当前类的实例对象，继承了这个抽象类的子类的对象实例
                toView(request,response,result);
            } catch (NoSuchMethodException e) {
                String viewName="404.jsp";
                request.getRequestDispatcher(viewName).forward(request,response);
                e.printStackTrace();
            }catch (Exception e) {
                if(!EmptyUtils.isEmpty(result)){
                    if(result instanceof String){
                        String viewName="500.jsp";
                        request.getRequestDispatcher(viewName).forward(request,response);
                    }else {
                        ReturnResult returnR = new ReturnResult();
                        returnR.returnFail("系统错误");
                    }
                }else {
                    String viewName="500.jsp";
                    request.getRequestDispatcher(viewName).forward(request,response);
                }
                e.printStackTrace();
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
    //处理结果
    public void toView(HttpServletRequest request, HttpServletResponse response,Object result) throws Exception {
        if(result instanceof String){
            String viewName = result.toString()+".jsp";

            request.getRequestDispatcher(viewName).forward(request,response);

        }else{
            write(result,response);
        }
    }


    public void write(Object obj,HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        String json = JSONObject.toJSONString(obj);
        PrintWriter printWriter = null;
        if (response!=null){
            try {
                printWriter = response.getWriter();
                printWriter.print(json);
                printWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                printWriter.close();
            }

        }
    }
}
