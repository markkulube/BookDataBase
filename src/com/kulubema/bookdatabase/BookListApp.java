package com.kulubema.bookdatabase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BookListApp extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("BookListView.fxml"));
        primaryStage.setTitle("Book List");
        primaryStage.setScene(new Scene(root, 765, 534));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
