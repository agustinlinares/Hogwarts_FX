package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import view.WizardViewFX;

import static javafx.application.Application.launch;

public class App extends Application {
    public void start(Stage stage){
        TabPane tabPane = new TabPane();
        Tab wizard = new Tab(" Wizard ");
        Tab wand = new Tab(" Wand ");
        Tab house = new Tab(" House ");

        WizardViewFX wizardView = new WizardViewFX();
        wizard.setContent(wizardView.getRoot());
        tabPane.getTabs().add(wizard);
        wizard.setClosable(false);
        tabPane.getTabs().add(wand);
        wand.setClosable(false);
        tabPane.getTabs().add(house);
        house.setClosable(false);

        Scene scene = new Scene(tabPane, 1300, 750);
        stage.setTitle("Gestión de Mundo Mágico");
        stage.setResizable(false);
        String css = getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}