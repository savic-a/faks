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
let reg = document.getElementById('registrovanje');
let rPopUp = document.getElementById('registracija'); 
reg.addEventListener('click', function(){
    rPopUp.style.display = 'block';
})

let zat = document.getElementById('zat');
zat.addEventListener('click', function(){
    rPopUp.style.display = 'none';
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
        alert('Niste uneli sve podatke za registraciju.');
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