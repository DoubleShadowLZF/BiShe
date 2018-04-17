package com.wgh.core; //�����ౣ�浽com.wgh.core����

//����java.sql���е�������
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnDB {
    public Connection conn = null; // ����Connection�����ʵ��
    public Statement stmt = null; // ����Statement�����ʵ��
    public ResultSet rs = null; // ����ResultSet�����ʵ��
    private static String dbClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//���屣�����ݿ������ı���
    private static String dbUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=db_TK";
    private static String dbUser = "Double";
    private static String dbPwd = "1234";
    public ConnDB() {   //���幹�췽��
        try {
            Class.forName(dbClassName);
        } catch (Exception ex) {
            System.out.println("���ݿ����ʧ��");
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
                    .println("����: DbConnectionManager.getConnection() ������ݿ�����ʧ��.\r\n\r\n��������:"
                            + dbClassName
                            + "\r\n����λ��:"
                            + dbUrl
                            + "\r\n�û�/����"
                            + dbUser + "/" + dbPwd);
        }
        return conn;
    }

    /*
     * ���ܣ�ִ�в�ѯ���
     */
    public ResultSet executeQuery(String sql) {
        try { // ��׽�쳣
            conn = getConnection(); // ����getConnection()��������Connection�����һ��ʵ��conn
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage()); // ����쳣��Ϣ
        }
        return rs; // ���ؽ��������
    }

    /*
     * ����:ִ�и��²���
     */
    public int executeUpdate(String sql) {
        int result = 0; // ���屣�淵��ֵ�ı���
        try { // ��׽�쳣
            conn = getConnection(); // ����getConnection()��������Connection�����һ��ʵ��conn
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            result = stmt.executeUpdate(sql); // ִ�и��²���
        } catch (SQLException ex) {
            result = 0; // �����淵��ֵ�ı�����ֵΪ0
        }
        return result; // ���ر��淵��ֵ�ı���
    }

    /*
     * ����:�ر����ݿ������
     */
    public void close() {
        try { // ��׽�쳣
            if (rs != null) { // ��ResultSet�����ʵ��rs��Ϊ��ʱ
                rs.close(); // �ر�ResultSet����
            }
            if (stmt != null) { // ��Statement�����ʵ��stmt��Ϊ��ʱ
                stmt.close(); // �ر�Statement����
            }
            if (conn != null) { // ��Connection�����ʵ��conn��Ϊ��ʱ
                conn.close(); // �ر�Connection����
            }
        } catch (Exception e) {
            e.printStackTrace(System.err); // ����쳣��Ϣ
        }
    }

}
