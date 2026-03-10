package rs.ac.uns.ftn.informatika.ib.crypto.zadaci.pki;

import java.util.Scanner;

public class KeyTool {
    public static void main(String[] args) {
        KeyTool kt = new KeyTool();
        System.out.println("===== Konzolna aplikacija za upravljanje sertifikatima i kljucevima =====");
        Scanner keyboard = new Scanner(System.in);
        int choice = 0;
        do {
            kt.menu();
            choice = keyboard.nextInt();
            switch (choice) {
                case 1: {
                    kt.createNewKeyStore();
                    break;
                }
                case 2: {
                    kt.showKeyStoreContent();
                    break;
                }
                case 3: {
                    kt.createNewSelfSignedCertificate();
                    break;
                }
                case 4: {
                    kt.createNewIssuedCertificate();
                    break;
                }
            }
        } while (choice != 5);
        keyboard.close();
    }

    private void createNewKeyStore() {
        // TODO: Upotrebom klasa iz primeri/pki paketa, implementirati funkciju gde korisnik unosi ime keystore datoteke i ona se kreira

    }

    private void showKeyStoreContent() {
        // TODO: Upotrebom klasa iz primeri/pki paketa, prikazati sadrzaj keystore-a, gde korisnik unosi ime i lozinku
        // Dozvoljeno je menjati klase iz primeri/pki paketa

    }

    private void createNewSelfSignedCertificate() {
        // TODO: Upotrebom klasa iz primeri/pki paketa, prikazati sadrzaj keystore-a, gde korisnik unosi sve potrebne podatke
        // Radi ustede hardkodovati podatke vezane za subjekta sertifikata
    }

    private void createNewIssuedCertificate() {
        // TODO: Upotrebom klasa iz primeri/pki paketa, prikazati sadrzaj keystore-a, gde korisnik unosi sve potrebne podatke
        // Radi ustede vremena hardkodovati podatke vezane za subjekta sertifikata
    }

    private void menu() {
        System.out.println("==================================");
        System.out.println("1.	Create new key store");
        System.out.println("2.	Show key store content");
        System.out.println("3.	Create new self signed certificate");
        System.out.println("4.	Create new issued certificate");
        System.out.println("5.	Exit");
        System.out.print(">>>");
    }
}
