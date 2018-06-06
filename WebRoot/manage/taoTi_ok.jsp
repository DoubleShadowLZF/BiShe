<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*,java.util.*" errorPage="" %>
<html>
<head>
<title>操作成功!</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>

<body>
<%int para=Integer.parseInt(request.getParameter("para"));
switch(para){
	case 1:
	%>
		<script language="javascript">
		alert("套题信息添加成功!");
		window.location.href="taoTi.do?action=taoTiQuery";
		</script>	
	<%	break;
	case 2:
	%>
		<script language="javascript">
		alert("套题信息修改成功!");
		window.location.href="taoTi.do?action=taoTiQuery";
		</script>		
	<%	break;
	case 3:
	%>
		<script language="javascript">
		alert("套题信息删除成功!");
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
			alert("除了"+ids+",其他word试卷生成成功!");
		}else{
			alert("生成word试卷成功!");
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
			alert("除了"+idsDel+",其他word试卷删除成功!");
		}else{
			alert("删除word试卷成功!");
		}
		window.location.href="wordPaper.do?action=wordPaperQuery";
		</script>		
	<%	break;
}
%>
</body>
</html>