package com.library.firebaselibrary.helpers;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.library.firebaselibrary.Exceptions.InternalServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirestoreInit {

    @Value("${api.projectId}")
    private String projectId;

    private Firestore firestore;

    public Firestore getFirestore() {

        if(firestore != null) return firestore;
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("creds.json");
            GoogleCredentials credentials = null;
            credentials = GoogleCredentials.fromStream(is);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setProjectId(projectId)
                    .build();
            FirebaseApp.initializeApp(options);

            firestore = FirestoreClient.getFirestore();
            return firestore;

        } catch (IOException e) {
            throw new InternalServerException();
        }

    }
}
