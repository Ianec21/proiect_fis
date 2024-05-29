package com.ibilet.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.ibilet.entities.Flight;
import com.ibilet.entities.FlightDTO;
import com.ibilet.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FlightService {
    @Autowired
    UserService userService;

    private static final String COLLECTION_NAME = "flights";

    public String createFlight(Flight flight) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        System.out.println(flight);
        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(flight.getCode());
        ApiFuture<WriteResult> writeResult = documentReference.set(flight);
        return writeResult.get().getUpdateTime().toString();
    }

    public List<FlightDTO> getAllFlights() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference flightsCollection = dbFirestore.collection(COLLECTION_NAME);

        // Retrieve all documents in the "flights" collection
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

        System.out.println(flights);
        return flights;
    }

    public Flight getFlightById(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = db.collection("flights").whereEqualTo("code", id).get();
        if (query.get().isEmpty()) {
            return null;
        }

        return query.get().getDocuments().get(0).toObject(Flight.class);
    }
}
