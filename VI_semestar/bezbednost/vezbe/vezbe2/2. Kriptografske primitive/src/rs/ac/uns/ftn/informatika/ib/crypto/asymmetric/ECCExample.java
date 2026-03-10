package rs.ac.uns.ftn.informatika.ib.crypto.asymmetric;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import rs.ac.uns.ftn.informatika.ib.util.Base64Utility;

public class ECCExample {
    public ECCExample() {
    }

    public void testIt() {
        PublicKey publicKey;
        PrivateKey privateKey;
        String data = "Ovo su podaci koji se kriptuju asimetricnom sifrom";

        System.out.println("===== Primer asimetricne ECC sifre =====");
        System.out.println("Podaci koji se sifruju: " + data);

        System.out.println("\n===== Generisanje kljuceva =====");
        KeyPair kp = generateKeys();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();
        System.out.println("Generisan javni kljuc: " + Base64Utility.encode(publicKey.getEncoded()));
        System.out.println("Generisan privatni kljuc: " + Base64Utility.encode(privateKey.getEncoded()));

        System.out.println("\n===== Sifrovanje javnim kljucem =====");
        byte[] cipherText = encrypt(data, publicKey);
        System.out.println("Sifrat: " + Base64Utility.encode(cipherText));

        System.out.println("\n===== Desifrovanje privatnim kljucem =====");
        byte[] plainText = decrypt(cipherText, privateKey);
        System.out.println("Originalna poruka: " + new String(plainText));
    }

    private KeyPair generateKeys() {
        // TODO: Generisati i vratiti ECC par kljuceva duzine koju diktira najbolja praksa
        return null;
    }

    private byte[] encrypt(String plainText, PublicKey key) {
        // TODO: Sifrovati otvoren tekst uz pomoc javnog kljuca koristeci konfiguraciju ECC algoritma koju diktira najbolja praksa
        return null;
    }

    private byte[] decrypt(byte[] cipherText, PrivateKey key) {
        // TODO: Desifrovati sifru uz pomoc privatnog kljuca koristeci konfiguraciju ECC algoritma koju diktira najbolja praksa
        return null;
    }

    public static void main(String[] args) {
        ECCExample test = new ECCExample();
        test.testIt();
    }
}
