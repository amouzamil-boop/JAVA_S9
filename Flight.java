public class Flight {
    private final Aeroport departureAirport;
    private final Aeroport arrivalAirport;
    private final String departureIata;
    private final String arrivalIata;
    private final String departureDate;
    private final String arrivalDate;
    private final String flightId;

    public Flight(Aeroport departureAirport, Aeroport arrivalAirport, String departureIata, String arrivalIata,
                  String departureDate, String arrivalDate, String flightId) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureIata = departureIata;
        this.arrivalIata = arrivalIata;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.flightId = flightId;
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

    public String getDepartureDate() {
        return departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getFlightId() {
        return flightId;
    }
}

