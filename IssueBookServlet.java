package com.library;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/issueBook")
public class IssueBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.html");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        String check = "SELECT available_copies FROM books WHERE id=?";
        String issue = "INSERT INTO issued_books(user_id,book_id,issue_date,due_date,status) " +
                       "VALUES(?,?,CURDATE(),DATE_ADD(CURDATE(), INTERVAL 14 DAY),'ISSUED')";
        String update = "UPDATE books SET available_copies=available_copies-1 WHERE id=? AND available_copies>0";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(check)) {
                ps.setInt(1, bookId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next() || rs.getInt("available_copies") <= 0) {
                        response.sendRedirect("issueBook.jsp?error=Book not available");
                        return;
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(update)) {
                ps.setInt(1, bookId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(issue)) {
                ps.setInt(1, userId);
                ps.setInt(2, bookId);
                ps.executeUpdate();
            }

            con.commit();
            response.sendRedirect("dashboard.jsp?message=Book issued successfully");
        } catch (SQLException e) {
            throw new ServletException("Book issue failed.", e);
        }
    }
}
