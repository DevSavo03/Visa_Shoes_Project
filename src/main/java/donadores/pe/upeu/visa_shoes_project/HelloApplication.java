package donadores.pe.upeu.visa_shoes_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(
                "/donadores/pe/upeu/visa_shoes_project/views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 400);
        stage.getIcons().add(
                new Image(getClass().getResourceAsStream(
                        "/donadores/pe/upeu/visa_shoes_project/images/logo.png")));
        stage.setTitle("Visa Shoes - Iniciar sesión");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
