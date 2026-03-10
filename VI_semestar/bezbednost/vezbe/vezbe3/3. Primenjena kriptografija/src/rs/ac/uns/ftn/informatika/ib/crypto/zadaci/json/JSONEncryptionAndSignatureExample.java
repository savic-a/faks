package rs.ac.uns.ftn.informatika.ib.crypto.zadaci.json;

import rs.ac.uns.ftn.informatika.ib.crypto.primeri.pki.keystores.KeyStoreReader;

public class JSONEncryptionAndSignatureExample {

    public void testIt() {
        String originalFile = "./files/data/json/univerzitet.json";
        String encryptedFile = "./files/data/json/univerzitet_encrypted.json";
        String signedFile = "./files/data/json/univerzitet_signed.json";
        String endFile = "./files/data/json/univerzitet_end.json";
        String keyStoreFile = "./files/pki/keystores/primer.jks";

        KeyStoreReader ksReader = new KeyStoreReader();

        // TODO: Dizajnirati i implementirati sifrovanje i digitalno potpisivanje JSON objekta
    }

    public static void main(String[] args) {
        JSONEncryptionAndSignatureExample xml = new JSONEncryptionAndSignatureExample();
        xml.testIt();
    }

}
