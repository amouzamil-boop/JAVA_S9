import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse une réponse JSON d'aviationstack pour en extraire les vols.
 * Implémentation simplifiée (sans dépendance externe JSON).
 */
public class JsonFlightFiller {

    private final ArrayList<Flight> list = new ArrayList<>();

    public JsonFlightFiller(String jsonString, World w) {
        if (jsonString == null || jsonString.isEmpty()) {
            return;
        }

        Pattern depPattern = Pattern.compile("\"departure\"\\s*:\\s*\\{[^}]*?\"iata\"\\s*:\\s*\"([A-Za-z0-9]{2,4})\"", Pattern.CASE_INSENSITIVE);
        Pattern arrPattern = Pattern.compile("\"arrival\"\\s*:\\s*\\{[^}]*?\"iata\"\\s*:\\s*\"([A-Za-z0-9]{2,4})\"", Pattern.CASE_INSENSITIVE);

        Matcher depMatcher = depPattern.matcher(jsonString);
        Matcher arrMatcher = arrPattern.matcher(jsonString);

        while (depMatcher.find() && arrMatcher.find()) {
            String depCode = depMatcher.group(1);
            String arrCode = arrMatcher.group(1);

            Aeroport dep = w.findByCode(depCode);
            Aeroport arr = w.findByCode(arrCode);

            list.add(new Flight(dep, arr, depCode, arrCode));
        }
    }

    public ArrayList<Flight> getList() {
        return list;
    }
}

