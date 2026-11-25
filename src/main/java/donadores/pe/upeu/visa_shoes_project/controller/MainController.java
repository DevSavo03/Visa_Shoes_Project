package donadores.pe.upeu.visa_shoes_project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        mostrarInicio();
    }

    @FXML
    private void mostrarInicio() {
        cargarVista("home-view.fxml");
    }

    @FXML
    private void mostrarClientes() {
        cargarVista("cliente-view.fxml");
    }

    @FXML
    private void mostrarProductos() {
        cargarVista("producto-view.fxml");
    }

    @FXML
    private void mostrarVentas() {
        cargarVista("venta-view.fxml");
    }

    private void cargarVista(String nombreVista) {
        String ruta = "/donadores/pe/upeu/visa_shoes_project/views/" + nombreVista;
        URL recurso = getClass().getResource(ruta);
        if (recurso == null) {
            System.err.println("No se encontró la vista: " + ruta);
            return;
        }

        try {
            Parent vista = new FXMLLoader(recurso).load();
            contentArea.getChildren().setAll(vista);
        } catch (IOException e) {
            System.err.println("No se pudo abrir la vista " + nombreVista + ": " + e.getMessage());
        }
    }
}
