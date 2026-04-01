<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.grievance.dao.GrievanceDAO" %>
<%@ page import="com.grievance.model.Grievance" %>
<%@ page import="java.util.List" %>

<%
    String role = (String) session.getAttribute("role");
    if (role == null || !role.equals("admin")) {
        response.sendRedirect("index.html");
        return;
    }

    GrievanceDAO dao = new GrievanceDAO();
    List<Grievance> grievances = dao.getAllGrievances();
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Dashboard</title>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Syne:wght@700;800&family=DM+Sans:wght@400;500&display=swap');

    *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

    :root {
      --navy: #0a1628;
      --blue: #1a56db;
      --accent: #f59e0b;
    }

    body {
      background: #f0f4ff;
      font-family: 'DM Sans', sans-serif;
      min-height: 100vh;
    }

    nav {
      background: var(--navy);
      padding: 16px 40px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .nav-brand {
      font-family: 'Syne', sans-serif;
      color: #fff;
      font-size: 20px;
      font-weight: 800;
    }
    .nav-brand span { color: var(--accent); }
    .nav-right {
      display: flex;
      align-items: center;
      gap: 20px;
    }
    .admin-badge {
      background: rgba(245,158,11,0.15);
      color: var(--accent);
      border: 1px solid rgba(245,158,11,0.3);
      padding: 6px 14px;
      border-radius: 20px;
      font-size: 12px;
      letter-spacing: 1px;
    }
    .logout-btn {
      color: rgba(255,255,255,0.7);
      text-decoration: none;
      font-size: 14px;
      padding: 8px 16px;
      border-radius: 6px;
      transition: all 0.2s;
    }
    .logout-btn:hover {
      background: rgba(255,255,255,0.1);
      color: #fff;
    }

    .container { max-width: 1200px; margin: 0 auto; padding: 0 24px; }

    .stats-row {
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      gap: 20px;
      margin: 32px 0;
    }
    .stat-card {
      background: #fff;
      border-radius: 12px;
      padding: 24px;
      box-shadow: 0 2px 12px rgba(0,0,0,0.06);
      border-left: 4px solid #e2e8f0;
    }
    .stat-card.total   { border-left-color: var(--blue); }
    .stat-card.pending { border-left-color: #f59e0b; }
    .stat-card.progress { border-left-color: #6366f1; }
    .stat-card.resolved { border-left-color: #10b981; }

    .stat-number {
      font-family: 'Syne', sans-serif;
      font-size: 36px;
      font-weight: 800;
      color: var(--navy);
    }
    .stat-label {
      font-size: 13px;
      color: #64748b;
      margin-top: 4px;
      text-transform: uppercase;
      letter-spacing: 1px;
    }

    .section-title {
      font-family: 'Syne', sans-serif;
      font-size: 22px;
      color: var(--navy);
      margin-bottom: 16px;
    }

    .table-wrapper {
      background: #fff;
      border-radius: 16px;
      box-shadow: 0 4px 24px rgba(0,0,0,0.07);
      overflow: hidden;
      margin-bottom: 40px;
    }

    table { width: 100%; border-collapse: collapse; }
    thead { background: var(--navy); }
    th {
      padding: 16px 20px;
      text-align: left;
      color: rgba(255,255,255,0.8);
      font-family: 'Syne', sans-serif;
      font-size: 12px;
      letter-spacing: 1px;
      text-transform: uppercase;
    }
    td {
      padding: 16px 20px;
      border-bottom: 1px solid #f1f5f9;
      font-size: 14px;
      color: #374151;
      vertical-align: middle;
    }
    tr:last-child td { border-bottom: none; }
    tr:hover td { background: #f8fafc; }

    .complaint-title {
      font-weight: 500;
      color: var(--navy);
      max-width: 250px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .badge {
      padding: 5px 14px;
      border-radius: 20px;
      font-size: 12px;
      font-weight: 600;
      white-space: nowrap;
    }
    .badge-Pending    { background: #fef9c3; color: #854d0e; }
    .badge-InProgress { background: #dbeafe; color: #1e40af; }
    .badge-Resolved   { background: #dcfce7; color: #166534; }

    .action-group {
      display: flex;
      gap: 8px;
      align-items: center;
    }
    select {
      padding: 7px 10px;
      border: 1.5px solid #e2e8f0;
      border-radius: 8px;
      font-family: 'DM Sans', sans-serif;
      font-size: 13px;
      color: #374151;
      background: #fff;
      cursor: pointer;
      outline: none;
    }
    select:focus { border-color: var(--blue); }

    .update-btn {
      padding: 7px 16px;
      background: var(--blue);
      color: #fff;
      border: none;
      border-radius: 8px;
      font-family: 'DM Sans', sans-serif;
      font-size: 13px;
      font-weight: 500;
      cursor: pointer;
      transition: all 0.2s;
      white-space: nowrap;
    }
    .update-btn:hover { background: #1447b0; }
    .update-btn.loading { opacity: 0.6; cursor: not-allowed; }

    .empty-state {
      text-align: center;
      padding: 60px;
      color: #94a3b8;
    }
    .empty-state h3 {
      font-family: 'Syne', sans-serif;
      font-size: 18px;
      margin-bottom: 8px;
    }

    .toast {
      position: fixed;
      bottom: 30px;
      right: 30px;
      background: #0a1628;
      color: #fff;
      padding: 14px 24px;
      border-radius: 10px;
      font-size: 14px;
      display: none;
      z-index: 999;
      box-shadow: 0 8px 24px rgba(0,0,0,0.2);
    }
    .toast.show { display: block; }
    .toast.success { border-left: 4px solid #10b981; }
    .toast.error   { border-left: 4px solid #ef4444; }
  </style>
</head>
<body>

  <nav>
    <div class="nav-brand">Grievance <span>Admin</span></div>
    <div class="nav-right">
      <span class="admin-badge">ADMIN PANEL</span>
      <a href="logout" class="logout-btn">Logout</a>
    </div>
  </nav>

  <div class="container">

    <%
      int total = grievances.size();
      int pending = 0, inProgress = 0, resolved = 0;
      for (Grievance g : grievances) {
          if ("Pending".equals(g.getStatus()))     pending++;
          if ("In Progress".equals(g.getStatus())) inProgress++;
          if ("Resolved".equals(g.getStatus()))    resolved++;
      }
    %>

    <div class="stats-row">
      <div class="stat-card total">
        <div class="stat-number"><%= total %></div>
        <div class="stat-label">Total Complaints</div>
      </div>
      <div class="stat-card pending">
        <div class="stat-number"><%= pending %></div>
        <div class="stat-label">Pending</div>
      </div>
      <div class="stat-card progress">
        <div class="stat-number"><%= inProgress %></div>
        <div class="stat-label">In Progress</div>
      </div>
      <div class="stat-card resolved">
        <div class="stat-number"><%= resolved %></div>
        <div class="stat-label">Resolved</div>
      </div>
    </div>

    <h2 class="section-title">All Grievances</h2>

    <div class="table-wrapper">
      <% if (grievances.isEmpty()) { %>
        <div class="empty-state">
          <h3>No complaints submitted yet</h3>
          <p>When citizens submit complaints, they will appear here.</p>
        </div>
      <% } else { %>
      <table>
        <thead>
          <tr>
            <th>#ID</th>
            <th>Citizen Name</th>
            <th>Complaint Title</th>
            <th>Department</th>
            <th>Date</th>
            <th>Status</th>
            <th>Update Status</th>
          </tr>
        </thead>
        <tbody>
          <% for (Grievance g : grievances) {
             String badgeClass = "badge-" + g.getStatus().replace(" ", "");
          %>
          <tr id="row-<%= g.getId() %>">
            <td>#<%= g.getId() %></td>
            <td><%= g.getUserName() %></td>
            <td>
              <div class="complaint-title" title="<%= g.getTitle() %>">
                <%= g.getTitle() %>
              </div>
            </td>
            <td><%= g.getDepartment() %></td>
            <td><%= g.getSubmittedAt().substring(0, 10) %></td>
            <td>
              <span class="badge <%= badgeClass %>" id="badge-<%= g.getId() %>">
                <%= g.getStatus() %>
              </span>
            </td>
            <td>
              <div class="action-group">
                <select id="status-<%= g.getId() %>">
                  <option value="Pending"
                    <%= "Pending".equals(g.getStatus()) ? "selected" : "" %>>
                    Pending
                  </option>
                  <option value="In Progress"
                    <%= "In Progress".equals(g.getStatus()) ? "selected" : "" %>>
                    In Progress
                  </option>
                  <option value="Resolved"
                    <%= "Resolved".equals(g.getStatus()) ? "selected" : "" %>>
                    Resolved
                  </option>
                </select>
                <button class="update-btn" onclick="updateStatus(this)" data-id="<%= g.getId() %>"></button>
                  Update
                </button>
              </div>
            </td>
          </tr>
          <% } %>
        </tbody>
      </table>
      <% } %>
    </div>

  </div>

  <div class="toast" id="toast"></div>

  <script>
    function updateStatus(id) {
      var select = document.getElementById("status-" + id);
      var newStatus = select.value;
      var btn = select.nextElementSibling;

      btn.textContent = "Saving...";
      btn.classList.add("loading");
      btn.disabled = true;

      var body = "id=" + id + "&status=" + encodeURIComponent(newStatus);

      fetch("updateStatus", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: body
      })
      .then(function(res) { return res.json(); })
      .then(function(data) {
        if (data.success) {
          var badge = document.getElementById("badge-" + id);
          badge.textContent = newStatus;
          badge.className = "badge badge-" + newStatus.replace(" ", "");
          showToast("Status updated to " + newStatus, "success");
        } else {
          showToast("Update failed. Try again.", "error");
        }
        btn.textContent = "Update";
        btn.classList.remove("loading");
        btn.disabled = false;
      })
      .catch(function() {
        showToast("Network error.", "error");
        btn.textContent = "Update";
        btn.classList.remove("loading");
        btn.disabled = false;
      });
    }

    function showToast(message, type) {
      var toast = document.getElementById("toast");
      toast.textContent = message;
      toast.className = "toast show " + type;
      setTimeout(function() { toast.className = "toast"; }, 3000);
    }
  </script>

</body>
</html> 