<%--
  Created by IntelliJ IDEA.
  User: Xue
  Date: 2019/3/26
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="menu_bg">
    <div class="menu">
        <!--Begin 商品分类详情 Begin-->
        <div class="nav">
            <div class="nav_t">全部商品分类</div>

            <div class="leftNav">
                <ul>
                    <c:forEach items="${pcList}" var="pc1">
                        <li>
                            <div class="fj">
                                <span class="n_img"><span></span><img src="${ctx}/statics/images/nav1.png" /></span>
                                <span class="fl">${pc1.productCategory.name}</span>
                            </div>
                            <div class="zj">

                                    <div class="zj_l">
                                        <c:forEach items="${pc1.productCategoryVoList}" var="pc2">
                                        <div class="zj_l_c">
                                            <h2><a href="${ctx}/product?action=queryProductList&categoryId=${pc2.productCategory.id}">${pc2.productCategory.name}</a></h2>
                                            <c:forEach items="${pc2.productCategoryVoList}" var="pc3">
                                                <a href="${ctx}/product?action=queryProductList&categoryId=${pc3.productCategory.id}">${pc3.productCategory.name}</a>|
                                            </c:forEach>

                                        </div>
                                        </c:forEach>
                                    </div>


                                <div class="zj_r">
                                    <a href="#"><img src="${ctx}/statics/images/n_img1.jpg" width="236" height="200" /></a>
                                    <a href="#"><img src="${ctx}/statics/images/n_img2.jpg" width="236" height="200" /></a>
                                </div>
                            </div>
                        </li>
                    </c:forEach>

                </ul>
            </div>
        </div>
        <!--End 商品分类详情 End-->
        <ul class="menu_r">
            <li><a href="${ctx}/pre/index.jsp">首页</a></li>
            <c:forEach items="${pcList}" var="pcList">
                <li><a href="${ctx}/product?action=queryProductList&categoryId=${pcList.productCategory.id}">${pcList.productCategory.name}</a></li>
            </c:forEach>
        </ul>
        <div class="m_ad">中秋送好礼！</div>
    </div>
</div>
</body>
</html>
