package donadores.pe.upeu.visa_shoes_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(
                "/donadores/pe/upeu/visa_shoes_project/views/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 720);
        stage.setTitle("Visa Shoes");
        stage.setScene(scene);
        stage.show();
    }
}
