package rs.ac.uns.ftn.informatika.ib.crypto.symmetric;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import rs.ac.uns.ftn.informatika.ib.util.Base64Utility;

// Primer simetricnog sifrovanja
// Moze da se sifruje podatak prozivoljne duzine
public class DESExample {
    public DESExample() {
    }

    public void testIt() {
        String data = "Ovo su podaci koji se kriptuju simetricnim DES algoritmom, duzina podataka nije bitna, tj. DES moze da se koristi za proizvoljnu duzinu podataka";

        System.out.println("===== Primer simetricne DES sifre =====");
        System.out.println("Podaci koji se sifruju: " + data);

        System.out.println("\n===== Generisanje kljuca =====");
        SecretKey secretKey = generateKey();
        System.out.println("Generisan kljuc: " + Base64Utility.encode(secretKey.getEncoded()));

        System.out.println("\n===== Sifrovanje =====");
        byte[] cipherText = encrypt(data, secretKey);
        System.out.println("Sifrat: " + Base64Utility.encode(cipherText));

        System.out.println("\n===== Desifrovanje =====");
        byte[] plainText = decrypt(cipherText, secretKey);
        System.out.println("Originalna poruka: " + new String(plainText));
    }

    private SecretKey generateKey() {
        try {
            // generator para kljuceva za DES algoritam
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            // generise kljuc za DES
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] encrypt(String plainText, SecretKey key) {
        try {
            // Kada se definise sifra potrebno je navesti njenu konfiguraciju, sto u ovom slucaju ukljucuje:
            //	- Algoritam koji se koristi (DES)
            //	- Rezim rada tog algoritma (ECB)
            //	- Strategija za popunjavanje poslednjeg bloka (PKCS5Padding)
            Cipher desCipherEnc = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // inicijalizacija za sifrovanje
            desCipherEnc.init(Cipher.ENCRYPT_MODE, key);

            // sifrovanje
            return desCipherEnc.doFinal(plainText.getBytes());
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalStateException
                | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] decrypt(byte[] cipherText, SecretKey key) {
        try {
            // algoritam MORA biti isti kao i kod sifrovanja
            Cipher desCipherDec = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // inicijalizacija za dekriptovanje
            desCipherDec.init(Cipher.DECRYPT_MODE, key);

            // desifrovanje
            return desCipherDec.doFinal(cipherText);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalStateException
                | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        DESExample test = new DESExample();
        test.testIt();
    }
}
