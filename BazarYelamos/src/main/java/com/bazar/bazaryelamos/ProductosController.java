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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductosController implements Initializable {

	@FXML
	private TableColumn<Producto, Integer> tableId;

	@FXML
	private TableColumn<Producto, String> tableName;

	@FXML
	private TableColumn<Producto, Integer> tablePrecio;

	@FXML
	private TableColumn<Producto, Integer> tableStock;

	@FXML
	private TableView<Producto> tabla;

	@FXML
	private Button actualizar;

	@FXML
	private Button eliminar;

	@FXML
	private Button insertar;

	@FXML
	private Button limpiar;

	@FXML
	private Button volver;

	@FXML
	private TextField nombre;

	@FXML
	private Spinner<Integer> precio;
	SpinnerValueFactory<Integer> valueFactoryPrecio;

	@FXML
	private Spinner<Integer> stock;
	SpinnerValueFactory<Integer> valueFactoryStock;

	private int id;
	Conexion conexion = new Conexion();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		valueFactoryPrecio = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
		valueFactoryPrecio.setValue(1);
		valueFactoryStock = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
		valueFactoryStock.setValue(1);
		precio.setValueFactory(valueFactoryPrecio);
		stock.setValueFactory(valueFactoryStock);
		tableId.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("id"));
		tableName.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
		tablePrecio.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("precio"));
		tableStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("stock"));
		showProductos();
		insertar.setDisable(true);
		actualizar.setDisable(true);
		eliminar.setDisable(true);
	}

	public void showProductos() {
		ObservableList<Producto> productos = conexion.getProductos();
		tabla.setItems(productos);
	}

	@FXML
	void actualizarProducto(ActionEvent event) {
		conexion.updateProducto(id, nombre.getText(), precio.getValue(), stock.getValue());
		showProductos();
	}

	@FXML
	void eliminarProducto(ActionEvent event) {
		conexion.deleteProducto(id);
		showProductos();
	}

	@FXML
	void insertarProducto(ActionEvent event) {
		conexion.insertarProducto(nombre.getText(), precio.getValue(), stock.getValue());
		showProductos();
	}

	@FXML
	void limpiar(ActionEvent event) {
		nombre.clear();
		valueFactoryPrecio.setValue(0);
		valueFactoryStock.setValue(0);
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

	@FXML
	void getData(javafx.scene.input.MouseEvent mouseEvent) {
		Producto producto = tabla.getSelectionModel().getSelectedItem();
		if (producto != null) {
			id = producto.id;
			nombre.setText(producto.nombre);
			valueFactoryPrecio.setValue(producto.precio);
			valueFactoryStock.setValue(producto.stock);
			insertar.setDisable(true);
			eliminar.setDisable(false);
			actualizar.setDisable(false);
		}
	}

	@FXML void checkNombre(KeyEvent keyEvent) {
		String name = nombre.getText();
		insertar.setDisable(conexion.getNombreProducto(nombre.getText()));
		if (name.equals("") || name.isBlank()) {
			insertar.setDisable(true);
		}
	}
}
