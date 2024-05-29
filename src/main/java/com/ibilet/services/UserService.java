package com.ibilet.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.ibilet.entities.User;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    public String createUser(User user) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        String uniqueID = UUID.randomUUID().toString();
        user.setId(uniqueID); // Set the ID field
        ApiFuture<WriteResult> docRef = db.collection("users").document(uniqueID).set(user);
        return docRef.get().getUpdateTime().toString();
    }

    public User getUserByUsername(String username) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = db.collection("users").whereEqualTo("username", username).get();
        if (query.get().isEmpty()) {
            return null;
        }
        return query.get().getDocuments().get(0).toObject(User.class);
    }
}
