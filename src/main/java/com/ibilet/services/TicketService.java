package com.ibilet.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.ibilet.entities.Ticket;
import com.ibilet.entities.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class TicketService {
    public String createTicket(Ticket ticket) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        String uniqueID = UUID.randomUUID().toString();
        ticket.setId(uniqueID);
        ApiFuture<WriteResult> docRef = db.collection("tickets").document(uniqueID).set(ticket);

        return docRef.get().getUpdateTime().toString();
    }

    public List<Ticket> getUnpaidTickets() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference flightsCollection = dbFirestore.collection("tickets");

        // Retrieve all documents in the "flights" collection
        ApiFuture<QuerySnapshot> future = flightsCollection.get();
        QuerySnapshot snapshot = future.get();

        List<Ticket> tickets = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            Ticket ticket = document.toObject(Ticket.class);

            if(ticket != null && ticket.getPaymentMethod() == Ticket.PaymentMethod.CASH && !ticket.isValidatedCashPayment()){
                tickets.add(ticket);
            }
        }

        return tickets;
    }

    public Ticket getTicketById(String ticketId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = db.collection("tickets").whereEqualTo("id", ticketId).get();
        if (query.get().isEmpty()) {
            return null;
        }

        return query.get().getDocuments().get(0).toObject(Ticket.class);
    }

    public void validateTicket(Ticket ticket) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ticket.setValidatedCashPayment(true);
        ApiFuture<WriteResult> docRef = db.collection("tickets").document(ticket.getId()).set(ticket);
        docRef.get().getUpdateTime().toString();
    }

    public List<Ticket> getTickets(){
        return null;
    }
}
