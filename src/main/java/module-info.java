module com.example.group21 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;

    requires org.json;
    requires json.simple;
   // requires org.junit.jupiter.api;

    opens CS2212.group21 to javafx.fxml;
    exports CS2212.group21;
}