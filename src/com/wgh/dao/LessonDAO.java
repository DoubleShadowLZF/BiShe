package com.wgh.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.wgh.actionForm.LessonForm;
import com.wgh.core.ConnDB;

public class LessonDAO {
	private ConnDB conn=new ConnDB();
    //添加数据
    public int insert(LessonForm lessonForm) {
        String sql1="SELECT * FROM tb_Lesson WHERE Name='"+lessonForm.getName()+"'";
        ResultSet rs = conn.executeQuery(sql1);
        String sql = "";
        int falg = 0;
            try {
                if (rs.next()) {
                    falg=2;
                } else {
                    sql = "INSERT INTO tb_Lesson (Name) values('" +
                                 lessonForm.getName() + "')";
                    falg = conn.executeUpdate(sql);
                    System.out.println("添加课程时的SQL：" + sql);
                    conn.close();
                }
            } catch (Exception ex) {
                falg=0;
            }
        return falg;
    }
    //查询方法
    public List query(int id) {
    	List lessonList = new ArrayList();
        LessonForm lessonForm1 = null;
        String sql="";
        if(id==0){//查询全部课程
            sql = "SELECT * FROM tb_Lesson ORDER BY joinTime ";
        }else if(id==-1){//查询包括套题的课程
        	sql="SELECT distinct l.* FROM tb_Lesson l INNER JOIN tb_taoTi t ON l.id=t.LessonID";
        }else if(id==-2){	//查询包括考试题目的课程
        	sql="SELECT distinct l.* FROM tb_Lesson l INNER JOIN tb_Questions q ON l.id=q.LessonId WHERE name not in (SELECT distinct whichLesson FROM tb_StuResult)";
        	//sql="SELECT * FROM tb_Lesson WHERE ID in(SELECT distinct lessonID FROM (SELECT lessonId,taoTiId FROM tb_Questions GROUP BY taoTiId,lessonId,type) as lessonTaoTi GROUP BY lessonId,taoTiId HAVING COUNT(taoTiId) >1) WHERE name not in (SELECT distinct whichLesson FROM tb_StuResult";
        }else{
        	sql = "SELECT * FROM tb_Lesson WHERE ID=" +id+ "";
        }
        System.out.println("查询包括考试题目的课程："+sql);
        ResultSet rs = conn.executeQuery(sql);
        try {
            while (rs.next()) {
                lessonForm1 = new LessonForm();
                lessonForm1.setID(rs.getInt("id"));
                lessonForm1.setName(rs.getString("name"));
                lessonForm1.setJoinTime(java.text.DateFormat.getDateTimeInstance().parse(rs.getString("joinTime")));
                lessonList.add(lessonForm1);
            }
        } catch (Exception ex) {}
        return lessonList;
    }
    //查询方法
    public List query(String studentID) {
    	List lessonList = new ArrayList();
        LessonForm lessonForm1 = null;
        String sql="SELECT * FROM tb_Lesson WHERE ID in(SELECT distinct lessonID FROM " +
        		"(SELECT lessonId,taoTiId FROM tb_Questions GROUP BY taoTiId,lessonId,type)" +
        		" as lessonTaoTi GROUP BY lessonId,taoTiId HAVING COUNT(taoTiId) >1) AND" +
        		" name not in (SELECT distinct whichLesson FROM tb_StuResult WHERE stuId='"+studentID+"')";
        //String sql="SELECT distinct l.* FROM tb_lesson l INNER JOIN tb_questions q ON l.id=q.LessonId WHERE name not in (SELECT distinct whichLesson FROM tb_stuResult WHERE stuId='"+studentID+"')";
               ResultSet rs = conn.executeQuery(sql);
        try {
            while (rs.next()) {
                lessonForm1 = new LessonForm();
                lessonForm1.setID(rs.getInt(1));
                lessonForm1.setName(rs.getString(2));
                lessonList.add(lessonForm1);
            }
        } catch (Exception ex) {}
        return lessonList;
    }   

//    删除数据
        public int delete(LessonForm lessonForm) {
        	int flag=0;
        	String[] delId=lessonForm.getDelIdArray();
        	if (delId.length>0){
        		String id="";
        		for(int i=0;i<delId.length;i++){
        			id=id+delId[i]+",";
        		}
        		id=id.substring(0,id.length()-1);
                String sql = "DELETE FROM tb_Lesson where ID in (" + id +")";
                flag = conn.executeUpdate(sql);
                conn.close();
        	}else{
        		flag=0;
        	}
            return flag;
        }
}
