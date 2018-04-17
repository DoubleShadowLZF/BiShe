package com.wgh.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.wgh.core.ConnDB;

public class ConnDBTest {


	public static void main(String[] args) {
        Connection conn;
        Statement stmt;
        ResultSet rs;
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=db_TK;";
        String sql = "SELECT * FROM tb_Student where id=1";
        try {
            // 连接数据库
            conn = DriverManager.getConnection(url, "Double", "1234");
            // 建立Statement对象
            stmt = conn.createStatement();
            /**
             * Statement createStatement() 创建一个 Statement 对象来将 SQL 语句发送到数据库。
             */
            // 执行数据库查询语句
            rs = stmt.executeQuery(sql);
            /**
             * ResultSet executeQuery(String sql) throws SQLException 执行给定的 SQL
             * 语句，该语句返回单个 ResultSet 对象
             */
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String cardNo = rs.getString("cardNo");
                System.out.println("id:" + id + "\tName:" + name + "\tcardNo:" + cardNo);
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }
	}

	@Test
	public void testGetConnection() throws Exception {
		String sql =  "SELECT * FROM tb_Student where ID=1";
		ConnDB connDB = new ConnDB();
		ResultSet rs = connDB.executeQuery(sql);
		while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String cardNo = rs.getString("cardNo");
            String pwd  = rs.getString("pwd"); 
            pwd  = rs.getString(4); 
            System.out.println("id:" + id + "\tName:" + name + "\tcardNo:" + cardNo
            		+ "\tpwd:" + pwd);
        }
	}

	@Test
	public void testExecuteUpdate() throws Exception {
		String sql =  "update tb_student set profession='你的名字是?' where ID=1";
		ConnDB connDB = new ConnDB();
		int update = connDB.executeUpdate(sql);
		System.out.println(update);
	}

	@Test
	public void testClose() {
		fail("尚未实现");
	}

}
