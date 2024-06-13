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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConsultasController implements Initializable {

	@FXML private TableView<Producto> tablaProductos;
	@FXML private TableColumn<Producto, Integer> tableIdProducto;
	@FXML private TableColumn<Producto, String> tableNombreProducto;
	@FXML private TableColumn<Producto, Integer> tablePrecioProducto;
	@FXML private TableColumn<Producto, Integer> tableStockProducto;

	@FXML private ComboBox<String> nombreAdmins;
	@FXML private TableView<Cuenta> tablaTotal;
	@FXML private TableColumn<Cuenta, String> tablaTotalNombre;
	@FXML private TableColumn<Cuenta, Integer> tablaTotalSuma;

	@FXML
	private Spinner<Integer> field;
	private SpinnerValueFactory<Integer> valueFactorySpinner;

	@FXML
	private MenuButton menuConsultas;

	@FXML
	private TableView<Usuario> tablaUsuarios;

	@FXML
	private TableColumn<Usuario, Integer> tableEdad;

	@FXML
	private TableColumn<Usuario, String> tableDireccion;

	@FXML
	private TableColumn<Usuario, String> tableEmail;

	@FXML
	private TableColumn<Usuario, Integer> tableId;

	@FXML
	private TableColumn<Usuario, String> tableName;

	@FXML
	private Text texto, enunciado, suma;

	@FXML
	private Button volver;

	@FXML
	private DatePicker fecha;

	Conexion conexion = new Conexion();
	ObservableList<Usuario> admins;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		valueFactorySpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
		valueFactorySpinner.setValue(1);
		field.setValueFactory(valueFactorySpinner);

		tableId.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id"));
		tableName.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
		tableEmail.setCellValueFactory(new PropertyValueFactory<Usuario, String>("email"));
		tableDireccion.setCellValueFactory(new PropertyValueFactory<Usuario, String>("direccion"));
		tableEdad.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("edad"));

		tablaTotalNombre.setCellValueFactory(new PropertyValueFactory<Cuenta, String>("nombre"));
		tablaTotalSuma.setCellValueFactory(new PropertyValueFactory<Cuenta, Integer>("total"));

		tableIdProducto.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("id"));
		tableNombreProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
		tablePrecioProducto.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("precio"));
		tableStockProducto.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("stock"));
	}

	@FXML
	void getUsuariosSimple(ActionEvent event) {
		hideEverything();
		enunciado.setText("Muestra los contenidos de usuario de forma user friendly sin mostrar la contraseña");
		enunciado.setVisible(true);
		tablaUsuarios.setVisible(true);
		ObservableList<Usuario> usuarios = conexion.getUsuariosConsulta1();
		tablaUsuarios.setItems(usuarios);
	}

	@FXML
	void getSumaAlmacenes(ActionEvent event) {
		hideEverything();
		enunciado.setText("Cuenta cuantos almacenes son manejados por un mismo administrador");
		enunciado.setVisible(true);
		tablaTotal.setVisible(true);
		ObservableList<Cuenta> cuentas = conexion.getSumaAlmacen();
		tablaTotal.setItems(cuentas);
	}

	@FXML
	void getSumaCompras(ActionEvent event) {
		hideEverything();
		enunciado.setText("Cuenta cuantos pedidos ha realizado un usuario");
		enunciado.setVisible(true);
		tablaTotal.setVisible(true);
		ObservableList<Cuenta> cuentas = conexion.getSumaProductos();
		tablaTotal.setItems(cuentas);
	}

	@FXML
	void showSpinnerUsuario(ActionEvent event) {
		hideEverything();
		tablaUsuarios.setVisible(true);
		valueFactorySpinner.setValue(50);
		enunciado.setText("Muestra la informacion de los usuarios que han gastado mas de un precio determinado");
		enunciado.setVisible(true);
		texto.setText("Introduce un precio para buscar");
		texto.setVisible(true);
		field.setVisible(true);
	}

	@FXML
	void showComboBox(ActionEvent event) {
		hideEverything();
		nombreAdmins.setVisible(true);
		enunciado.setText("Muestra todos los productos de un administrador con id dado");
		enunciado.setVisible(true);
		texto.setText("¿Que admin quieres buscar?");
		texto.setVisible(true);
		admins = conexion.getAdmins();
		for (Usuario admin: admins) {
			nombreAdmins.getItems().add(admin.nombre);
		}
	}

	@FXML
	void populateProductos() {
		int selectedIndex = nombreAdmins.getSelectionModel().getSelectedIndex();
		Usuario selectedItem = admins.get(selectedIndex);
		ObservableList<Producto> productos = conexion.getProductosAdmin(selectedItem.id);
		tablaProductos.setItems(productos);
		tablaProductos.setVisible(true);
	}

	@FXML
	void populateUsuarios() {
		ObservableList<Usuario> usuarios = conexion.getUsuariosConsulta2(field.getValue());
		tablaUsuarios.setItems(usuarios);
		tablaUsuarios.setVisible(true);
	}

	@FXML
	void showDatePicker() {
		hideEverything();
		enunciado.setText("Muestra el dinero ganado a partir de una fecha");
		enunciado.setVisible(true);
		texto.setText("Introduce una fecha");
		texto.setVisible(true);
		fecha.setVisible(true);
	}

	@FXML
	void sumTotal() {
		enunciado.setText("Muestra el dinero ganado a partir de una fecha dada");
		enunciado.setVisible(true);
		suma.setText("DINERO GANADO A PARTIR DEL DIA " + fecha.getValue().toString() + ": " +
				"\n" + conexion.dineroGanado(fecha.getValue()) + "€");
		suma.setVisible(true);
	}

	private void hideEverything() {
		tablaTotal.setVisible(false);
		tablaUsuarios.setVisible(false);
		tablaProductos.setVisible(false);
		nombreAdmins.setVisible(false);
		enunciado.setVisible(false);
		field.setVisible(false);
		texto.setVisible(false);
		fecha.setVisible(false);
		suma.setVisible(false);
	}

	@FXML
	void volver(ActionEvent event) {
		Stage ventana = (Stage) menuConsultas.getScene().getWindow();
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
