package com.bazar.bazaryelamos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class Conexion {
	Connection connection = null;
	PreparedStatement sentencia = null;
	ResultSet resultado = null;
	public Conexion() {
		try {
			String usuario = "postgres";
			String url = "jdbc:postgresql://localhost:5432/BazarYelamos";
			String pass = "1234";
			connection = DriverManager.getConnection(url, usuario, pass);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public ObservableList<Producto> getProductos() {
		String sentenciaSQL = "Select id, nombre, precio, stock from productos";
		ObservableList<Producto> productos = FXCollections.observableArrayList();
		if (connection != null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				resultado = sentencia.executeQuery();
				while (resultado.next()) {
					productos.add(new Producto(resultado.getString(2), resultado.getInt(1), resultado.getInt(3), resultado.getInt(4)));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return productos;
	}

	public ObservableList<Usuario> getUsuarios() {
		String sentenciaSQL = "select id, edad, nombre, apellidos, email from usuarios;";
		ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
		if (connection != null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				resultado = sentencia.executeQuery();
				while (resultado.next()) {
					usuarios.add(new Usuario(resultado.getInt(1), resultado.getInt(2), resultado.getString(3), resultado.getString(4), resultado.getString(5)));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return usuarios;
	}

	public ObservableList<Usuario> getUsuariosConsulta1() {
		String sentenciaSQL = "select id, concat(nombre, ' ', apellidos) as nombre, edad, email, concat((direccion).calle, ' ', (direccion).ciudad, ' ', (direccion).provincia, ' - ', (direccion).cp) as direccion from only usuarios;";
		ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
		if (connection != null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				resultado = sentencia.executeQuery();
				while (resultado.next()) {
					usuarios.add(new Usuario(resultado.getInt(3), resultado.getString(2), resultado.getString(5), resultado.getString(4), resultado.getInt(1)));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return usuarios;
	}

	public ObservableList<Usuario> getUsuariosConsulta2(int precio) {
		String sentenciaSQL = "select id, concat(nombre, ' ', apellidos) as nombre, edad, email, concat((direccion).calle, ' ', (direccion).ciudad, ' ', (direccion).provincia, ' - ', (direccion).cp) as direccion from only usuarios where id in (select idcliente from pedidos where total > ?);";
		ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
		if (connection != null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				sentencia.setInt(1, precio);
				resultado = sentencia.executeQuery();
				while (resultado.next()) {
					usuarios.add(new Usuario(resultado.getInt(3), resultado.getString(2), resultado.getString(5), resultado.getString(4), resultado.getInt(1)));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return usuarios;
	}

	public ObservableList<Cuenta> getSumaAlmacen() {
		String sentenciaSQL = "select count(almacenes.direccion) as total, nombre  from administradores inner join almacenes on administradores.id = almacenes.idadmin group by nombre;";
		ObservableList<Cuenta> cuentas = FXCollections.observableArrayList();
		if (connection != null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				resultado = sentencia.executeQuery();
				while (resultado.next()) {
					cuentas.add(new Cuenta(resultado.getInt("total"), resultado.getString("nombre")));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return cuentas;
	}

	public ObservableList<Cuenta> getSumaProductos() {
		String sentenciaSQL = "select nombre, count(pedidos.id) as total from usuarios inner join pedidos on usuarios.id = pedidos.idcliente group by nombre;";
		ObservableList<Cuenta> cuentas = FXCollections.observableArrayList();
		if (connection != null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				resultado = sentencia.executeQuery();
				while (resultado.next()) {
					cuentas.add(new Cuenta(resultado.getInt("total"), resultado.getString("nombre")));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return cuentas;
	}

	public ObservableList<Usuario> getAdmins() {
		String sentenciaSQL = "select id, nombre from administradores";
		ObservableList<Usuario> admins = FXCollections.observableArrayList();
		if (connection!=null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				resultado = sentencia.executeQuery();
				while (resultado.next()) {
					admins.add(new Usuario(resultado.getInt(1), resultado.getString(2)));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return admins;
	}

	public ObservableList<Producto> getProductosAdmin(int id) {
		String sentenciaSQL = "SELECT nombre, id, precio, stock FROM productos WHERE productos.idalmacen IN (SELECT almacenes.id FROM almacenes WHERE almacenes.idadmin = (SELECT id FROM administradores where id = ?));";
		ObservableList<Producto> productos = FXCollections.observableArrayList();
		if (connection != null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				sentencia.setInt(1, id);
				resultado = sentencia.executeQuery();
				while (resultado.next()) {
					productos.add(new Producto(resultado.getString(1), resultado.getInt(2), resultado.getInt(3), resultado.getInt(4)));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return productos;
	}

	public int dineroGanado(LocalDate fecha) {
		String sentenciaSQL = "select sum(total) from pedidos where fecha >= ?;";
		if (connection!=null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				sentencia.setDate(1, Date.valueOf(fecha));
				resultado = sentencia.executeQuery();
				resultado.next();
				return resultado.getInt(1);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return 0;
	}

	public void insertarProducto(String nombre, int precio, int stock) {
		String sentenciaSQL = "INSERT INTO productos (nombre, precio, stock, idAlmacen) VALUES (?, ?, ?, ?)";
		if (connection != null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				sentencia.setString(1, nombre);
				sentencia.setInt(2, precio);
				sentencia.setInt(3, stock);
				sentencia.setInt(4, 1);
				sentencia.execute();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void insertarUsuario(String nombre, String apellidos, String email, int edad) {
		String sentenciaSQL = "INSERT INTO usuarios (nombre, apellidos, email, edad) VALUES (?, ?, ?, ?)";
		if (connection != null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				sentencia.setString(1, nombre);
				sentencia.setString(2, apellidos);
				sentencia.setString(3, email);
				sentencia.setInt(4, edad);
				sentencia.execute();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public boolean getNombreProducto(String text) {
		String sentenciaSQL = "Select * from productos where nombre = ?";
		if (connection != null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				sentencia.setString(1, text);
				resultado = sentencia.executeQuery();
				return resultado.next();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}

	public boolean getNombreUsuario(String nombre, String apellidos) {
		String sentenciaSQL = "Select * from usuarios where nombre = ? and apellidos = ?";
		if (connection != null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				sentencia.setString(1, nombre);
				sentencia.setString(2, apellidos);
				resultado = sentencia.executeQuery();
				return resultado.next();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}

	public void deleteProducto(int id) {
		String sentenciaSQL = "delete from productos where id = ?";
		if (connection!=null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				sentencia.setInt(1, id);
				sentencia.execute();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void deleteUsuario(int id) {
		String sentenciaSQL = "delete from usuarios where id = ?";
		if (connection!=null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				sentencia.setInt(1, id);
				sentencia.execute();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void updateProducto(int id, String nombre, int precio, int stock) {
		String sentenciaSQL = "update productos set nombre=?,precio=?,stock=? where id=?";
		if (connection!=null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				sentencia.setString(1, nombre);
				sentencia.setInt(2, precio);
				sentencia.setInt(3, stock);
				sentencia.setInt(4, id);
				sentencia.execute();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void updateUsuario(int id, String nombre, String apellidos, String email ,int edad) {
		String sentenciaSQL = "update usuarios set nombre=?,apellidos=?,email=?,edad=? where id=?";
		if (connection!=null) {
			try {
				sentencia = connection.prepareStatement(sentenciaSQL);
				sentencia.setString(1, nombre);
				sentencia.setString(2, apellidos);
				sentencia.setString(3, email);
				sentencia.setInt(4, edad);
				sentencia.setInt(5, id);
				sentencia.execute();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
