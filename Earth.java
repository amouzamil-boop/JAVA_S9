import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

/**
 * Représentation 3D simplifiée de la Terre.
 */
public class Earth extends Group {

    private static final double RADIUS = 300d;
    private static final String TEXTURE_PATH = "file:src/data/earth_lights_4800.png";

    private final Sphere sphere;
    private final Rotate rotationY;
    private final Group rotatingGroup; // Groupe qui contient la Terre et toutes les sphères d'aéroports

    public Earth() {
        // Créer un groupe qui tournera avec la Terre
        rotatingGroup = new Group();
        rotationY = new Rotate(0, Rotate.Y_AXIS);
        rotatingGroup.getTransforms().add(rotationY);
        
        sphere = new Sphere(RADIUS);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(loadTexture());
        sphere.setMaterial(material);

        // Ajouter la sphère principale au groupe rotatif
        rotatingGroup.getChildren().add(sphere);
        
        // Ajouter le groupe rotatif au groupe principal
        this.getChildren().add(rotatingGroup);
        
        startRotation();
    }

    private Image loadTexture() {
        try {
            return new Image(TEXTURE_PATH);
        } catch (Exception e) {
            System.err.println("Impossible de charger la texture de la Terre: " + e.getMessage());
            return null;
        }
    }

    private void startRotation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                rotationY.setAngle((now / 1e9) * (360d / 15d)); // 1 tour en 15s
            }
        };
        timer.start();
    }

    /**
     * Crée une petite sphère positionnée sur le globe à la latitude/longitude
     * de l'aéroport passé en paramètre.
     */
    public Sphere createSphere(Aeroport a, Color color) {
        Sphere sp = new Sphere(2);
        PhongMaterial mat = new PhongMaterial(color);
        sp.setMaterial(mat);

        double theta = Math.toRadians(a.getLatitude());
        double phi = Math.toRadians(a.getLongitude());

        double x = RADIUS * Math.cos(theta) * Math.sin(phi);
        double y = -RADIUS * Math.sin(theta);
        double z = -RADIUS * Math.cos(theta) * Math.cos(phi);

        sp.setTranslateX(x);
        sp.setTranslateY(y);
        sp.setTranslateZ(z);
        
        // Les sphères seront ajoutées au groupe rotatif, donc elles tourneront automatiquement avec la Terre
        return sp;
    }

    public void displayRedSphere(Aeroport a) {
        if (a == null) {
            return;
        }
        Platform.runLater(() -> rotatingGroup.getChildren().add(createSphere(a, Color.RED)));
    }

    public void displayYellowSphere(Aeroport a) {
        if (a == null) {
            return;
        }
        Platform.runLater(() -> rotatingGroup.getChildren().add(createSphere(a, Color.GOLD)));
    }

    public Sphere getSphere() {
        return sphere;
    }
}

