java
package com.grievance.servlet;

import com.grievance.dao.GrievanceDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

// Called from admin.jsp when admin changes complaint status
@WebServlet("/updateStatus")
public class UpdateStatusServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("application/json");

        // Only admin can update status — security check
        HttpSession session = req.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            res.getWriter().write(
                "{\"success\":false,\"message\":\"Unauthorized\"}"
            );
            return;
        }

        // Get complaint ID and new status from admin form
        String idParam = req.getParameter("id");
        String status = req.getParameter("status");

        if (idParam == null || status == null) {
            res.getWriter().write(
                "{\"success\":false,\"message\":\"Missing parameters\"}"
            );
            return;
        }

        int id = Integer.parseInt(idParam);

        GrievanceDAO dao = new GrievanceDAO();
        boolean ok = dao.updateStatus(id, status);

        res.getWriter().write("{\"success\":" + ok + "}");
    }
}
