package com.bazar.bazaryelamos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML Button productos;
    @FXML Button usuarios;
    @FXML Button consultas;

    @FXML
    public void onProductosButtonClick() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("productos.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Welcome");
            stage.setScene(scene);
            stage.show();
			close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onClientesButtonClick() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("clientes.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Welcome");
            stage.setScene(scene);
            stage.show();
			close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void onConsultasButtonClick() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("consultas.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Welcome");
            stage.setScene(scene);
            stage.show();
			close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        Stage stage = (Stage) usuarios.getScene().getWindow();
		stage.close();
    }
}
