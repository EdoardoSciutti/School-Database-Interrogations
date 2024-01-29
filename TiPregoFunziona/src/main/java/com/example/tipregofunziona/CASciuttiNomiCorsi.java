package com.example.tipregofunziona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CASciuttiNomiCorsi {

    public class Corso {
        String titolo;
        String id;

        public Corso(String nome, String id) {
                this.titolo = nome;
                this.id = id;
            }

            @Override
            public String toString() {
                return titolo;
            }
    
            public String getTitolo() {
                return this.titolo;
            }
        }
        
        public List<Corso> getCorsi() {
        List<Corso> corsi = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/calvinoacademy",
                        "root", "");
                Statement stmt = conn.createStatement();
        ) {
            String listaCorsi = "SELECT ca_courses.title, ca_courses.id FROM ca_courses";
            ResultSet rset = stmt.executeQuery(listaCorsi);

            while(rset.next()) {
                String nome = rset.getString("ca_courses.title");
                String id = rset.getString("ca_courses.id");
                Corso corso = new Corso(nome, id);
                corsi.add(corso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return corsi;
    }
}