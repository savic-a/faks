let firebaseUrl = 'https://turisticke-atrakcije-default-rtdb.europe-west1.firebasedatabase.app';

let korisnikId = dobaviVrednost('id');
let korisnik = {};
let zahtev = new XMLHttpRequest();

zahtev.onreadystatechange = function(){
    if(this.readyState == 4){
        if(this.status == 200){
            korisnik = JSON.parse(this.responseText);

            podesiPodatke(korisnik);

        }else{
            alert('Greška prilikom učitavanja korisnika!')
        }
    }
}
zahtev.open('GET', firebaseUrl.concat('/korisnici/', korisnikId, '.json'));
zahtev.send();

let izmeniDgm = document.getElementById('izmena');
izmeniDgm.addEventListener('click', function(e){
    let id = korisnikId;
    window.location.href = 'izmena-korisnika.html?id=' + id;
});

function podesiPodatke(korisnik){
    let korisnickoIme = document.getElementById('korisnik');
    korisnickoIme.value = korisnik.username;
    korisnickoIme.disabled = true;

    let lozinka = document.getElementById('lozinka');
    lozinka.value = korisnik.password; 
    lozinka.disabled = true; 
    
    let mejl = document.getElementById('mejl');
    mejl.value = korisnik.email;
    mejl.disabled = true;

    let ime = document.getElementById('ime');
    ime.value = korisnik.ime;
    ime.disabled = true;
    
    let prezime = document.getElementById('prezime');
    prezime.value = korisnik.prezime;
    prezime.disabled = true;

    let datum = document.getElementById('datum');
    datum.value = korisnik.datumRodjenja; 
    datum.disabled = true;

    let adresa = document.getElementById('adresa');
    adresa.value = korisnik.adresa;
    adresa.disabled = true;

    let brTel = document.getElementById('tel');
    brTel.value = korisnik.telefon; 
    brTel.disabled = true;
}

function dobaviVrednost(parametar) {
    let lokacija = decodeURI(window.location.toString());
    let indeks = lokacija.indexOf("?") + 1;
    let subs = lokacija.substring(indeks, lokacija.length);
    let split = subs.split("&");

    for (i = 0; i < split.length; i++) {
        let s = split[i].split("=");
        let id = s[0];
        let vrednost = s[1];
        if (id == parametar) {
            return vrednost;
        }
    }
}