package com.wgh.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.wgh.actionForm.LessonForm;
import com.wgh.actionForm.QuestionsForm;
import com.wgh.core.ConnDB;

public class StartExamDAO {
	private ConnDB conn=new ConnDB();
	private LessonDAO lessonDAO=new LessonDAO();
    //�����ȡ����
    public int randomGetQuestion(int lessonID){
    	int questionsID=0;
    	String sql="SELECT taotiId FROM (SELECT distinct lessonId,taotiId from " +
    			"(SELECT lessonId,taotiId FROM tb_Questions GROUP BY taotiId,lessonId,type)" +
    			" as lessonTaoTi GROUP BY lessonId,taotiId having count(taotiId) >1)as temp" +
    			" WHERE lessonId="+lessonID+"";
    	ResultSet rs = conn.executeQuery(sql);
    	int i=0;
        try {
        	rs.last();
        	int recordNum=rs.getRow();
        	rs.first();
        	int[] id=new int[recordNum];
            do {
                id[i]=rs.getInt("id");
                i++;
            }while (rs.next());
            int rand=Math.abs(new Random().nextInt(id.length));
            questionsID=id[rand];
        } catch (Exception ex) {
        	ex.printStackTrace();
        }    	
    	return questionsID;
    }
    //�տ�ʼ����ʱ���濼�Խ��
    public int startSaveResult(String studentID,int lessonID){
    	String lesson=((LessonForm)lessonDAO.query(lessonID).get(0)).getName();
    	String sql="INSERT INTO tb_StuResult (id,whichLesson,resSingle,resMore) values('"+studentID+"','"+lesson+"',0,0)";
    	System.out.println("�տ�ʼ����ʱ���濼�Խ����SQL��䣺"+sql);
    	int ret=conn.executeUpdate(sql);
    	return ret;
    }
    //���Խ����󱣴濼�Խ��
    public int saveResult(String studentID,int lessonID,int resSingle,int resMore){
    	String lesson=((LessonForm)lessonDAO.query(lessonID).get(0)).getName();
    	String sql="UPDATE tb_StuResult set resSingle="+resSingle+",resMore="+resMore+" WHERE id='"+studentID+"' AND whichLesson='"+lesson+"'";
    	System.out.println(sql);
    	int ret=conn.executeUpdate(sql);
    	return ret;
    }   
    public List queryExam(int questionsID,int flag){
    	List questionsList = new ArrayList();
        QuestionsForm questionsForm1 = null;
        String sql="";
        if(flag==0){
            sql = "SELECT * FROM tb_Questions WHERE taotiId="+questionsID+" AND type='��ѡ��'";
        }else{
        	sql = "SELECT * FROM tb_Questions WHERE taotiId="+questionsID+" AND type='��ѡ��'";
        }
        ResultSet rs = conn.executeQuery(sql);
        String type="";
        int id=0;
        try {
            rs.last();
            int recordNum=rs.getRow();
            rs.first();
            int[] idArr=new int[recordNum];
            for(int i=0;i<recordNum;i++) {
                questionsForm1 = new QuestionsForm();
                id=rs.getInt("id");
                questionsForm1.setID(id);
                questionsForm1.setSubject(rs.getString("subject"));
                type=rs.getString(3);
                questionsForm1.setType(type);
                questionsForm1.setLessonId(rs.getInt("lessonId"));
                questionsForm1.setTaoTiId(rs.getInt("taoTiId"));
                questionsForm1.setOptionA(rs.getString("optionA"));
                questionsForm1.setOptionB(rs.getString("optionB"));
                questionsForm1.setOptionC(rs.getString("optionC"));
                questionsForm1.setOptionD(rs.getString("optionD"));
                if(type.equals("��ѡ��")){
                	String[] ans=rs.getString("answer").split(",");
                	questionsForm1.setAnswerArr(ans);
                	idArr[i]=id;
                	questionsForm1.setIdArrM(idArr);
                }else{
                	questionsForm1.setAnswer(rs.getString(11));
                	idArr[i]=id;
                	questionsForm1.setIdArrS(idArr);
                }
                questionsForm1.setNote(rs.getString("note"));
                
                questionsList.add(questionsForm1);
                rs.next();
            }
        } catch (Exception e) {
        	e.printStackTrace();			//����쳣��Ϣ
        }        
        return questionsList;
    }
    public String getRightAnswer(int id){
    	String answer="";
    	String sql = "SELECT * FROM tb_Questions WHERE id="+id+"";
    	System.out.println("��ȡ����ȷ��ʱ��SQL��䣺"+sql);
    	ResultSet rs = conn.executeQuery(sql);
        try {
            if (rs.next()) {
                answer=rs.getString(12);
            }
        } catch (Exception ex) {
        	System.out.println("��ȡ��ȷ��ʱ�����Ĵ�����Ϣ��"+ex.getMessage());
        } 
    	return answer;
    }
}
