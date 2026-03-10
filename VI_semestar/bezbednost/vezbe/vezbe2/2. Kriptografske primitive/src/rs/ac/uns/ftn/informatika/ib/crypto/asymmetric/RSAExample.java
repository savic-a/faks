package rs.ac.uns.ftn.informatika.ib.crypto.asymmetric;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import rs.ac.uns.ftn.informatika.ib.util.Base64Utility;


// RSA(Rivest-Sharmir-Adleman) algoritam moze da kriptuje podatke maksimalne velicine do velicine kljuca (u bajtovima)
// Stoga se obicno koristi samo za kriptovanje tajnih kljuceva koji kriptuju podatke
public class RSAExample {
    public RSAExample() {
        // Bouncy Castle (https://www.bouncycastle.org) je provajder kriptografskih funkcionalnosti
        // Iako JDK dolazi sa odredjenim skupom kriptografskih primitiva, treba skrenuti paznju da postoje druga resenja za ovaj problem
        // Razliciti provajderi nude implementacije razlicitih skupova kriptografskih funkcija
        // U zavisnosti od potrebe, treba izabrati provajdera koji vazi za najpouzdanijeg za dat programski jezik
        // Za RSA u Javi, mi koristimo Bouncy Castle API
        Security.addProvider(new BouncyCastleProvider());
    }

    public void testIt() {
        PublicKey publicKey;
        PrivateKey privateKey;
        String data = "Ovo su podaci koji se kriptuju asimetricnom sifrom";

        System.out.println("===== Primer asimetricne RSA sifre =====");
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
        try {
            // Generator para kljuceva
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

            // Za kreiranje kljuceva neophodno je definisati generator pseudoslucajnih brojeva
            // Ovaj generator mora biti bezbedan (nije jednostavno predvideti koje brojeve ce RNG generisati)
            // U ovom primeru se koristi generator zasnovan na SHA1 algoritmu, gde je SUN provajder
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

            // inicijalizacija generatora, 2048 bitni kljuc
            keyGen.initialize(2048, random);

            // generise par kljuceva koji se sastoji od javnog i privatnog kljuca
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] encrypt(String plainText, PublicKey key) {
        try {
            // Kada se definise sifra potrebno je navesti njenu konfiguraciju, sto u ovom slucaju ukljucuje:
            //	- Algoritam koji se koristi (RSA)
            //	- Rezim rada tog algoritma (ECB)
            //	- Strategija za popunjavanje poslednjeg bloka (PKCS1Padding)
            //	- Provajdera kriptografskih funckionalnosti (BC)
            Cipher rsaCipherEnc = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

            // inicijalizacija za sifrovanje
            rsaCipherEnc.init(Cipher.ENCRYPT_MODE, key);

            // sifrovanje
            return rsaCipherEnc.doFinal(plainText.getBytes());
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
                | IllegalStateException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] decrypt(byte[] cipherText, PrivateKey key) {
        try {
            Cipher rsaCipherDec = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

            // inicijalizacija za desifrovanje
            rsaCipherDec.init(Cipher.DECRYPT_MODE, key);

            // desifrovanje
            return rsaCipherDec.doFinal(cipherText);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
                | IllegalStateException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        RSAExample test = new RSAExample();
        test.testIt();
    }
}
