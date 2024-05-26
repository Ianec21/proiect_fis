package com.ibilet;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class IbiletApplication {

	public static void main(String[] args) {
		File file = new File(IbiletApplication.class.getClassLoader().getResource("firebase_credentials.json").getFile());

		try {
			FileInputStream firebaseCredentials = new FileInputStream(file.getAbsolutePath());

			//check if there are no created apps already, otherwise error is thrown.
			if(FirebaseApp.getApps().isEmpty()){
				FirebaseOptions options = new FirebaseOptions.Builder()
						.setCredentials(GoogleCredentials.fromStream(firebaseCredentials))
						.build();

				FirebaseApp.initializeApp(options);
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

        SpringApplication.run(IbiletApplication.class, args);
	}

}
