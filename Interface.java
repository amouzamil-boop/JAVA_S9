// Contenu pour votre nouveau fichier Interface.java
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Interface JavaFX principale.
 */
public class Interface extends Application {

    private static final String CSV_PATH = "./src/data/airport-codes_no_comma.csv";
    private static final String SAMPLE_JSON_PATH = "./src/data/test.txt";

    private Earth earth;
    private World world;
    private PerspectiveCamera camera;
    private double lastMouseY;

    @Override
    public void start(Stage primaryStage) {
        world = new World(CSV_PATH);
        earth = new Earth();

        Scene scene = new Scene(earth, 900, 700, true);
        camera = createCamera();
        scene.setCamera(camera);

        scene.setOnMousePressed(this::handleMousePressed);
        scene.setOnMouseDragged(this::handleMouseDragged);
        scene.setOnMouseClicked(this::handleMouseClicked);

        primaryStage.setTitle("Catch me if you can !");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private PerspectiveCamera createCamera() {
        PerspectiveCamera cam = new PerspectiveCamera(true);
        cam.setTranslateZ(-1000);
        cam.setNearClip(0.1);
        cam.setFarClip(2000);
        cam.setFieldOfView(35);
        return cam;
    }

    private void handleMousePressed(MouseEvent event) {
        lastMouseY = event.getSceneY();
        System.out.println("Clic souris: x=" + event.getSceneX() + " y=" + event.getSceneY());
    }

    private void handleMouseDragged(MouseEvent event) {
        double deltaY = event.getSceneY() - lastMouseY;
        camera.setTranslateZ(camera.getTranslateZ() + deltaY * 2);
        lastMouseY = event.getSceneY();
    }

    private void handleMouseClicked(MouseEvent event) {
        System.out.println("handleMouseClicked appelé - button=" + event.getButton());
        
        // Après la sélection d'un aéroport par clic droit sur le globe
        // Sur macOS, le clic droit peut être détecté différemment, on accepte aussi PRIMARY pour les tests
        if (event.getButton() != MouseButton.SECONDARY && event.getButton() != MouseButton.PRIMARY) {
            System.out.println("Clic ignoré (ni PRIMARY ni SECONDARY).");
            return;
        }
        
        Point2D tex = event.getPickResult().getIntersectedTexCoord();
        if (tex == null) {
            System.out.println("Aucune intersection avec la sphère.");
            return;
        }

        System.out.println("TexCoords: x=" + tex.getX() + " y=" + tex.getY());

        double theta = 180d * (0.5d - tex.getY());
        double phi = 360d * (tex.getX() - 0.5d);

        System.out.println("Coordonnées calculées: theta=" + theta + " phi=" + phi);

        Aeroport nearest = world.findNearestAirport(phi, theta);
        if (nearest == null) {
            System.out.println("Aucun aéroport trouvé.");
            return;
        }

        System.out.println("Aéroport sélectionné: " + nearest);
        earth.displayRedSphere(nearest);
        System.out.println("Sphère rouge affichée, récupération des vols...");
        fetchFlights(nearest.getIATA());
    }

    private void fetchFlights(String arrivalIata) {
        // Clé API AviationStack fournie pour ce TP (usage pédagogique uniquement)
        String apiKey = "3930019efe1856dc0079ad6876aff936";

        Runnable task = () -> {
            String json;
            String url = "http://api.aviationstack.com/v1/flights?access_key=" + apiKey + "&arr_iata=" + arrivalIata;
            json = httpGet(url);
            if (json == null) {
                return;
            }

            System.out.println("JSON reçu, longueur: " + json.length() + " caractères");
            JsonFlightFiller filler = new JsonFlightFiller(json, world);
            ArrayList<Flight> flights = filler.getList();
            System.out.println("Nombre de vols trouvés: " + flights.size());
            
            if (flights.isEmpty()) {
                System.out.println("Aucun vol trouvé. Vérifiez le parsing JSON.");
            } else {
                flights.forEach(flight -> {
                    Aeroport dep = flight.getDepartureAirport();
                    if (dep != null) {
                        System.out.println("Affichage sphère jaune pour: " + dep.getIATA());
                        earth.displayYellowSphere(dep);
                    } else {
                        System.out.println("Aéroport de départ non trouvé pour le code: " + flight.getDepartureIata());
                    }
                });
            }
        };
        new Thread(task, "aviationstack-fetch").start();
    }

    private String httpGet(String url) {
        try {
            System.out.println("Envoi de la requête HTTP vers: " + url);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Réponse HTTP reçue, code: " + response.statusCode());
            if (response.statusCode() != 200) {
                System.err.println("Erreur HTTP: " + response.statusCode() + " - " + response.body());
                return null;
            }
            return response.body();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel HTTP: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
