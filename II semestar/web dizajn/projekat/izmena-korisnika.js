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

let izmeniForma = document.getElementById('izmeniForma');
izmeniForma.addEventListener('submit', function(e){

    e.preventDefault();

    let lozinka = document.getElementById('lozinka').value.trim();
    let mejl = document.getElementById('mejl').value.trim();
    let ime = document.getElementById('ime').value.trim();
    let prezime = document.getElementById('prezime').value.trim();
    let datum = document.getElementById('datum').value.trim();
    let adresa = document.getElementById('adresa').value.trim();
    let brTel = document.getElementById('tel').value.trim();

    let datumi = datum.split('-');
    let datumProvera = datumi[1] + '-' + datumi[2] + '-' + datumi[0];
    datumProvera = new Date(datumProvera);
    let danas = new Date();
    danas.setHours(0,0,0,0);

    if (lozinka == '' || mejl == '' || ime == '' || prezime == '' || datum == '' || adresa == '' || brTel == ''){
        alert('Niste uneli sve podatke.');
    } else {
        let zahtev = new XMLHttpRequest();

        zahtev.onreadystatechange = function(){
            if (this.readyState == 4){
                if (this.status == 200){

                    let korisnici = JSON.parse(this.responseText);
                    let sviKorisnici = [];
                    for(let id in korisnici){
                        sviKorisnici.push(korisnici[id]);
                    }

                    let listaEmail = [];
                    let listaTel = [];
                    let formatEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
                    let formatAdresa = /^[\w\s]+\d[a-z]?,\s*[\w\s]+$/;
                    for (let i = 0; i < sviKorisnici.length; i++){
                        let korisnik = sviKorisnici[i];
                        listaEmail.push(korisnik.email);
                        listaTel.push(korisnik.telefon);
                    }
                    if(lozinka.length < 6){
                        alert('Lozinka mora da sadrži minimum 6 karaktera.');
                    }
                    else if(ime.split(' ').length > 1 || prezime.split(' ').length > 1){
                        alert('Ime i prezime ne mogu da sadrže više od 1 reči.')
                    }
                    else if(/\d/.test(ime) || /\d/.test(prezime)){
                        alert('Ime i prezime ne mogu da sadrže broj/eve.');
                    }
                    else if(ime.length > 15 || prezime.length > 15){
                        alert('Ime i prezime ne mogu biti duži od 15 karaktera.');
                    }
                    else if(!mejl.match(formatEmail)){
                        alert('Uneli ste nevalidnu email adresu! Proverite ponovo.');
                    }
                    else if(listaEmail.includes(mejl) && mejl != korisnik.email){
                        alert('Uneli ste postojeću email adresu.');
                    }
                    else if(datumProvera >= danas){
                        alert('Niste uneli odgovarajući datum! Proverite ponovo.');
                    }
                    else if(!adresa.match(formatAdresa)){
                        alert('Adresu unesite u formatu ulica broj, grad');
                    }
                    else if(isNaN(brTel)){
                        alert('Pri unosu broja telefona unesite samo brojeve!');
                    }
                    else if(listaTel.includes(brTel) && brTel != korisnik.telefon){
                        alert('Uneli ste postojeći broj telefona.');
                    }
                    else if(brTel.length < 8 || brTel.length > 12){
                        alert('Broj telefona treba da ima najmanje 8, a najviše 12 cifara.');
                    }
                    else{
                        korisnik.password = lozinka;
                        korisnik.email = mejl;
                        korisnik.ime = ime;
                        korisnik.prezime = prezime;
                        korisnik.datumRodjenja = datum;
                        korisnik.adresa = adresa;
                        korisnik.telefon = brTel;
                        let putRequest = new XMLHttpRequest();

                        putRequest.onreadystatechange = function(e){
                            if(this.readyState == 4){
                                if(this.status == 200){
                                    let izmeniDgm = document.getElementById('izmena');
                                    izmeniDgm.addEventListener('click', function(){
                                    let dialog = document.getElementById('dialog');
                                    dialog.style.display = 'block';
                                    let potvrdiDgm = document.getElementById('potvrdi');
                                    potvrdiDgm.addEventListener('click', function(){
                                        dialog.style.display = 'none';
                                        alert('Uspešno ste izmenili podatke.');
                                        window.location.href='administrator.html';
                                    })
                                    let odustaniDgm = document.getElementById('odustani');
                                    odustaniDgm.addEventListener('click', function(){
                                        dialog.style.display = 'none';
                                    })
                                    })
                                }else{
                                    alert('Greška prilikom izmene podataka.')
                                }
                            }
                        }

                        putRequest.open('PUT', firebaseUrl.concat('/korisnici/', korisnikId, '.json'));
                        putRequest.send(JSON.stringify(korisnik));
                    }
                }
            }
        };
        zahtev.open('GET', firebaseUrl.concat('/korisnici.json'));
        zahtev.send();
    }
});

function podesiPodatke(korisnik){
    let korisnickoIme = document.getElementById('korisnik');
    korisnickoIme.value = korisnik.username;
    korisnickoIme.disabled = true;

    let lozinka = document.getElementById('lozinka');
    lozinka.value = korisnik.password;  
    
    let mejl = document.getElementById('mejl');
    mejl.value = korisnik.email;

    let ime = document.getElementById('ime');
    ime.value = korisnik.ime;
    
    let prezime = document.getElementById('prezime');
    prezime.value = korisnik.prezime;

    let datum = document.getElementById('datum');
    datum.value = korisnik.datumRodjenja; 

    let adresa = document.getElementById('adresa');
    adresa.value = korisnik.adresa;

    let brTel = document.getElementById('tel');
    brTel.value = korisnik.telefon; 
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