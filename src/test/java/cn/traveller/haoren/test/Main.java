package cn.traveller.haoren.test;

import java.sql.*;

/**
 * 链接数据库
 */
public class Main {
    public static void main(String[] args) {
        // JDBC URL, 用户名和密码
        String url = "jdbc:mysql://localhost:3306/mydatabase"; // 替换为你的数据库URL
        String user = "root";  // 替换为你的数据库用户名
        String password = "password";  // 替换为你的数据库密码

        // SQL 查询语句
        String query = "SELECT * FROM users";  // 假设数据库有一个 users 表

        // 1. 加载数据库驱动（MySQL 驱动）
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // MySQL 8+ 驱动类

            // 2. 建立数据库连接
            Connection connection = DriverManager.getConnection(url, user, password);

            // 3. 创建 Statement 对象
            Statement statement = connection.createStatement();

            // 4. 执行查询
            ResultSet resultSet = statement.executeQuery(query);

            // 5. 处理结果
            while (resultSet.next()) {
                // 假设 users 表有 id 和 name 字段
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                // 输出结果
                System.out.println("ID: " + id + ", Name: " + name);
            }

            // 6. 关闭资源
            resultSet.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动加载失败：" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("数据库连接或操作失败：" + e.getMessage());
        }
    }
}
