package com.grievance.dao;

import com.grievance.model.Grievance;
import java.sql.*;
import java.util.*;

public class GrievanceDAO {

    public boolean insertGrievance(Grievance g) {
        String sql = "INSERT INTO grievances (user_id, title, description, department) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, g.getUserId());
            ps.setString(2, g.getTitle());
            ps.setString(3, g.getDescription());
            ps.setString(4, g.getDepartment());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Grievance> getAllGrievances() {
        List<Grievance> list = new ArrayList<>();
        String sql = "SELECT * FROM grievances";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Grievance g = new Grievance();
                g.setId(rs.getInt("id"));
                g.setTitle(rs.getString("title"));
                g.setDepartment(rs.getString("department"));
                g.setStatus(rs.getString("status"));
                list.add(g);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}