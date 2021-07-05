package com.wallet.erp.services;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseInitializer {
	
	private FirebaseApp fireApp; 
	
	private Storage storage; 
	
	public static final String BUCKET_NAME = "fir-17bc3.appspot.com"; 
	
	@PostConstruct
	private void initDB() throws IOException {
		InputStream serviceAccount =
				  this.getClass().getClassLoader()
				  .getResourceAsStream("./fir-17bc3-firebase-adminsdk-ehv71-c560a7a706.json");
		
		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount); 
		
		FirebaseOptions options = FirebaseOptions.builder()
				  .setCredentials(credentials)
				  .build();

		fireApp = FirebaseApp.initializeApp(options);
		
		storage = StorageOptions.newBuilder().setCredentials(credentials)
					 .setProjectId("fir-17bc3")
					 .build()
					 .getService();
	}
	
	public Storage getStorage() {
		return storage; 
	}
	
	public Firestore getFirebase() {
		return FirestoreClient.getFirestore(); 
	}
}
