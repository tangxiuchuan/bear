<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%

       String  callback=request.getParameter("callback");

       if(callback==null||callback.equals("")){
           //按照原来的方式返回
           out.print("{\"abc\":123}");
       }else{
           out.print(callback+"({\"abc\":123})");
       }



%>

