public class Flight {
    private final Aeroport departureAirport;
    private final Aeroport arrivalAirport;
    private final String departureIata;
    private final String arrivalIata;

    public Flight(Aeroport departureAirport, Aeroport arrivalAirport, String departureIata, String arrivalIata) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureIata = departureIata;
        this.arrivalIata = arrivalIata;
    }

    public Aeroport getDepartureAirport() {
        return departureAirport;
    }

    public Aeroport getArrivalAirport() {
        return arrivalAirport;
    }

    public String getDepartureIata() {
        return departureIata;
    }

    public String getArrivalIata() {
        return arrivalIata;
    }
}

