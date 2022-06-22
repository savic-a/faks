let firebaseUrl = 'https://turisticke-atrakcije-default-rtdb.europe-west1.firebasedatabase.app';

let gradId = dobaviVrednost('id');
let grad = {};
let zahtev = new XMLHttpRequest();

zahtev.onreadystatechange = function(){
    if(this.readyState == 4){
        if(this.status == 200){
            grad = JSON.parse(this.responseText);
            console.log(grad);

            let pozadina = document.getElementById('pozadina');
            pozadina.style.backgroundImage = "url(" + grad.slika + ")";

            let naslov = document.getElementById('naslov');
            naslov.innerText = 'Preporučene ture ' + grad.naziv;

            let idSvihTura = grad.idAtrakcija;
            let tura = {};
            let ids = [];
            let pojedinacneTureSlike = [];
            let listaNazivi = [];
            let listaOpis = [];
            let listaCena = [];
            let zahtevTure = new XMLHttpRequest();

            zahtevTure.onreadystatechange = function(){
                if(this.readyState == 4){
                    if(this.status == 200){
                        tura = JSON.parse(this.responseText);
                        for(let id in tura){
                            pojedinacnaTura = tura[id];
                            ids.push(id);
                            pojedinacneTureSlike.push(tura[id].slika);
                            listaNazivi.push(tura[id].naziv);
                            listaOpis.push(tura[id].kratakOpis);
                            listaCena.push(tura[id].cena);
                            
                            postaviSlike(gradId, idSvihTura, ids, pojedinacneTureSlike, listaNazivi, listaOpis, listaCena);
                        }

                    }else{
                        alert('Greška prilikom učitavanja ture!')
                    }
                }
            }
            zahtevTure.open('GET', firebaseUrl.concat('/turistickeAtrakcije/', idSvihTura, '.json'));
            zahtevTure.send();

        }else{
            alert('Greška prilikom učitavanja grada!')
        }
    }
}

zahtev.open('GET', firebaseUrl.concat('/gradovi/', gradId, '.json'));
zahtev.send();

let slajdIndeks = 1;

function postaviSlike(gradId, idSvihTura, ids, pojedinacneTureSlike, listaNazivi, listaOpis, listaCena){
    let tekst = document.getElementsByClassName('tekst');
    tekst[0].innerText = 'Naziv ture: ' + listaNazivi[0];
    tekst[1].innerText = 'Naziv ture: ' + listaNazivi[1];
    tekst[2].innerText = 'Naziv ture: ' + listaNazivi[2];
    tekst[3].innerText = 'Naziv ture: ' + listaNazivi[3];
    let opis = document.getElementsByClassName('opis');
    opis[0].innerText = 'Opis: ' + listaOpis[0];
    opis[1].innerText = 'Opis: ' + listaOpis[1];
    opis[2].innerText = 'Opis: ' + listaOpis[2];
    opis[3].innerText = 'Opis: ' + listaOpis[3];
    let cena = document.getElementsByClassName('cena');
    cena[0].innerText = 'Cena: ' + listaCena[0] + ' RSD';
    cena[1].innerText = 'Cena: ' + listaCena[1] + ' RSD';
    cena[2].innerText = 'Cena: ' + listaCena[2] + ' RSD';
    cena[3].innerText = 'Cena: ' + listaCena[3] + ' RSD';
    let slika1 = document.getElementById('prva');
    slika1.src = pojedinacneTureSlike[0];
    let slika2 = document.getElementById('druga');
    slika2.src = pojedinacneTureSlike[1];
    let slika3 = document.getElementById('treca');
    slika3.src = pojedinacneTureSlike[2];
    let slika4 = document.getElementById('cetvrta');
    slika4.src = pojedinacneTureSlike[3];
    let link = document.getElementsByClassName('link');
    link[0].addEventListener('click', function(){
        window.location.href = 'avala.html?id=' + gradId + '!' + idSvihTura + '@' + ids[0];
    })
    link[1].addEventListener('click', function(){
        window.location.href = 'avala.html?id=' + gradId + '!' + idSvihTura + '@' + ids[1];
    })
    link[2].addEventListener('click', function(){
        window.location.href = 'avala.html?id=' + gradId + '!' + idSvihTura + '@' + ids[2];
    })
    link[3].addEventListener('click', function(){
        window.location.href = 'avala.html?id=' + gradId + '!' + idSvihTura + '@' + ids[3];
    })
    prikaziSliku(slajdIndeks);
}

