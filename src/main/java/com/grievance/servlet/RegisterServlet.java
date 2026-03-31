java
package com.grievance.servlet;

import com.grievance.dao.UserDAO;
import com.grievance.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

// This URL is called from index.html register button
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("application/json");

        // Get details from registration form
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Basic validation
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            res.getWriter().write(
                "{\"success\":false,\"message\":\"All fields are required\"}"
            );
            return;
        }

        // Create user object and save to database
        User u = new User();
        u.setName(name.trim());
        u.setEmail(email.trim());
        u.setPassword(password);

        UserDAO dao = new UserDAO();
        boolean success = dao.register(u);

        if (success) {
            res.getWriter().write(
                "{\"success\":true," +
                "\"message\":\"Account created! Please login.\"}"
            );
        } else {
            res.getWriter().write(
                "{\"success\":false," +
                "\"message\":\"Email already exists. Try another.\"}"
            );
        }
    }
}
