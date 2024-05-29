package com.ibilet.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.ibilet.entities.Flight;
import com.ibilet.entities.FlightFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FlightFilterService {

    private static final String COLLECTION_NAME = "flights";

    public List<Flight> FilterFlights(String arrivalCity,String reservationType, String departureDate,String arrivalDate, int seatNumber, String departureCity, String flightType) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference flightsCollection = dbFirestore.collection(COLLECTION_NAME);

        ApiFuture<QuerySnapshot> future = flightsCollection.get();
        QuerySnapshot snapshot = future.get();

        List<Flight> flights = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            Flight flight = document.toObject(Flight.class);
            flights.add(flight);
        }
        List<Flight> filteredFlight;
        filteredFlight = flights.stream()
                .filter(flight -> flight.getArrivalCity().equals(arrivalCity))
                .filter(flight -> flight.getDepartureHour().equals(departureDate))
                .filter(flight -> flight.getDepartureHour().equals(arrivalDate))
                //.filter(flight -> flight.get() >= seatNumber)
                .filter(flight -> flight.getDepartureCity().equals(departureCity))
                //.filter(flight -> flight.getFlightType().equals(flightType))
                .toList();

        return filteredFlight;
    }

}
