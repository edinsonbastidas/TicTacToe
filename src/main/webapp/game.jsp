<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<jsp:useBean id="gameBean" scope="session" class="game.GameBean" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Tablero</title></head>
<body>
   <table border="10" style="border-collapse: collapse;">
    <c:forEach var="line" items="${gameBean.gridLines}">
        <tr>
            <c:forEach var="cell" items="${gameBean.getGridStatus(line)}">
                <td style="width: 60px; height: 60px; text-align: center;">
                    <c:choose>
                        <%-- Si la celda es X --%>
                        <c:when test="${cell.state == 'X'}">
                            <img src="img/state_x.png" alt="X" width="50" height="50"/>
                        </c:when>
                        
                        <%-- Si la celda es O --%>
                        <c:when test="${cell.state == 'O'}">
                            <img src="img/state_o.png" alt="O" width="50" height="50"/>
                        </c:when>
                        
                        <%-- Si la celda está vacía --%>
                        <c:otherwise>
                            <c:if test="${winner == null && gameBean.hasEmptyCell()}">
                                <a href="gameServlet?Line=${cell.line}&Col=${cell.col}">
                                    <img src="img/state_null.png" alt=" " width="50" height="50" border="0"/>
                                </a>
                            </c:if>
                            <c:if test="${winner != null || !gameBean.hasEmptyCell()}">
                                <img src="img/state_null.png" alt=" " width="50" height="50" style="opacity: 0.5;"/>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
    <c:if test="${winner != null}">
        <h2>¡Ganador: ${winner}!</h2>
        <a href="index.jsp">Reiniciar</a>
    </c:if>
    <c:if test="${winner != null}">
    <h2>¡El ganador es: ${winner}!</h2>
</c:if>

<c:if test="${winner == null && !gameBean.hasEmptyCell()}">
    <h2>¡Es un empate! No quedan movimientos.</h2>
</c:if>

<c:if test="${winner != null || !gameBean.hasEmptyCell()}">
    <form action="index.jsp" method="get">
        <input type="submit" value="Jugar de nuevo">
    </form>
</c:if>
</body>
</html>