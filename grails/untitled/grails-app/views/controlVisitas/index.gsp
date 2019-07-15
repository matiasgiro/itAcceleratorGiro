<%--
  Created by IntelliJ IDEA.
  User: mgiro
  Date: 2019-07-15
  Time: 11:57
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="miTemplate"/>
</head>

<body>

<table>
    <tr>
        <td>Titulo</td>
        <td>Subtitulo</td>
        <td>Ver</td>
        <td>Categoria</td>
    </tr>
    <g:each in="${lista}" var="item">
        <tr>
            <td>
                ${item.title}
            </td>
            <td>
                ${item.subtitle}
            </td>
            <td>

                <g:link action="save"  params="[title: item.title,subtitle:item.subtitle, category:item.categoty]">My Link!</g:link>


            </td>
            <td>
                ${item.categoty}
            </td>
        </tr>
    </g:each>
</table>
</body>
</html>
