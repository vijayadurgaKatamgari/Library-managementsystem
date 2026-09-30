package com.library;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.html");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String method = request.getParameter("method");
        String reference = "PAY" + System.currentTimeMillis();

        String sql = "INSERT INTO payments(user_id,amount,payment_method,transaction_reference,status) " +
                     "VALUES(?,?,?,?, 'SUCCESS')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setDouble(2, amount);
            ps.setString(3, method);
            ps.setString(4, reference);
            ps.executeUpdate();

            response.sendRedirect("payment.jsp?success=true&ref=" + reference);
        } catch (SQLException e) {
            throw new ServletException("Payment failed.", e);
        }
    }
}
