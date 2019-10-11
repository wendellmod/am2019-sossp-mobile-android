package br.com.sossp.sosspapp.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfigurationFirebase {

    private static FirebaseAuth firebaseAuth;

    public static FirebaseAuth getFirebaseAuth() {

        if( firebaseAuth == null ){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;

    }

}
