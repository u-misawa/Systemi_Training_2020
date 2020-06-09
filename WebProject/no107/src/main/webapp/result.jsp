<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Hello, World!</title>
  </head>
  <body>
    <h3>画面遷移成功！</h3>
    <!-- リクエストにセットされたメッセージを出力 -->
    <h3>メッセージ1: ${message}</h3>
    <h3>メッセージ3: <%= request.getAttribute("message") %></h3>
    <h3></h3>
  </body>
</html>
