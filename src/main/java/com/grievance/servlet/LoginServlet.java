java
package com.grievance.servlet;

import com.grievance.dao.UserDAO;
import com.grievance.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

// This URL is called from index.html login button
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("application/json");

        // Get email and password from the HTML form
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Check in database using UserDAO (Member 3's file)
        UserDAO dao = new UserDAO();
        User user = dao.login(email, password);

        if (user != null) {
            // Login success — save user info in session
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("role", user.getRole());

            // Send success response to browser
            res.getWriter().write(
                "{\"success\":true," +
                "\"role\":\"" + user.getRole() + "\"," +
                "\"name\":\"" + user.getName() + "\"}"
            );
        } else {
            // Login failed
            res.getWriter().write(
                "{\"success\":false," +
                "\"message\":\"Invalid email or password\"}"
            );
        }
    }
}

