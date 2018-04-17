package com.wgh.core; //将该类保存到com.wgh.core包中

//导入java.sql包中的所有类
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnDB {
    public Connection conn = null; // 声明Connection对象的实例
    public Statement stmt = null; // 声明Statement对象的实例
    public ResultSet rs = null; // 声明ResultSet对象的实例
    private static String dbClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//定义保存数据库驱动的变量
    private static String dbUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=db_TK";
    private static String dbUser = "Double";
    private static String dbPwd = "1234";
    public ConnDB() {   //定义构造方法
        try {
            Class.forName(dbClassName);
        } catch (Exception ex) {
            System.out.println("数据库加载失败");
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
        	//Class.forName(dbClassName);
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
            System.out.println("creatConnectionError!");
        } 
        if (conn == null) {
            System.err
                    .println("警告: DbConnectionManager.getConnection() 获得数据库链接失败.\r\n\r\n链接类型:"
                            + dbClassName
                            + "\r\n链接位置:"
                            + dbUrl
                            + "\r\n用户/密码"
                            + dbUser + "/" + dbPwd);
        }
        return conn;
    }

    /*
     * 功能：执行查询语句
     */
    public ResultSet executeQuery(String sql) {
        try { // 捕捉异常
            conn = getConnection(); // 调用getConnection()方法构造Connection对象的一个实例conn
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage()); // 输出异常信息
        }
        return rs; // 返回结果集对象
    }

    /*
     * 功能:执行更新操作
     */
    public int executeUpdate(String sql) {
        int result = 0; // 定义保存返回值的变量
        try { // 捕捉异常
            conn = getConnection(); // 调用getConnection()方法构造Connection对象的一个实例conn
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            result = stmt.executeUpdate(sql); // 执行更新操作
        } catch (SQLException ex) {
            result = 0; // 将保存返回值的变量赋值为0
        }
        return result; // 返回保存返回值的变量
    }

    /*
     * 功能:关闭数据库的连接
     */
    public void close() {
        try { // 捕捉异常
            if (rs != null) { // 当ResultSet对象的实例rs不为空时
                rs.close(); // 关闭ResultSet对象
            }
            if (stmt != null) { // 当Statement对象的实例stmt不为空时
                stmt.close(); // 关闭Statement对象
            }
            if (conn != null) { // 当Connection对象的实例conn不为空时
                conn.close(); // 关闭Connection对象
            }
        } catch (Exception e) {
            e.printStackTrace(System.err); // 输出异常信息
        }
    }

}
