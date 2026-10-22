module donadores.pe.upeu.visa_shoes_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;


    opens pe.edu.upeu.project_final.Models to javafx.base, javafx.fxml;
    opens donadores.pe.upeu.visa_shoes_project to javafx.fxml;
    exports donadores.pe.upeu.visa_shoes_project;

}