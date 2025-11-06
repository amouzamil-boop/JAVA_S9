public class Main {
    public static void main(String[] args) {
        // Si tu déplaces "data" à la racine du projet, garde ce chemin :
        World w = new World("./data/airport-codes_no_comma.csv");

        System.out.println("Found " + w.getList().size() + " airports.");

        Aeroport paris = w.findNearestAirport(2.316, 48.866); // Paris
        Aeroport cdg = w.findByCode("CDG");

        if (paris != null) {
            double distance = w.distance(2.316, 48.866, paris.getLongitude(), paris.getLatitude());
            System.out.println(paris);
            System.out.println(distance);
        }
        if (cdg != null) {
            double distanceCDG = w.distance(2.316, 48.866, cdg.getLongitude(), cdg.getLatitude());
            System.out.println(cdg);
            System.out.println(distanceCDG);
        }
    }
}