function prikaziSliku(indeks){
    let tura = document.getElementsByClassName('tura');
    if(indeks > tura.length){
        slajdIndeks = 1
    }
    if(indeks < 1){
        slajdIndeks = tura.length
    }
    for (i = 0; i < tura.length; i++) {
        tura[i].style.display = 'none';
    }
    tura[slajdIndeks-1].style.display = 'block';
}

let pretDgm = document.getElementById('levo');
pretDgm.addEventListener('click', function(){
    prikaziSliku(slajdIndeks += -1);
})

let sledDgm = document.getElementById('desno');
sledDgm.addEventListener('click', function(){
    prikaziSliku(slajdIndeks += 1);
})

let sveTure = document.getElementById('sve-ture')
sveTure.addEventListener('click', function(){
    window.location.href = 'beograd.html?id=' + gradId; 
})

function stranicaTure(){
    let klik = this;
    let idTura = klik.getAttribute('data-idTure');
    let idSvihTura = klik.getAttribute('data-idSvihTura');
    let idGrada = klik.getAttribute('data-gradId');
    window.location.href = 'avala.html?id=' + idGrada + '!' + idSvihTura + '@' + idTura; 
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

// navigacija-dugme
let navMeni = document.getElementById('js-meni');
let navDugme = document.getElementById('nav');
let x = window.matchMedia("(max-width: 800px)")

navDugme.addEventListener('click', function(e){
    e.stopPropagation();
    navMeni.style.display = 'block';
})

document.body.addEventListener('click', function(e) {
    if(x.matches){
        navMeni.style.display = 'none';
    }
});

// prijavljivanje
let prijavljivanje = document.getElementById('prijavljivanje');
let pPopUp = document.getElementById('prijava'); 
prijavljivanje.addEventListener('click', function(){
    pPopUp.style.display = 'block';
})

let zatvori = document.getElementById('zatvori');
zatvori.addEventListener('click', function(){
    pPopUp.style.display = 'none';
})

let pForm = document.getElementById('pForm');
pForm.addEventListener('submit', function(e){
    e.preventDefault();

    let korisnickoIme = document.getElementById('korisnicko-ime').value.trim();
    let lozinka = document.getElementById('lozinka').value.trim();

    if (korisnickoIme == '' || lozinka == ''){
        alert('Niste uneli sve podatke za prijavu.');
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

                    let ime = '';
                    for (let i = 0; i < sviKorisnici.length; i++){
                        let korisnik = sviKorisnici[i];
                        if (korisnik.username == korisnickoIme && korisnik.password == lozinka) {
                            ime = korisnik.ime;
                            break;
                        }
                    }
                    if (ime == ''){
                        alert('Uneli ste neispravne podatke.');
                    } else{
                        alert('Dobrodošli ' + ime + '!');
                        pPopUp.style.display = 'none';
                    }
                } else{
                    alert('Greška prilikom preuzimanja podataka!')
                }
            }
        };
        zahtev.open('GET', firebaseUrl.concat('/korisnici.json'));
        zahtev.send();
    }
});

