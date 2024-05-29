package com.ibilet.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.ibilet.entities.Flight;
import com.ibilet.entities.FlightDTO;
import com.ibilet.entities.FlightFilter;
import com.ibilet.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FlightFilterService {

    @Autowired
    UserService userService;
    private static final String COLLECTION_NAME = "flights";

    public List<FlightDTO> FilterFlights(String arrivalCity,String reservationType, String departureDate,String arrivalDate,int children,int adults, String departureCity, String flightType) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference flightsCollection = dbFirestore.collection(COLLECTION_NAME);

        ApiFuture<QuerySnapshot> future = flightsCollection.get();
        QuerySnapshot snapshot = future.get();

        List<FlightDTO> flights = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            FlightDTO flight = document.toObject(FlightDTO.class);

            if(flight != null){
                User company = userService.getUserById(flight.getCompanyId());
                System.out.println(company.getUsername());
                flight.setCompanyName(company.getUsername());

                flights.add(flight);
            }
        }

        int seatNumber = adults+children;
        List<FlightDTO> filteredFlight;
        filteredFlight = flights.stream()
                .filter(flight -> flight.getArrivalCity().equals(arrivalCity))
                .filter(flight -> flight.getDepartureDate().equals(departureDate))
                //.filter(flight -> flight.getSeatNumber() >= seatNumber)
                .filter(flight -> flight.getDepartureCity().equals(departureCity))
                //.filter(flight -> flight.getFlightType().equals(flightType))
                .filter(flight -> flight.getFlightType() == Flight.FlightType.valueOf(flightType))
                .toList();

        return filteredFlight;
    }

}
