package rs.ac.uns.ftn.informatika.ib.crypto.symmetric;

import javax.crypto.SecretKey;

import rs.ac.uns.ftn.informatika.ib.util.Base64Utility;

// Primer simetricnog sifrovanja
// Moze da se sifruje podatak prozivoljne duzine
public class AESExample {
    public AESExample() {
    }

    public void testIt() {
        SecretKey secretKey;
        String data = "Ovo su podaci koji se kriptuju simetricnim AES algoritmom, duzina podataka nije bitna, tj. AES moze da se koristi za proizvoljnu duzinu podataka";

        System.out.println("===== Primer simetricne AES sifre =====");
        System.out.println("Podaci koji se sifruju: " + data);

        System.out.println("\n===== Generisanje kljuca =====");
        secretKey = generateKey();
        System.out.println("Generisan kljuc: " + Base64Utility.encode(secretKey.getEncoded()));

        System.out.println("\n===== Sifrovanje =====");
        byte[] cipherText = encrypt(data, secretKey);
        System.out.println("Sifrat: " + Base64Utility.encode(cipherText));

        System.out.println("\n===== Desifrovanje =====");
        byte[] plainText = decrypt(cipherText, secretKey);
        System.out.println("Originalna poruka: " + new String(plainText));
    }

    private SecretKey generateKey() {
        //TODO: Generisati i vratiti AES kljuc duzine koju diktira najbolja praksa
        return null;
    }

    private byte[] encrypt(String plainText, SecretKey key) {
        //TODO: Sifrovati otvoren tekst uz pomoc tajnog kljuca koristeci konfiguraciju AES algoritma koju diktira najbolja praksa
        return null;
    }

    private byte[] decrypt(byte[] cipherText, SecretKey key) {
        //TODO: Desifrovati sifru uz pomoc tajnog kljuca koristeci konfiguraciju AES algoritma koju diktira najbolja praksa
        return null;
    }

    public static void main(String[] args) {
        AESExample test = new AESExample();
        test.testIt();
    }
}
