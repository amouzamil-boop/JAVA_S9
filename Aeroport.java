import java.lang.Math;

public class Aeroport {

    // 1. Les attributs (champs) de la classe
    private String IATA;
    private String Name;
    private String country;
    private double latitude;
    private double longitude;

    // 2. Le constructeur pour créer un objet Aeroport
    public Aeroport(String IATA, String Name, String country, double latitude, double longitude) {
        this.IATA = IATA;
        this.Name = Name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // 3. Les "getters" pour accéder aux attributs
    public String getIATA() {
        return IATA;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return Name;
    }

    public String getCountry() {
        return country;
    }

    // 4. La méthode toString() pour les tests
    @Override
    public String toString() {
        return "Aeroport{" +
                "IATA='" + IATA + '\'' +
                ", Name='" + Name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    // 5. La méthode de calcul de distance
    /**
     * Calcule la "norme" de distance simplifiée entre cet aéroport
     * et un autre aéroport 'a'.
     * @param a L'autre aéroport
     * @return La valeur de la norme (pas une distance réelle)
     */
    public double calculDistance(Aeroport a) {
        // Formule: norme = (Θ2−Θ1)² + ((Φ2−Φ1)cos((Θ2+Θ1)/2))²
        // Où Θ = latitude et Φ = longitude

        // (Θ1, Φ1) correspondent à 'this'
        double lat1 = this.latitude;
        double lon1 = this.longitude;

        // (Θ2, Φ2) correspondent à 'a'
        double lat2 = a.getLatitude();
        double lon2 = a.getLongitude();

        // (Θ2−Θ1)
        double dLat = lat2 - lat1;
        // (Φ2−Φ1)
        double dLon = lon2 - lon1;
        // (Θ2+Θ1)/2
        double avgLat = (lat2 + lat1) / 2.0;

        // Math.cos attend des radians
        double cosAvgLat = Math.cos(Math.toRadians(avgLat));

        // ((Φ2−Φ1)cos((Θ2+Θ1)/2))
        double lonFactor = dLon * cosAvgLat;

        // (dLat)² + (lonFactor)²
        double norme = (dLat * dLat) + (lonFactor * lonFactor);

        return norme;
    }
}
