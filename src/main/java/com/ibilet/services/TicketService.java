package com.ibilet.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.ibilet.entities.Ticket;
import org.springframework.stereotype.Service;

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

    public List<Ticket> getTickets(){
        return null;
    }
}
