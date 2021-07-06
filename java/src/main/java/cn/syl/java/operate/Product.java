package cn.syl.java.operate;

public class Product {

    public static void main(String[] args) {
        Product p = Product.builder().p1("1").p3("p3").build();
        System.out.println(p);
    }
    private String p1;
    private String p2;
    private String p3;

    @Override
    public String toString() {
        return "Product{" +
                "p1='" + p1 + '\'' +
                ", p2='" + p2 + '\'' +
                ", p3='" + p3 + '\'' +
                '}';
    }

    public Product(ProductBuilder productBuilder) {
        this.p1 = productBuilder.p1;
        this.p2 = productBuilder.p2;
        this.p3 = productBuilder.p3;
    }


    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder{
        private String p1;
        private String p2;
        private String p3;


        public Product build() {
            return new Product(this);
        }

        public ProductBuilder p1(String p1){
            this.p1 = p1;
            return this;
        }
        public ProductBuilder p2(String p2){
            this.p2 = p2;
            return this;
        }
        public ProductBuilder p3(String p3){
            this.p3 = p3;
            return this;
        }

    }


}

