package cn.syl.java.mysql;

import java.sql.*;

public class TestStatement {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("url","root","password");
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user where id = ?");
            preparedStatement.setInt(1,1);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
