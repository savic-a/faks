let firebaseUrl = 'https://turisticke-atrakcije-default-rtdb.europe-west1.firebasedatabase.app';

let vrednosti = dobaviVrednost('id');
let idGrada = vrednosti[0];
let idSvihTura = vrednosti[1];
let idTure = vrednosti[2];
let tura = {};
let zahtev = new XMLHttpRequest();

zahtev.onreadystatechange = function(){
    if(this.readyState == 4){
        if(this.status == 200){
            tura = JSON.parse(this.responseText);

            podesiPodatke(tura);

        }else{
            alert('Greška prilikom učitavanja turaa!')
        }
    }
}
zahtev.open('GET', firebaseUrl.concat('/turistickeAtrakcije/', idSvihTura, '/', idTure, '.json'));
zahtev.send();

let izmeniForma = document.getElementById('izmeniForma');
izmeniForma.addEventListener('submit', function(e){

    e.preventDefault();

    let naziv = document.getElementById('naziv').value.trim();
    let adresa = document.getElementById('adresa').value.trim();
    let kratakOpis = document.getElementById('kratak-opis').value.trim();
    let trajanje = document.getElementById('trajanje').value.trim();
    let tip = document.getElementById('tip').value.trim();
    let detaljno = document.getElementById('detaljno').value.trim();
    let cena = document.getElementById('cena').value.trim();
    let maks = document.getElementById('brOsoba').value.trim();

    if (naziv == '' || adresa == '' || kratakOpis == '' || trajanje == '' || detaljno == '' || cena == '' || maks == ''){
        alert('Niste uneli sve podatke.');
    } else {
        let zahtev = new XMLHttpRequest();

        zahtev.onreadystatechange = function(){
            if (this.readyState == 4){
                if (this.status == 200){

                    let ture = JSON.parse(this.responseText);
                    let listaNazivi = [];
                    for(let id in ture){
                        listaNazivi.push(ture[id].naziv);
                    }

                    let formatAdresa = /^[\w\s]+\d[a-z]?,\s*[\w\s]+$/;
                    if(naziv.length < 6){
                        alert('Naziv ture mora da sadrži minimum 6 karaktera.');
                    }
                    else if(!adresa.match(formatAdresa)){
                        alert('Adresu unesite u formatu ulica broj, grad');
                    }
                    else if(isNaN(trajanje)){
                        alert('Pri unosu trajanja unesite samo brojeve!');
                    }
                    else if(trajanje > 500){
                        alert('Tura traja previše dugo.')
                    }
                    else if(trajanje < 30){
                        alert('Tura traje prekratko.')
                    }
                    else if(isNaN(cena)){
                        alert('Cenu unesite brojevima.');
                    }
                    else if(cena < 3){
                        alert('Cena je suviše niska!');
                    }
                    else if(cena > 10000){
                        alert('Cena je previsoka!')
                    }
                    else if(isNaN(maks)){
                        alert('Broj osoba unesite brojevima.');
                    }
                    else if(maks < 5){
                        alert('Minimalan broj osoba je 5!');
                    }
                    else if(maks > 200){
                        alert('Maksimalan broj osoba je 200!')
                    }
                    else{
                        tura.naziv = naziv;
                        tura.adresaPolaska = adresa;
                        tura.kratakOpis = kratakOpis;
                        tura.trajanje = trajanje;
                        tura.tip = tip;
                        tura.opis = detaljno;
                        tura.cena = cena;
                        tura.maxOsobe = maks;
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
                                        window.location.href= 'avala.html?id=' + idGrada + '!' + idSvihTura + '@' + idTure;
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

                        putRequest.open('PUT', firebaseUrl.concat('/turistickeAtrakcije/', idSvihTura, '/', idTure, '.json'));
                        putRequest.send(JSON.stringify(tura));
                    }
                }
            }
        };
        zahtev.open('GET', firebaseUrl.concat('/turistickeAtrakcije/', idSvihTura, '/', '.json'));
        zahtev.send();
    }
})


function podesiPodatke(tura){
    let naziv = document.getElementById('naziv');
    naziv.value = tura.naziv;

    let sifra = document.getElementById('sifra');
    sifra.value = tura.kod; 
    sifra.disabled = true; 
    
    let adresa = document.getElementById('adresa');
    adresa.value = tura.adresaPolaska;

    let kratakOpis = document.getElementById('kratak-opis');
    kratakOpis.value = tura.kratakOpis;
    
    let trajanje = document.getElementById('trajanje');
    trajanje.value = tura.trajanje;

    let tip = document.getElementById('tip');
    tip.value = tura.tip; 

    let dugOpis = document.getElementById('detaljno');
    dugOpis.value = tura.opis;

    let cena = document.getElementById('cena');
    cena.value = tura.cena; 

    let maks = document.getElementById('brOsoba');
    maks.value = tura.maxOsobe;
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
            let ids = vrednost.split('!')
            let idGrada = ids[0]
            let idsTura = ids[1].split('@');
            let idSvihTura = idsTura[0];
            let idTure = idsTura[1];
            return [idGrada, idSvihTura, idTure];
        }
    }
}

