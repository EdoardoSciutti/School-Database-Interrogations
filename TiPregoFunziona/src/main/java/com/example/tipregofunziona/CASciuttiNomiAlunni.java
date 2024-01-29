package com.example.tipregofunziona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CASciuttiNomiAlunni {
    public class Alunno {
        String nome;
        String id;

        public Alunno(String nome, String id) {
            this.nome = nome;
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        @Override
        public String toString() {
            return nome;
        }
    }

    public List<Alunno> getAlunni(String idCorso) {
        List<Alunno> alunni = new ArrayList<>();

        try {
            try (
                Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/calvinoacademy",
                    "root", "");
                Statement stmt = conn.createStatement();
            ) {
                String listaCorsi = "SELECT DISTINCT u.name, u.id FROM ca_users AS u JOIN ca_presences AS p ON u.id = p.user_id JOIN ca_activities AS a ON p.activity_id = a.id JOIN ca_courses AS c ON a.course_id = c.id AND c.id = ? ORDER BY u.name";
                try (PreparedStatement pstmt = conn.prepareStatement(listaCorsi)) {
                    pstmt.setString(1, idCorso);
                    ResultSet rset = pstmt.executeQuery();

                    while(rset.next()) {
                        String nome = rset.getString("u.name");
                        String id = rset.getString("u.id");
                        Alunno alunno = new Alunno(nome, id);
                        alunni.add(alunno);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alunni;
    }

}
