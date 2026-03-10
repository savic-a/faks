package rs.ac.uns.ftn.informatika.ib.crypto.hash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import rs.ac.uns.ftn.informatika.ib.util.Base64Utility;

public class HashPasswordExample {
    public HashPasswordExample() {
    }

    public void testIt() {
        // TODO: Izuciti hash & salt mehanizam zastite korisnicke lozinke i implementirati takav mehanizam prateci najbolje prakse

        String password = "!pA55w0rd0ne";

        System.out.println("===== Generisanje salt-a =====");
        byte[] salt = generateSalt();
        System.out.println("Salt je: " + Base64Utility.encode(salt));

        System.out.println("===== Hesiranje lozinke =====");
        byte[] hashedPassword = hashPassword(password, salt);
        System.out.println("Hesirana lozinka je: " + Base64Utility.encode(hashedPassword));

        System.out.println("===== Korisnik unosi lozinku sa tastature =====");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Lozinka:");
        String attemptedPassword = "";
        try {
            attemptedPassword = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("===== Vrsi se provera da li je unesena ispravna lozinka =====");
        if (authenticate(attemptedPassword, hashedPassword, salt)) {
            System.out.println("Uspesna prijava :)");
        } else {
            System.out.println("Neuspesna prijava :(");

        }
    }

    private byte[] generateSalt() {
        // TODO: Implementirati generator salt-a prateci najbolje prakse.
        return null;
    }

    private byte[] hashPassword(String password, byte[] salt) {
        // TODO: Implementirati funkciju za hesiranje lozinke prateci najbolje prakse.
        return null;
    }

    private boolean authenticate(String attemptedPassword, byte[] storedPassword, byte[] salt) {
        // TODO: Proveriti da li je unesena lozinka (koja je u otvorenom tekstu) ista onoj koja je "uskladistena" (koja je zasticena hash & salt mehanizmom)
        return false;
    }

    public static void main(String[] args) {
        HashPasswordExample test = new HashPasswordExample();
        test.testIt();
    }
}
