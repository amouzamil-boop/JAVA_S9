import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Programme de test pour JsonFlightFiller.
 * Teste la lecture du fichier test.txt et la création des objets Flight.
 */
public class TestJsonFlightFiller {
    
    public static void main(String[] args) {
        try {
            // Lit le fichier test.txt contenant une réponse JSON statique
            String jsonContent = Files.readString(Paths.get("./src/data/test.txt"));
            System.out.println("Fichier test.txt lu avec succès (" + jsonContent.length() + " caractères).\n");
            
            // Crée une instance de World pour rechercher les aéroports
            World world = new World("./src/data/airport-codes_no_comma.csv");
            System.out.println("World initialisé avec " + world.getList().size() + " aéroports.\n");
            
            // Analyse le JSON avec JsonFlightFiller
            System.out.println("Analyse du JSON...");
            JsonFlightFiller filler = new JsonFlightFiller(jsonContent, world);
            ArrayList<Flight> flights = filler.getList();
            
            // Affiche les résultats
            System.out.println("Nombre de vols trouvés: " + flights.size() + "\n");
            
            // Affiche les détails des 5 premiers vols pour vérification
            int maxDisplay = Math.min(5, flights.size());
            System.out.println("Détails des " + maxDisplay + " premiers vols:");
            for (int i = 0; i < 80; i++) System.out.print("=");
            System.out.println();
            
            for (int i = 0; i < maxDisplay; i++) {
                Flight flight = flights.get(i);
                System.out.println("\nVol " + (i + 1) + ":");
                System.out.println("  Identifiant: " + (flight.getFlightId() != null ? flight.getFlightId() : "N/A"));
                System.out.println("  Départ: " + flight.getDepartureIata() + 
                                 (flight.getDepartureAirport() != null ? " (" + flight.getDepartureAirport().getName() + ")" : " (aéroport non trouvé)"));
                System.out.println("  Date départ: " + (flight.getDepartureDate() != null ? flight.getDepartureDate() : "N/A"));
                System.out.println("  Arrivée: " + flight.getArrivalIata() + 
                                 (flight.getArrivalAirport() != null ? " (" + flight.getArrivalAirport().getName() + ")" : " (aéroport non trouvé)"));
                System.out.println("  Date arrivée: " + (flight.getArrivalDate() != null ? flight.getArrivalDate() : "N/A"));
            }
            
            // Vérifie l'absence d'erreurs
            System.out.println();
            for (int i = 0; i < 80; i++) System.out.print("=");
            System.out.println();
            System.out.println("Test terminé avec succès. Aucune erreur détectée lors du parsing.");
            
        } catch (Exception e) {
            System.err.println("Erreur lors du test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

