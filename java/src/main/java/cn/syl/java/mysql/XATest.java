package cn.syl.java.mysql;

import com.mysql.jdbc.jdbc2.optional.MysqlXAConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class XATest {
    public static void main(String[] args) throws Exception {
        Connection lotteryConnection = DriverManager.getConnection(
                "jdbc:mysql://39.106.39.129:3306/ct-lottery",
                "root",
                "1311150301");

        XAConnection aConnection = new MysqlXAConnection((com.mysql.jdbc.Connection) lotteryConnection,true);
        Connection creditConnection = DriverManager.getConnection(
                "jdbc:mysql://39.106.39.129:3306/ct-credit",
                "root",
                "1311150301");

        XAConnection bConnection = new MysqlXAConnection((com.mysql.jdbc.Connection) creditConnection,true);

        //创建RM
        XAResource lotteryRm = aConnection.getXAResource();
        XAResource creditRm = bConnection.getXAResource();

        byte[] gtrid = "g12345".getBytes();
        int formatId = 1;

        //开启lottery子事务
        byte[] sub1 = "b00001".getBytes();
        Xid xid = new MysqlXid(gtrid,sub1,formatId);

        lotteryRm.start(xid,XAResource.TMNOFLAGS);
        lotteryConnection.prepareStatement("select * from lottery_draw").execute();
        lotteryRm.end(xid,XAResource.TMSUCCESS);

        //开启credit子事务
        byte[] sub2 = "b00002".getBytes();
        Xid xid2 = new MysqlXid(gtrid,sub2,formatId);

        creditRm.start(xid2,XAResource.TMNOFLAGS);
        creditConnection.prepareStatement("select * from credit").execute();
        creditRm.end(xid2,XAResource.TMSUCCESS);

        // 2PC的阶段一：向两个库都发送prepare消息，执行事务中的SQL语句，但是不提交
        int lotteryPrepareResult = lotteryRm.prepare(xid);
        int creditPrepareResult = creditRm.prepare(xid2);

        // 2PC的阶段二：两个库都发送commit消息，提交事务
        // 如果两个库对prepare都返回ok，那么就全部commit，对每个库都发送commit消息，完成自己本地事务的提交
        if (lotteryPrepareResult == XAResource.XA_OK
                && creditPrepareResult == XAResource.XA_OK) {
            lotteryRm.commit(xid, false);
            creditRm.commit(xid2, false);
        }else {
            lotteryRm.rollback(xid);
            creditRm.rollback(xid2);
        }
    }
}
