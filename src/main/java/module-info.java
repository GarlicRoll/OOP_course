module com.example.oop_course {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.oop_course to javafx.fxml;
    exports com.example.oop_course;
}