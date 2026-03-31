java
package com.grievance.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

// Called when user clicks Logout button
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Destroy the session completely
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Send user back to login page
        res.sendRedirect("index.html");
    }
}
