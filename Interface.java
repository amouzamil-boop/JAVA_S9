// Contenu pour votre nouveau fichier Interface.java
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane; // J'ai ajouté Pane, qui manquait dans l'image mais pas dans le texte
import javafx.stage.Stage;

public class Interface extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello world");
        Group root = new Group();
        Pane pane = new Pane(root); // Note: Le listing 4 utilise Pane, pas l'image
        Scene theScene = new Scene(pane, 600, 400, true);
        primaryStage.setScene(theScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}