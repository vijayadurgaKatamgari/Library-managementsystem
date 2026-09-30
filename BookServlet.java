package com.library;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder html = new StringBuilder();

        String sql = "SELECT id,title,author,category,available_copies,total_copies FROM books ORDER BY title";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                html.append("<tr>")
                    .append("<td>").append(rs.getInt("id")).append("</td>")
                    .append("<td>").append(escape(rs.getString("title"))).append("</td>")
                    .append("<td>").append(escape(rs.getString("author"))).append("</td>")
                    .append("<td>").append(escape(rs.getString("category"))).append("</td>")
                    .append("<td>").append(rs.getInt("available_copies")).append("/")
                    .append(rs.getInt("total_copies")).append("</td>")
                    .append("<td><a class='btn small' href='issueBook.jsp?bookId=")
                    .append(rs.getInt("id")).append("'>Issue</a></td>")
                    .append("</tr>");
            }

            request.setAttribute("bookRows", html.toString());
            request.getRequestDispatcher("books.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Unable to load books.", e);
        }
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("&","&amp;").replace("<","&lt;")
                   .replace(">","&gt;").replace("\"","&quot;");
    }
}
