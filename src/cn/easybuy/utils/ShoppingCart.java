package cn.easybuy.utils;

import cn.easybuy.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();
    private double sum;//总金额

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public ReturnResult addItem(Product product,Integer quantity){
        ReturnResult result = new ReturnResult();
        System.out.println(items.size());
        boolean flag= false;
        //判断购物车是否又此商品，如果有则累计数量
        for (int i = 0; i < items.size() ; i++) {
            if(items.get(i).getProduct().getId().equals(product)){
                 if(items.get(i).getQuantity()+quantity> product.getStock()){
                     return result.returnFail("商品数量不足");
                 }else {
                     items.get(i).setQuantity(items.get(i).getQuantity());
                     flag =true;
                 }
            }
        }
        if(!flag){
            items.add(new ShoppingCartItem(product,quantity));
        }
        return result.returnSuccess();
    }

    //移除一项
    public void removeItem(int index){
        items.remove(index);
    }
    //修改数量
    public void modifyQuantity(int index,Integer quantity){
        items.get(index).setQuantity(quantity);
    }

    public float getTotalCost(){
        float sum=0;
        for (ShoppingCartItem item:items) {
            sum = sum+item.getCost();
        }
        return sum;
    }
}
