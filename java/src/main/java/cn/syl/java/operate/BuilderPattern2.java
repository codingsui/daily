package cn.syl.java.operate;

public class BuilderPattern2 {

    public static void main(String[] args) {
        BuilderPattern2 p = BuilderPattern2.builder().p1("1").p3("p3").build();
        System.out.println(p);
    }
    private String p1;
    private String p2;
    private String p3;

    @Override
    public String toString() {
        return "BuilderPattern2{" +
                "p1='" + p1 + '\'' +
                ", p2='" + p2 + '\'' +
                ", p3='" + p3 + '\'' +
                '}';
    }

    public BuilderPattern2(ProductBuilder productBuilder) {
        this.p1 = productBuilder.p1;
        this.p2 = productBuilder.p2;
        this.p3 = productBuilder.p3;
    }

    interface Builder<T>{
        T build();
    }
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder implements Builder<BuilderPattern2>{
        private String p1;
        private String p2;
        private String p3;


        @Override
        public BuilderPattern2 build() {
            return new BuilderPattern2(this);
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

