package com.bazar.bazaryelamos;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientesController implements Initializable {

	@FXML
	private TextField email;

	@FXML
	private Button actualizar;

	@FXML
	private TextField apellidos;

	@FXML
	private Spinner<Integer> edad;
	private SpinnerValueFactory<Integer> valueFactoryEdad;

	@FXML
	private Button eliminar;

	@FXML
	private Button insertar;

	@FXML
	private Button limpiar;

	@FXML
	private TextField nombre;

	@FXML
	private TableView<Usuario> tabla;


	@FXML
	private TableColumn<Usuario, String> tableApellidos;

	@FXML
	private TableColumn<Usuario, Integer> tableEdad;

	@FXML
	private TableColumn<Usuario, String> tableEmail;

	@FXML
	private TableColumn<Usuario, Integer> tableId;

	@FXML
	private TableColumn<Usuario, String> tableName;
	@FXML
	private Button volver;

	Conexion conexion = new Conexion();
	int id;

	public void initialize(URL url, ResourceBundle resourceBundle) {
		valueFactoryEdad = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
		valueFactoryEdad.setValue(1);
		edad.setValueFactory(valueFactoryEdad);

		tableId.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id"));
		tableName.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
		tableApellidos.setCellValueFactory(new PropertyValueFactory<Usuario, String>("apellidos"));
		tableEmail.setCellValueFactory(new PropertyValueFactory<Usuario, String>("email"));
		tableEdad.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("edad"));
		showUsuarios();

		insertar.setDisable(true);
		actualizar.setDisable(true);
		eliminar.setDisable(true);
	}

	public void showUsuarios() {
		ObservableList<Usuario> usuarios = conexion.getUsuarios();
		tabla.setItems(usuarios);
	}

	@FXML
	void actualizarUsuario(ActionEvent event) {
		conexion.updateUsuario(id, nombre.getText(), apellidos.getText(), email.getText(), edad.getValue());
		showUsuarios();
	}

	@FXML
	void checkNombre(KeyEvent event) {
		String name = nombre.getText();
		insertar.setDisable(conexion.getNombreUsuario(nombre.getText(), apellidos.getText()));
		if (name.equals("") || name.isBlank()) {
			insertar.setDisable(true);
		}
	}

	@FXML
	void eliminarUsuario(ActionEvent event) {
		conexion.deleteUsuario(id);
		showUsuarios();
	}

	@FXML
	void getData(MouseEvent event) {
		Usuario usuario = tabla.getSelectionModel().getSelectedItem();
		if (usuario != null) {
			id = usuario.id;
			nombre.setText(usuario.nombre);
			apellidos.setText(usuario.apellidos);
			valueFactoryEdad.setValue(usuario.edad);
			email.setText(usuario.email);
			insertar.setDisable(true);
			eliminar.setDisable(false);
			actualizar.setDisable(false);
		}
	}

	@FXML
	void insertarUsuario(ActionEvent event) {
		conexion.insertarUsuario(nombre.getText(), apellidos.getText(), email.getText(), edad.getValue());
		showUsuarios();
	}

	@FXML
	void limpiar(ActionEvent event) {
		nombre.clear();
		apellidos.clear();
		valueFactoryEdad.setValue(0);
		email.clear();
	}

	@FXML
	void volver(ActionEvent event) {
		Stage ventana = (Stage) actualizar.getScene().getWindow();
		ventana.close();

		Parent parent = null;
		try {
			parent = FXMLLoader.load(getClass().getResource("main.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setTitle("Welcome");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}

