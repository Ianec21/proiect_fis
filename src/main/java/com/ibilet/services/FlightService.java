package com.ibilet.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.ibilet.entities.Flight;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FlightService {

    private static final String COLLECTION_NAME = "flights";

    public String createFlight(Flight flight) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(flight.getCode());
        ApiFuture<WriteResult> writeResult = documentReference.set(flight);
        return writeResult.get().getUpdateTime().toString();
    }

    public List<Flight> getAllFlights() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference flightsCollection = dbFirestore.collection(COLLECTION_NAME);

        // Retrieve all documents in the "flights" collection
        ApiFuture<QuerySnapshot> future = flightsCollection.get();
        QuerySnapshot snapshot = future.get();

        List<Flight> flights = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            Flight flight = document.toObject(Flight.class);
            flights.add(flight);
        }
        return flights;
    }
}
