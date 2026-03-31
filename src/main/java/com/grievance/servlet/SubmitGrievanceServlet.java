java
package com.grievance.servlet;

import com.grievance.dao.GrievanceDAO;
import com.grievance.model.Grievance;
import com.grievance.nlp.GrievanceClassifier;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

// This URL is called from submit.html
@WebServlet("/submitGrievance")
public class SubmitGrievanceServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("application/json");

        // Step 1: Check if user is logged in
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.getWriter().write(
                "{\"success\":false,\"message\":\"Please login first\"}"
            );
            return;
        }

        // Step 2: Get data from the form
        int userId = (int) session.getAttribute("userId");
        String title = req.getParameter("title");
        String description = req.getParameter("description");

        // Step 3: Validate input
        if (title == null || title.trim().isEmpty() ||
            description == null || description.trim().isEmpty()) {
            res.getWriter().write(
                "{\"success\":false,\"message\":\"Title and description required\"}"
            );
            return;
        }

        // Step 4: Auto-classify using NLP (the keyword matching logic)
        String department = GrievanceClassifier.classify(title, description);

        // Step 5: Save to database
        Grievance g = new Grievance();
        g.setUserId(userId);
        g.setTitle(title.trim());
        g.setDescription(description.trim());
        g.setDepartment(department);

        GrievanceDAO dao = new GrievanceDAO();
        boolean success = dao.insertGrievance(g);

        // Step 6: Send result back to browser
        if (success) {
            res.getWriter().write(
                "{\"success\":true," +
                "\"department\":\"" + department + "\"}"
            );
        } else {
            res.getWriter().write(
                "{\"success\":false,\"message\":\"Failed to save. Try again.\"}"
            );
        }
    }
}
