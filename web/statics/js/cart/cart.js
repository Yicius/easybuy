//结算 订单支付提醒
function settlement3() {
    //判断地址
    var addressId=$("input[name='selectAddress']:checked").val();
    var newAddress=$("input[name='address']").val();
    var newRemark=$("input[name='remark']").val();
    if(addressId=="" || addressId==null){
        showMessage("请选择收货地址");
        return;
    }else if(addressId=="-1"){
        if(newAddress=="" || newAddress==null){
            showMessage("请填写新的收货地址");
            return;
        }else if(newAddress.length<=2 || newAddress.length>=50){
            showMessage("收货地址为2-50个字符");
            return;
        }
    }
    $.ajax({
        url: contextPath + "/cartServlet",
        method: "post",
        data: {
            action: "settlement3",
            addressId:addressId,
            newAddress:newAddress,
            newRemark:newRemark
        },
        dataType:"HTML",
        success: function (jsonStr) {
            alert(11111111);
                $("#settlement").html(jsonStr);

        }
    });
}

// 结算 加载购物车列表
function settlement1(){
    $.ajax({
        url: contextPath + "/cartServlet",
        method: "post",
        data: {
            action: "settlement1"
        },
        success: function (jsonStr) {
            refreshCart();
            $("#settlement").html(jsonStr);
        }
    });
}
//结算 形成预备订单

function settlement2(){
    $.ajax({
        url: contextPath + "/cartServlet",
        method: "post",
        data: {
            action: "settlement2"
        },
        success: function (jsonStr) {
            $("#settlement").html(jsonStr);
        }
    });
}

function addFavourite(entityId) {
    $.ajax({
        url:contextPath+"/favorite",
        method:"post",
        data:{
            action:"addFavorite",
            id:entityId,
        },
        dataType:"JSON",
        success:function (result) {
            favouriteList();
        }
    })
}

function favouriteList() {

    $.ajax({
        url:contextPath+"/favorite",
        method:"post",
        data:{
            action:"favouriteList",
        },
        dataType:"html",
        success:function (result){
            $("#favoriteList").html(result);
        }
    })
}


//删除方法
function removeCart(entityId) {
    $.ajax({
        url:contextPath+"/cartServlet",
        method:"post",
        data:{
            action:"modifyCart",
            entityId:entityId,
            quantity:0
        },
        dataType:"JSON",
        success:function (result) {
            //状态判断
            if(result.status==1){
                settlement1();
            }else {
                showMessage(result.message);
            }
        }
    })
}


function subQuantity(obj,entityId) {
    var quantity = getPCount(obj) - 1;
    if(quantity==0){
        quantity=1;
    }
    modifyCart(entityId,quantity,obj);
}

function addQuantity(obj,entityId,stock) {
    var quantity =Number(getPCount(obj))+1;
    if(stock<quantity){
        showMessage("商品数量不足");
        return;
    }
    //进行商品数量的修改
    modifyCart(entityId,quantity,obj);
}
function modifyCart(entityId,quantity,obj) {
    $.ajax({
        url:contextPath+"/cartServlet",
        method:"post",
        data:{
            action:"modifyCart",
            entityId:entityId,
            quantity:quantity
        },
        dataType:"JSON",
        success:function (result) {
            //状态判断
            if(result.status==1){
                obj.parent().find(".car_ipt").val(quantity);
                settlement1();
            }else {
                showMessage(result.message);
            }
        }
    })
}


function addCart(entityId,quantity) {
    $.ajax({
        url:contextPath+"/cartServlet",
        method:"post",
        data:{
            action:"addItem",
            entityId:entityId,
            quantity:quantity
        },
        dataType:"JSON",
        success:function (result) {
            //状态判断
            if(result.status==1){
                showMessage("操作成功");
                refreshCart();
            }else {
                showMessage(result.message);
            }
        }
    })
}

function refreshCart() {
    $.ajax({
        url:contextPath+"/cartServlet",
        method:"post",
        data:{
            action:"refreshCart"
        },
        dataType:"html",
        success:function (jsonStr) {
            $("#searchBar").html(jsonStr);
        }
    })
}

function settlement1() {
    $.ajax({
        url:contextPath+"/cartServlet",
        method:"post",
        data:{
            action:"toSettlement1"
        },
        success:function (jsonStr) {
            $("#settlement").html(jsonStr);
        }
    })
}