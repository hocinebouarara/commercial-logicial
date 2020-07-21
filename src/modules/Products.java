/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

/**
 *
 * @author hocin
 */
public class Products {

    String reference, designation, category;

    float buyingPrice, totalBuy, salePrice, totalSale;

    int id, quantity;

    public Products(int id,String reference, String designation,  int quantity, String category, float buyingPrice, float salePrice,float totalBuy,  float totalSale) {
        this.reference = reference;
        this.designation = designation;
        this.category = category;
        this.buyingPrice = buyingPrice;
        this.totalBuy = totalBuy;
        this.salePrice = salePrice;
        this.totalSale = totalSale;
        this.id = id;
        this.quantity = quantity;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(float buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public float getTotalBuy() {
        return totalBuy;
    }

    public void setTotalBuy(float totalBuy) {
        this.totalBuy = totalBuy;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public float getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(float totalSale) {
        this.totalSale = totalSale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    

}
