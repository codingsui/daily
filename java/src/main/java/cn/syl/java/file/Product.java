package cn.syl.java.file;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private String minLoan;

    private String maxLoan;

    private String fenliu;

    private String risk;

    private String term;

    private String minAmt;

    private String maxAmt;

    private String tep;

    private String serverRate;

    private String productRate;

    private String overRate;

    @Override
    public String toString() {
        return "Product{" +
                "minLoan='" + minLoan + '\'' +
                ", maxLoan='" + maxLoan + '\'' +
                ", fenliu='" + fenliu + '\'' +
                ", risk='" + risk + '\'' +
                ", term='" + term + '\'' +
                ", minAmt='" + minAmt + '\'' +
                ", maxAmt='" + maxAmt + '\'' +
                ", tep='" + tep + '\'' +
                ", serverRate='" + serverRate + '\'' +
                ", productRate='" + productRate + '\'' +
                ", overRate='" + overRate + '\'' +
                '}';
    }
}
