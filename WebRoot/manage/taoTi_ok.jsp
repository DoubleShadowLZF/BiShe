<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*,java.util.*" errorPage="" %>
<html>
<head>
<title>�����ɹ�!</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>

<body>
<%int para=Integer.parseInt(request.getParameter("para"));
switch(para){
	case 1:
	%>
		<script language="javascript">
		alert("������Ϣ��ӳɹ�!");
		window.location.href="taoTi.do?action=taoTiQuery";
		</script>	
	<%	break;
	case 2:
	%>
		<script language="javascript">
		alert("������Ϣ�޸ĳɹ�!");
		window.location.href="taoTi.do?action=taoTiQuery";
		</script>		
	<%	break;
	case 3:
	%>
		<script language="javascript">
		alert("������Ϣɾ���ɹ�!");
		window.location.href="taoTi.do?action=taoTiQuery";
		</script>		
	<%	break;
	case 4:
		List<String> idsList = (List<String>) request.getAttribute("ids");
		String strIds = null;
		if( idsList.size() > 0){
			for(String str : idsList){
				strIds += str+",";
			}
			strIds = strIds.substring(0,strIds.length()-1);
		}
	%>
		<script language="javascript">
		var ids = <%=strIds %>;
		if(ids != null ){
			alert("����"+ids+",����word�Ծ����ɳɹ�!");
		}else{
			alert("����word�Ծ�ɹ�!");
		}
		window.location.href="wordPaper.do?action=wordPaperQuery";
		</script>		
	<%	break;
	case 5:
		List<String> idsDelList = (List<String>) request.getAttribute("idsDel");
		String strIdsDel = null;
		if(idsDelList.size() > 0){
			for(String str : idsDelList){
				strIdsDel += str+",";
			}
			strIdsDel = strIdsDel.substring(0,strIdsDel.length()-1);
		}
	%>
		<script language="javascript">
		var idsDel = <%=strIdsDel %>;
		if(idsDel != null){
			alert("����"+idsDel+",����word�Ծ�ɾ���ɹ�!");
		}else{
			alert("ɾ��word�Ծ�ɹ�!");
		}
		window.location.href="wordPaper.do?action=wordPaperQuery";
		</script>		
	<%	break;
}
%>
</body>
</html>