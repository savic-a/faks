package rs.ac.uns.ftn.informatika.ib.crypto.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import rs.ac.uns.ftn.informatika.ib.util.Base64Utility;

public class HashExample {
    public HashExample() {
    }

    public void testIt() {
        String data = "Hes kod se koristi za proveru integriteta podataka. Ako posaljemo poruku zajedno sa njenim hesom druga strana moze da izracuna hes pristigle poruke i proveri da li se podudaraju.";

        System.out.println("===== Primer SHA-256 hes funkcije u sluzbi provere integriteta =====");
        System.out.println("Podaci ciji integritet se proverava: " + data);

        System.out.println("\n===== Generisanje hesa =====");
        byte[] dataHash = hash(data);
        System.out.println("Generisan hes: " + Base64Utility.encode(dataHash));

        System.out.println("\n===== Provera integriteta kada on nije narusen =====");
        if (checkIntegrity(data, dataHash)) {
            System.out.println("Integritet nije narusen :)");
        } else {
            System.out.println("Integritet jeste narusen :(");
        }

        System.out.println("\n===== Provera integriteta kada on jeste narusen =====");
        data = data + "Sve prethodno je laz.";
        if (checkIntegrity(data, dataHash)) {
            System.out.println("Integritet nije narusen :)");
        } else {
            System.out.println("Integritet jeste narusen :(");
        }
    }

    // Metoda koja proverava da li je ostecen integritet poruke
    private boolean checkIntegrity(String data, byte[] dataHash) {
        byte[] newDataHash = hash(data);
        return Arrays.equals(dataHash, newDataHash);
    }

    public byte[] hash(String data) {
        // Kao hes funkcija koristi SHA-256
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            return sha256.digest(data.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        HashExample test = new HashExample();
        test.testIt();
    }
}
