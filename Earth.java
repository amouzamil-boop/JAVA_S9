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

    public Earth() {
        sphere = new Sphere(RADIUS);
        rotationY = new Rotate(0, Rotate.Y_AXIS);
        sphere.getTransforms().add(rotationY);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(loadTexture());
        sphere.setMaterial(material);

        this.getChildren().add(sphere);
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
        return sp;
    }

    public void displayRedSphere(Aeroport a) {
        if (a == null) {
            return;
        }
        Platform.runLater(() -> this.getChildren().add(createSphere(a, Color.RED)));
    }

    public void displayYellowSphere(Aeroport a) {
        if (a == null) {
            return;
        }
        Platform.runLater(() -> this.getChildren().add(createSphere(a, Color.GOLD)));
    }

    public Sphere getSphere() {
        return sphere;
    }
}

