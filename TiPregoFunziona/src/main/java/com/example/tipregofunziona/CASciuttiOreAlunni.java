package com.example.tipregofunziona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CASciuttiOreAlunni {

    public class Ora {
        String ore;

        public Ora(String ore) {
            this.ore = ore;
        }

        @Override
        public String toString() {
            return ore;
        }

        public String getOre() {
            return this.ore;
        }
    }

    public List<Ora> getCorsi(String userId, String courseId) {
        List<Ora> orari = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/calvinoacademy",
                        "root", "");
        ) {
            String resOre = "SELECT SUM(HOUR(TIMEDIFF(a.ended_at, a.started_at))) AS ore FROM ca_users AS u JOIN ca_presences AS p ON u.id = p.user_id JOIN ca_activities AS a ON p.activity_id = a.id JOIN ca_courses AS c ON a.course_id = c.id WHERE u.name = ? AND c.title = ?";
            PreparedStatement stmt = conn.prepareStatement(resOre);
            stmt.setString(1, userId);
            stmt.setString(2, courseId);
            ResultSet rset = stmt.executeQuery();

            while(rset.next()) {
                String ore = rset.getString("ore");
                Ora ora = new Ora(ore);
                orari.add(ora);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orari;
    }
}