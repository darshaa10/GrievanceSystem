java
package com.grievance.servlet;

import com.grievance.dao.GrievanceDAO;
import com.grievance.model.Grievance;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

// This URL is called from status.html to show citizen's complaints
@WebServlet("/myGrievances")
public class StatusServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("application/json");

        // Check login
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.getWriter().write("[]"); // empty list
            return;
        }

        int userId = (int) session.getAttribute("userId");

        // Get all complaints of this user from database
        GrievanceDAO dao = new GrievanceDAO();
        List<Grievance> list = dao.getGrievancesByUser(userId);

        // Build JSON response manually
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            Grievance g = list.get(i);
            json.append("{")
                .append("\"id\":").append(g.getId()).append(",")
                .append("\"title\":\"").append(escapeJson(g.getTitle())).append("\",")
                .append("\"department\":\"").append(g.getDepartment()).append("\",")
                .append("\"status\":\"").append(g.getStatus()).append("\",")
                .append("\"date\":\"").append(g.getSubmittedAt().substring(0, 10)).append("\"")
                .append("}");
            if (i < list.size() - 1) json.append(",");
        }
        json.append("]");

        res.getWriter().write(json.toString());
    }

    // Helper to avoid JSON breaking on special characters
    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r");
    }
}