// registracija
let tekst = document.getElementsByClassName('tekst');
let opis = document.getElementsByClassName('opis');
let cena = document.getElementsByClassName('cena');
let reg = document.getElementById('registrovanje');
let rPopUp = document.getElementById('registracija'); 
reg.addEventListener('click', function(){
    rPopUp.style.display = 'block';
    tekst[0].style.display = 'none';
    tekst[1].style.display = 'none';
    tekst[2].style.display = 'none';
    tekst[3].style.display = 'none';
    opis[0].style.display = 'none';
    opis[1].style.display = 'none';
    opis[2].style.display = 'none';
    opis[3].style.display = 'none';
    cena[0].style.display = 'none';
    cena[1].style.display = 'none';
    cena[2].style.display = 'none';
    cena[3].style.display = 'none';
})

let zat = document.getElementById('zat');
zat.addEventListener('click', function(){
    rPopUp.style.display = 'none';
    tekst[0].style.display = 'block';
    tekst[1].style.display = 'block';
    tekst[2].style.display = 'block';
    tekst[3].style.display = 'block';
    opis[0].style.display = 'block';
    opis[1].style.display = 'block';
    opis[2].style.display = 'block';
    opis[3].style.display = 'block';
    cena[0].style.display = 'block';
    cena[1].style.display = 'block';
    cena[2].style.display = 'block';
    cena[3].style.display = 'block';
})

let rForm = document.getElementById('rForm');
rForm.addEventListener('submit', function(e){
    e.preventDefault();

    let korisnickoIme = document.getElementById('username').value.trim();
    let lozinka = document.getElementById('pwd').value.trim();
    let ime = document.getElementById('fName').value.trim();
    let prezime = document.getElementById('lName').value.trim();
    let email = document.getElementById('email').value.trim();
    let datum = document.getElementById('date').value.trim();
    let adresa = document.getElementById('adress').value.trim();
    let tel = document.getElementById('mobile').value.trim();

    let datumi = datum.split('-');
    datum = datumi[1] + '-' + datumi[2] + '-' + datumi[0];
    datum = new Date(datum);
    let danas = new Date();
    danas.setHours(0,0,0,0);
    
    if (korisnickoIme == '' || lozinka == '' || ime == '' || prezime == '' || email == '' || datum == '' || adresa == '' || tel == ''){
        alert('Niste uneli sve podatke za prijavu.');
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
                    let listaUsername = [];
                    let listaTel = [];
                    let formatEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
                    let formatAdresa = /^[\w\s]+\d[a-z]?,\s*[\w\s]+$/;
                    for (let i = 0; i < sviKorisnici.length; i++){
                        let korisnik = sviKorisnici[i];
                        listaEmail.push(korisnik.email);
                        listaTel.push(korisnik.telefon);
                        listaUsername.push(korisnik.username);
                    }
                    if(listaUsername.includes(korisnickoIme)){
                        alert('Uneli ste već postojeće korisničko ime.');
                    }
                    else if(korisnickoIme.length < 4 || korisnickoIme.length > 15){
                        alert('Korisničko ime mora da sadrži minimum 4 i maksimum 15 karaktera.');
                    }
                    else if(lozinka.length < 6){
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
                    else if(!email.match(formatEmail)){
                        alert('Uneli ste nevalidnu email adresu! Proverite ponovo.');
                    }
                    else if(listaEmail.includes(email)){
                        alert('Uneli ste postojeću email adresu.');
                    }
                    else if(datum >= danas){
                        alert('Niste uneli odgovarajući datum! Proverite ponovo.');
                    }
                    else if(!adresa.match(formatAdresa)){
                        alert('Adresu unesite u formatu ulica broj, grad');
                    }
                    else if(isNaN(tel)){
                        alert('Pri unosu broja telefona unesite samo brojeve!');
                    }
                    else if(listaTel.includes(tel)){
                        alert('Uneli ste postojeći broj telefona.');
                    }
                    else if(tel.length < 8 || tel.length > 12){
                        alert('Broj telefona treba da ima najmanje 8, a najviše 12 cifara.');
                    }
                    else{
                        alert('Uspešno ste se registrovali. Dobrodošli!');
                        rPopUp.style.display = 'none';
                    }
                }
            }
        };
        zahtev.open('GET', firebaseUrl.concat('/korisnici.json'));
        zahtev.send();
    }
});
