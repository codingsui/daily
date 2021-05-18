package cn.syl.java.file;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileFormatter {


    public static void main(String[] args) throws Exception {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/suidada/code/git/daily/java/src/main/java/cn/syl/java/file/a.log"))));
        String str = null;
        List<Product> list = new ArrayList<>();
        String a = "INSERT INTO `kreditplus`.`pub_product_info`" +
                " ( `PRODUCT_CODE`, `PRODUCT_CODE_SUB`, `COST_RATE`, `PRODUCT_NAME`, `PRODUCT_RATE`, `PRE_RATE`, `SERVICE_RATE`, `OVERDUE_RATE`, `SERVICE_RATE_REMARK`, `INSURE_RATE`, `DEFAULT_RATE`, `DEFAULT_RATE_REMARK`, `FIXED_RATE`, `FIXED_RATE_REMARK`, `INCOME_RATE`, `product_term`, `PRODUCT_TERM_TYPE`, `PRODUCT_AMT_MIN`, `PRODUCT_AMT_MAX`, `RELOAN_AMT_MAX`, `PRODUCT_REMARK`, `IS_DISACOUNT`, `ACTIVITY_RULE`, `STATUS`, `PRODUCT_TYPE`, `CREATE_TIME`, `UPDATE_TIME`, `VERSION`, `OVERDUE_RATE_REMARK`, `EXTRA_FEE_CODE`, `FLOW`, `contract`, `APPLY_LOCATION`, `INVEST_LOCATION`, `loan_num_min`, `loan_num_max`, `loan_amount_incr`, `loan_amount_default`, `default_show`, `accrual_interest_method`, `min_accrual_interest_days`, `alternative`, `rank`) VALUES" +
                " ( '01', '%s', NULL, 'Dana Gampang', 0.8000000000, 0.0000000000, 0.00, 1.50, NULL, 0.40, 0.00, NULL, 0.00, NULL, 36.50, '%s', 'DAY', %s, %s, 600000.00, NULL, 0, '0,1000000,20000;1000000,2000000,40000;2000000,5000000,60000', '1', NULL, '2021-03-18 14:47:59', '2021-03-18 15:47:06', 0, NULL, 0, 0, NULL, NULL, '{\\\"kptSignPage\\\":\\\"7\\\",\\\"kptSignLX\\\":\\\"35\\\",\\\"kptSignLY\\\":\\\"175\\\",\\\"kptSignRX\\\":\\\"240\\\",\\\"kptSignRY\\\":\\\"235\\\",\\\"userSignPage\\\":\\\"7\\\",\\\"userSignLX\\\":\\\"275\\\",\\\"userSignLY\\\":\\\"175\\\",\\\"userSignRX\\\":\\\"480\\\",\\\"userSignRY\\\":\\\"235\\\"}', %s, %s, 100000.00, %s, 1, 'MAX_TERM', 0, '%s', '%s');";
        int code = 1100;
        while ((str = bufferedReader.readLine()) != null){
            String[] arrays = str.split("\t");
            Product p =  Product.builder().minLoan(arrays[1]).maxLoan(arrays[2]).fenliu(arrays[3]).risk(arrays[4]).term(arrays[5]).minAmt(arrays[6]).maxAmt(arrays[7]).productRate(arrays[10]).overRate(arrays[11]).build();
            System.out.println(String.format(a,code,p.getTerm(),p.getMinAmt(),p.getMaxAmt(),p.getMinLoan(),p.getMaxLoan(),p.getMinAmt(),p.getFenliu(),p.getRisk()));
            code++;
        }

    }
}
