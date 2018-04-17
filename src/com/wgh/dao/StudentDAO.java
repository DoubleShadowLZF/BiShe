package com.wgh.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.wgh.actionForm.StudentForm;
import com.wgh.core.ChStr;
import com.wgh.core.ConnDB;
public class StudentDAO {
	private ConnDB conn=new ConnDB();
	private ChStr chStr=new ChStr();
    //���������֤
    public int checkStudent(StudentForm studentForm) {
        int flag = 1;
        String sql = "SELECT * FROM tb_Student where ID='" +
                     studentForm.getID()+ "'";
        ResultSet rs = conn.executeQuery(sql);
        try {
            if (rs.next()) {
                String pwd = studentForm.getPwd();
                String btPwd = rs.getString("pwd");
                if (pwd.equals(btPwd)) {
                    rs.last();
                    int rowSum = rs.getRow();	//��ȡ��¼����
                    rs.first();
                    if (rowSum!=1) {
                        flag = 1;
                        System.out.print("��ȡrow��ֵ��" + sql + rowSum);
                    }
                } else {
                    flag = 2;
                }
            }else{
                flag = 2;
            }
        } catch (Exception ex) {
            flag = 2;
            System.out.println(ex.getMessage());
        }
        return flag;
    }

    //�������
    public String insert(StudentForm s) {
        String sql1="SELECT * FROM tb_Student WHERE cardNo='"+s.getCardNo()+"'";
        ResultSet rs = conn.executeQuery(sql1);                         //ִ��SQL��ѯ���
        String sql = "";
        String falg = "miss";                                           //���ڼ�¼������Ϣ�ı���
        String ID="";
            try {
                if (rs.next()) {                                            //������ڼ�¼
                    falg="re";                                      //��ʾ������Ϣ�Ѿ�ע��
                } 
                else {
                    /*****************�Զ�����׼��֤��***********************************************/
                    String sql_max="SELECT * FROM tb_Student order by joinTime DESC ";
                    ResultSet rs_max=conn.executeQuery(sql_max);//��ѯ����׼��֤��
                    java.util.Date date=new java.util.Date();               //ʵ����java.util.Date()��
                    String newTime=new SimpleDateFormat("yyyyMMdd").format(date);//��ʽ����ǰ����
                    if(rs_max.next()){
                        String max_ID=rs_max.getString("id"); //��ȡ����׼��֤��
                        System.out.println(max_ID);
                        int newId=Integer.parseInt(max_ID.substring(6,10))+1;//ȡ�����׼��֤���е����ֱ��+1
                        System.out.println(newId);
                        String no=chStr.formatNO(newId,6);//�����ɵı�Ÿ�ʽ��Ϊ6λ
                        ID="CN"+newTime+no;                     //���������׼��֤��
                    }else{                                          //����һ������ע��ʱ
                        ID="CN"+newTime+"000001";                   //���ɵ�һ��׼��֤��
                    }
                    /********************************************************************************/
                    sql = "INSERT INTO tb_Student (ID,name,pwd,sex,question,answer,profession,cardNo) values('" +
                                 ID+ "','" +s.getName() +"','"+s.getPwd()+"','"+s.getSex()+"','"+s.getQuestion()+
                                 "','"+s.getAnswer()+"','"+s.getProfession()+"','"+s.getCardNo()+"')";
                    System.out.println(sql);
                    int ret= conn.executeUpdate(sql);                   //���濼��ע����Ϣ
                    if(ret==0){
                        falg="miss";                                    //��ʾ����ע��ʧ��
                    }else{
                        falg="��ϲ����ע��ɹ�!\\r���ס����׼��֤�ţ�"+ID;   //�������ɵ�׼��֤��
                    }
                    conn.close();                                           //�ر����ݿ�����
                }
            } catch (Exception e) {
                falg="miss";
                System.out.println("��ӿ�����Ϣʱ�Ĵ�����Ϣ��"+e.getMessage()); //���������ʾ��Ϣ������̨
            }
        return falg;
    }


    //��ѯ����
    public List query(String id) {
    	List studentList = new ArrayList();
        StudentForm studentForm1 = null;
        String sql="";
        if(id==null ||id.equals("")){
            sql = "SELECT * FROM tb_Student ORDER BY joinTime DESC";
        }else{
        	sql = "SELECT * FROM tb_Student WHERE ID='" +id+ "'";
        }
        ResultSet rs = conn.executeQuery(sql);
        try {
            while (rs.next()) {
                studentForm1 = new StudentForm();
                studentForm1.setID(rs.getString("id"));
                studentForm1.setName(rs.getString("name"));
                studentForm1.setPwd(rs.getString("pwd"));
                studentForm1.setSex(rs.getString("sex"));
                String time = rs.getString("joinTime"); 
                studentForm1.setJoinTime(java.text.DateFormat.getDateTimeInstance().parse(time));
                studentForm1.setQuestion(rs.getString("question"));
                studentForm1.setAnswer(rs.getString("answer"));
                studentForm1.setProfession(rs.getString("profession"));
                studentForm1.setCardNo(rs.getString("cardNo"));
                studentList.add(studentForm1);
            }
        } catch (Exception ex) {}
        return studentList;
    }
    //�޸Ŀ�������
    public int update(StudentForm s){
        String sql="UPDATE tb_Student SET pwd='"+s.getPwd()+"',sex='"+s.getSex()+"',question='"+s.getQuestion()+"',answer='"+s.getAnswer()+"',profession='"+s.getProfession()+"' where ID='"+s.getID()+"'";
        int ret=conn.executeUpdate(sql);
        System.out.println("�޸Ŀ�������ʱ��SQL��"+sql);
        conn.close();
        return ret;
    }
//�һ����루��һ����
    public StudentForm seekPwd1(StudentForm s){
    	String sql="SELECT * FROM tb_Student WHERE ID='"+s.getID()+"'";
        ResultSet rs = conn.executeQuery(sql);
            try {
                if (rs.next()) {
                    s.setID(rs.getString("id"));
                    s.setQuestion(rs.getString("question"));
                }else{
                s.setID("");
                }
            }catch(Exception e){
            	System.out.println("�һ����루��һ�������ֵĴ�����Ϣ��"+e.getMessage());
            }
            return s;
    }
//  �һ����루�ڶ�����
    public StudentForm seekPwd2(StudentForm s){
    	String sql="SELECT * FROM tb_Student WHERE ID='"+s.getID()+"'";
    	System.out.println("SQL"+sql);
        ResultSet rs = conn.executeQuery(sql);
        try {
            if (rs.next()) {
                	String ID=rs.getString(1);
                	String pwd=rs.getString(3);
                	String answer=rs.getString(7);
                	if(answer.equals(s.getAnswer())){
                		s.setID(ID);
                		s.setPwd(pwd);
                		System.out.println("���룺"+pwd);
                	}else{
                		s.setID("");
                	}
                }
            }catch(Exception e){
            	System.out.println("�һ����루�ڶ��������ֵĴ�����Ϣ��"+e.getMessage());
            }
            return s;
    }
//    ɾ������
        public int delete(StudentForm studentForm) {
        	int flag=0;
        	String[] delId=studentForm.getDelIdArray();
        	if (delId.length>0){
        		String id="'";
        		for(int i=0;i<delId.length;i++){
        			id=id+delId[i]+"','";
        		}
        		id=id.substring(0,id.length()-2);
                String sql = "DELETE FROM tb_Student where ID in (" + id +")";
                System.out.println("ɾ��ʱ��SQL��"+sql);
                flag = conn.executeUpdate(sql);
                conn.close();
        	}else{
        		flag=0;
        	}
            return flag;
        }
}
