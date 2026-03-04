package com.ecommerce.entity;

public class Product {
    private Long id;                //商品id
    private String name;            //商品名称
    private Double price;           //商品价格
    private Integer stock;          //商品库存

    public Product() {}

    // 有参构造器（方便创建商品对象）
    public Product(Long id, String name, Double price, Integer stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }


    public Integer getStock() {return stock;}

    public void setStock(Integer stock) {this.stock = stock;}

    public Double getPrice() {return price;}

    public void setPrice(Double price) {this.price = price;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    //自动生成toString9
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
