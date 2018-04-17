package com.wgh.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.wgh.actionForm.QueryResultIfForm;
import com.wgh.actionForm.StuResultForm;
import com.wgh.core.ConnDB;

public class StuResultDAO {
	private ConnDB conn=new ConnDB();
    //查询方法
    public List query(String stuId) {
    	List stuResultList = new ArrayList();
        StuResultForm stuResultForm1 = null;
        String sql="";
        if(stuId.equals("")){
            sql = "SELECT * FROM tb_StuResult ORDER BY joinTime DESC";
        }else{
        	sql = "SELECT * FROM tb_StuResult WHERE stuId='" +stuId+ "'";
        }
        ResultSet rs = conn.executeQuery(sql);
        try {
            while (rs.next()) {
                stuResultForm1 = new StuResultForm();
                stuResultForm1.setID(rs.getInt("id"));
                stuResultForm1.setStuId(rs.getString("stuId"));
                stuResultForm1.setWhichLesson(rs.getString("whichLesson"));
                stuResultForm1.setResSingle(rs.getInt("resSingle"));
                stuResultForm1.setResMore(rs.getInt("resMore"));
                stuResultForm1.setResTotal(rs.getInt("resTotal"));
                stuResultForm1.setJoinTime(java.text.DateFormat.getDateTimeInstance().parse(rs.getString("joinTime")));               
                stuResultList.add(stuResultForm1);
            }
        } catch (Exception ex) {
        	System.out.println("查询学生成绩(全部和按准考证精确查询)时产生的错误："+ex.getMessage());
        }
        return stuResultList;
    }
    public List query(QueryResultIfForm q){
    	List stuResultList = new ArrayList();
        StuResultForm stuResultForm1 = null;
        String sql="SELECT * FROM tb_StuResult WHERE "+q.getQueryIf()+" like '%"+q.getKey()+"%'";
        System.out.println("SQL："+sql);
        ResultSet rs = conn.executeQuery(sql);
        try {
            while (rs.next()) {
                stuResultForm1 = new StuResultForm();
                stuResultForm1.setID(rs.getInt("id"));
                stuResultForm1.setStuId(rs.getString("stuId"));
                stuResultForm1.setWhichLesson(rs.getString("whichLesson"));
                stuResultForm1.setResSingle(rs.getInt("ResSingle"));
                stuResultForm1.setResMore(rs.getInt("resMore"));
                stuResultForm1.setResTotal(rs.getInt("resTotal"));
                stuResultForm1.setJoinTime(java.text.DateFormat.getDateTimeInstance().parse(rs.getString("joinTime")));               
                stuResultList.add(stuResultForm1);
            }
        } catch (Exception ex) {
        	System.out.println("带条件查询学生成绩时产生的错误："+ex.getMessage());
        }
        return stuResultList;
    }

}
