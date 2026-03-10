package rs.ac.uns.ftn.informatika.ib.crypto.zadaci.communication;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;


/**
 * Generise i proverava digitalni potpis
 */
public class SecureCommunication {

    public void testIt() {
        String message = "Ovo su podaci koje Alisa treba da posalje Bobu";

        // TODO: Implementirati pozivanje operacija tako da se simulira sifrovanje i digitalno potpisivanje poruke od strane Alise i desifrovanje i provera digitalnog potpisa od strane Boba
        // Dozvoljeno je kreiranje nove klase za DTO (data transfer object) ukoliko ima potrebe
    }

    private SecretKey generateSecretKey() {
        // TODO: Generisati i vratiti AES kljuc duzine koju diktira najbolja praksa
        return null;
    }

    private KeyPair generateKeyPair() {
        // TODO: Generisati i vratiti RSA kljuceve duzine koju diktira najbolja praksa
        return null;
    }

    private byte[] encrypt(String plainText, PublicKey publicKey) {
        // TODO: Sifrovati otvoren tekst uz pomoc kombinacije simetricne i asimetricne sifre koju diktira najbolja praksa
        return null;
    }

    private byte[] decrypt(byte[] cipherText, SecretKey key) {
        // TODO: Desifrovati sifrat uz pomoc kombinacije simetricne i asimetricne sifre koju diktira najbolja praksa
        return null;
    }

    private byte[] sign(byte[] data, PrivateKey privateKey) {
        // TODO: Izvrsiti digitalno potpisivanje prateci najbolje prakse
        return null;
    }

    private boolean verify(byte[] data, byte[] signature, PublicKey publicKey) {
        // TODO: Izvrsiti proveru digitalnog potpisa
        return false;
    }

    public static void main(String[] args) {
        SecureCommunication sec = new SecureCommunication();
        sec.testIt();
    }
}
