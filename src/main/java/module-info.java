module com.example.binaryTree {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.binaryTree to javafx.fxml;
    exports com.example.binaryTree;
}