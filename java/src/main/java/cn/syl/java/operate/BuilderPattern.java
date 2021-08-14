package cn.syl.java.operate;

import lombok.Data;

public class BuilderPattern {

    public static void main(String[] args) {
        Builder builder = new ConBuilder();
        Director director = new Director(builder);
        Product1 product = director.construct();
        System.out.println(product);
    }
}
@Data
class Product1{
    private String partA;
    private String partB;
    private String partC;

    @Override
    public String toString() {
        return "Product{" +
                "partA='" + partA + '\'' +
                ", partB='" + partB + '\'' +
                ", partC='" + partC + '\'' +
                '}';
    }
}
abstract class  Builder{
    abstract Builder setA();
    abstract Builder setB();
    abstract Builder setC();

    abstract Product1 getResult();
}
class ConBuilder extends   Builder{

    private Product1 product = new Product1();


    @Override
    public Builder setA() {
        product.setPartA("A");
        return this;
    }

    @Override
    public Builder setB() {
        product.setPartB("B");
        return this;
    }

    @Override
    public Builder setC() {
        product.setPartC("C");
        return this;
    }

    @Override
    public Product1 getResult() {
        return product;
    }
}

class Director{
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public Product1 construct(){
        builder.setA();
        builder.setB();
        builder.setC();

        return builder.getResult();
    }
}