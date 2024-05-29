package com.ibilet.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ibilet.entities.Flight;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

    private final DatabaseReference databaseReference;

    public FlightService() {
        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference("flights"); // "flights" is the reference to the flights node in the database
    }

    public void saveFlight(Flight flight) {
        // Generate a unique key for the flight
        String flightId = databaseReference.push().getKey();
        // Set flight data
        databaseReference.child(flightId).setValueAsync(flight);
    }
}
