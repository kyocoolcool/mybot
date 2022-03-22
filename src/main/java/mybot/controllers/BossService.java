package mybot.controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class BossService {

    public String createBoss(Boss boss) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("furseal").document(boss.getBossId()).set(boss);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Boss getBoss(String documentId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("furseal").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Boss boss;
        if (document.exists()) {
            boss = document.toObject(Boss.class);
            return boss;
        }
        return null;
    }

    public String updateBoss(Boss boss) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("furseal").document(boss.getBossId()).set(boss);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }


    public String deleteBoss(String documentId) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("furseal").document(documentId).delete();
        return "Successfully deleted " + documentId;
    }

    public List<Boss> getAllBoss() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReferenceList = dbFirestore.collection("furseal").listDocuments();
        Iterator<DocumentReference> iterator = documentReferenceList.iterator();
        ArrayList<Boss> bosses = new ArrayList<>();
        while (iterator.hasNext()) {
            DocumentReference next = iterator.next();
            Boss boss;
            DocumentSnapshot document = next.get().get();
            if (document.exists()) {
                boss = document.toObject(Boss.class);
                bosses.add(boss);
            }
        }
        return bosses;
    }
}
