module com.example.tipregofunziona {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.tipregofunziona to javafx.fxml;
    exports com.example.tipregofunziona;
}