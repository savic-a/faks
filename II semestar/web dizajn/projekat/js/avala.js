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

            podesiPodatke(idGrada, tura);    

        }else{
            alert('Greška prilikom učitavanja ture!')
        }
    }
}
zahtev.open('GET', firebaseUrl.concat('/turistickeAtrakcije/', idSvihTura, '/', idTure, '.json'));
zahtev.send();

function podesiPodatke(idGrada, tura){
    let linkSvihTura = document.getElementById('sve-ture')
    linkSvihTura.href = 'beograd.html?id=' + idGrada;

    let pozadina = document.getElementById('avala');
    pozadina.style.backgroundImage = "url(" + tura.slika + ")";

    let nazivTure = document.getElementById('naslov');
    nazivTure.innerText = tura.naziv;

    let podnaslov = document.getElementById('podnaslov');
    podnaslov.innerText = tura.naziv;

    let opis = document.getElementsByClassName('tekst');
    opis[0].innerText = tura.opis;

    let tip = document.getElementById('tip');
    tip.innerText = tura.tip;

    let polazak = document.getElementsByClassName('polazak');
    polazak[0].innerText = tura.adresaPolaska;
    polazak[1].innerText = tura.adresaPolaska;

    let trajanje = document.getElementById('trajanje');
    trajanje.innerText = tura.trajanje + ' minuta';

    let cena = document.getElementById('cena');
    cena.innerText = tura.cena + ' dinara po osobi';

    let brOsoba = document.getElementById('broj');
    brOsoba.innerText = tura.maxOsobe;

    let recenzije = document.getElementById('zvezde');
    let prosecnaOcena = tura.ocena;
    let ocena = Math.round(prosecnaOcena);
    for(i = 0; i < ocena; i++){
        let zvezda = document.createElement('span');
        zvezda.classList.add('fa', 'fa-star', 'fa-lg', 'zvezda');
        recenzije.appendChild(zvezda);
    }
    if(ocena != 5){
        for(j = 0; j < (5-ocena); j++){
           let praznaZvezda = document.createElement('span');
           praznaZvezda.classList.add('fa', 'fa-star-o', 'fa-lg', 'zvezda') 
           recenzije.appendChild(praznaZvezda);
        }
    }
    let listaOcena = tura.ocene;
    let ukupnoKorisnika = 0;
    let desno = document.getElementsByClassName('desno');
    let br = 4;
    for(k = 0; k < listaOcena.length; k++){
        ukupnoKorisnika += listaOcena[k];
        let brKorisnika = document.createElement('div');
        brKorisnika.innerText = listaOcena[br];
        desno[k].appendChild(brKorisnika);
        br --;
    }
    let prosek = document.getElementById('prosek');
    prosek.innerText = 'Prosek ' + tura.ocena + ', bazirano na ' + ukupnoKorisnika + ' ocene';

    let nav5 = document.getElementById('bar-5');
    nav5.style.width = (listaOcena[4] * 100 / ukupnoKorisnika) + '%';

    let nav4 = document.getElementById('bar-4');
    nav4.style.width = (listaOcena[3] * 100 / ukupnoKorisnika) + '%';

    let nav3 = document.getElementById('bar-3');
    nav3.style.width = (listaOcena[2] * 100 / ukupnoKorisnika) + '%';

    let nav2 = document.getElementById('bar-2');
    nav2.style.width = (listaOcena[1] * 100 / ukupnoKorisnika) + '%';

    let nav1 = document.getElementById('bar-1');
    nav1.style.width = (listaOcena[0] * 100 / ukupnoKorisnika) + '%';
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

let izmenaDgm = document.getElementById('dugme');
izmenaDgm.addEventListener('click', function(){
    window.location.href = 'izmena-ture.html?id=' + idGrada + '!' + idSvihTura + '@' + idTure;
})