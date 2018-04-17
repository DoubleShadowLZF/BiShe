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
            // �������ݿ�
            conn = DriverManager.getConnection(url, "Double", "1234");
            // ����Statement����
            stmt = conn.createStatement();
            /**
             * Statement createStatement() ����һ�� Statement �������� SQL ��䷢�͵����ݿ⡣
             */
            // ִ�����ݿ��ѯ���
            rs = stmt.executeQuery(sql);
            /**
             * ResultSet executeQuery(String sql) throws SQLException ִ�и����� SQL
             * ��䣬����䷵�ص��� ResultSet ����
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
            System.out.println("���ݿ�����ʧ��");
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
		String sql =  "update tb_student set profession='���������?' where ID=1";
		ConnDB connDB = new ConnDB();
		int update = connDB.executeUpdate(sql);
		System.out.println(update);
	}

	@Test
	public void testClose() {
		fail("��δʵ��");
	}

}
