<%--
  Created by IntelliJ IDEA.
  User: mgiro
  Date: 2019-07-15
  Time: 12:24
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
    <g:form action="save">
        <label for="nombre">Nombre</label>
        <g:textField name="nombre" value="${persona.nombre}" />

        <g:submitButton name="create" value="Save"/>
    </g:form>
</body>
</html>