<%-- 
    Document   : Logout
    Created on : Mar 11, 2025, 2:31:11?PM
    Author     : zainr
--%>

<%@ page session="true" %>
<%
    session.invalidate(); // Destroy session
    response.sendRedirect("Login.jsp?success=You have been logged out.");
%>
