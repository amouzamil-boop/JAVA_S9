import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse une réponse JSON d'aviationstack pour en extraire les vols.
 * Convertit la chaîne JSON en flux (InputStream) et utilise une approche structurée
 * pour lire l'objet racine et parcourir le tableau "data".
 */
public class JsonFlightFiller {

    private final ArrayList<Flight> list = new ArrayList<>();

    public JsonFlightFiller(String jsonString, World w) {
        if (jsonString == null || jsonString.isEmpty()) {
            return;
        }

        try {
            // Convertit la chaîne JSON en flux (InputStream)
            InputStream inputStream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            
            // Lit le contenu du flux
            StringBuilder sb = new StringBuilder();
            try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                int c;
                while ((c = reader.read()) != -1) {
                    sb.append((char) c);
                }
            }
            String jsonContent = sb.toString();

            // Utilise une approche simple mais efficace pour extraire les vols
            parseSimple(jsonContent, w);
            
        } catch (Exception e) {
            // Gestion d'exceptions : ignore les erreurs de parsing
            System.err.println("Erreur lors de la lecture du JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Parse le JSON en utilisant une approche simple mais efficace pour extraire les vols.
     * Parcourt le tableau "data" et crée un objet Flight pour chaque vol valide.
     */
    private void parseSimple(String jsonContent, World w) {
        // Patterns pour extraire les informations de départ, arrivée et vol
        Pattern depPattern = Pattern.compile("\"departure\"\\s*:\\s*\\{[^}]*?\"iata\"\\s*:\\s*\"([A-Za-z0-9]{2,4})\"[^}]*?\"scheduled\"\\s*:\\s*\"([^\"]+)\"", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Pattern arrPattern = Pattern.compile("\"arrival\"\\s*:\\s*\\{[^}]*?\"iata\"\\s*:\\s*\"([A-Za-z0-9]{2,4})\"[^}]*?\"scheduled\"\\s*:\\s*\"([^\"]+)\"", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Pattern flightPattern = Pattern.compile("\"flight\"\\s*:\\s*\\{[^}]*?\"iata\"\\s*:\\s*\"([^\"]+)\"", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        Matcher depMatcher = depPattern.matcher(jsonContent);
        Matcher arrMatcher = arrPattern.matcher(jsonContent);
        Matcher flightMatcher = flightPattern.matcher(jsonContent);

        // Crée une liste temporaire pour les vols
        java.util.ArrayList<String[]> depMatches = new java.util.ArrayList<>();
        java.util.ArrayList<String[]> arrMatches = new java.util.ArrayList<>();
        java.util.ArrayList<String> flightMatches = new java.util.ArrayList<>();

        // Collecte tous les matches
        while (depMatcher.find()) {
            depMatches.add(new String[]{depMatcher.group(1), depMatcher.group(2)});
        }
        while (arrMatcher.find()) {
            arrMatches.add(new String[]{arrMatcher.group(1), arrMatcher.group(2)});
        }
        while (flightMatcher.find()) {
            flightMatches.add(flightMatcher.group(1));
        }

        // Associe les départures et arrivals (ils doivent être dans le même ordre)
        int minSize = Math.min(depMatches.size(), arrMatches.size());
        for (int i = 0; i < minSize; i++) {
            try {
                String depCode = depMatches.get(i)[0];
                String depDate = depMatches.get(i)[1];
                String arrCode = arrMatches.get(i)[0];
                String arrDate = arrMatches.get(i)[1];
                String flightId = (i < flightMatches.size()) ? flightMatches.get(i) : null;

                // Associe les codes IATA aux objets Aeroport via la classe World
                Aeroport dep = w.findByCode(depCode);
                Aeroport arr = w.findByCode(arrCode);

                // Crée un objet Flight pour chaque vol valide
                list.add(new Flight(dep, arr, depCode, arrCode, depDate, arrDate, flightId));
            } catch (Exception e) {
                // Gestion d'exceptions : ignore les enregistrements incomplets ou invalides
                // System.err.println("Erreur lors du parsing d'un vol: " + e.getMessage());
            }
        }
    }

    public ArrayList<Flight> getList() {
        return list;
    }
}

