function login() {
    var loginName=$("#loginName").val();
    var password=$("#password").val();
    $.ajax({
        url:contextPath+"/Login",
        method:"post",
        data:{loginName:loginName,password:password,action:"login"},
        dataType:"json",
        success:function (data) {

            if(data.status==1){
                window.location.href=contextPath+"/Home?action=index";
            }else {
                showMessage(data.message);
            }
        }
    })
}