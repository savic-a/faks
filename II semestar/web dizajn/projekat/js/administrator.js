let firebaseUrl = 'https://turisticke-atrakcije-default-rtdb.europe-west1.firebasedatabase.app';

tabelaKorisnika();

function tabelaKorisnika(){
    let zahtev = new XMLHttpRequest();

    zahtev.onreadystatechange = function(){
        if(this.readyState == 4){
            if(this.status == 200){

                obrisiRed('sviKorisnici');

                let korisnici = JSON.parse(this.responseText);

                for(let id in korisnici){
                    let korisnik = korisnici[id];
                    dodajRed('sviKorisnici', id, korisnik);
                }
            } else{
                alert('Greška prilikom preuzimanja podataka!');
            }
        }
    }
    zahtev.open('GET', firebaseUrl.concat('/korisnici.json'));
    zahtev.send();
}

function obrisiRed(tBody){
    let tbody = document.getElementById(tBody);
    while (tbody.firstChild) {
        tbody.removeChild(tbody.lastChild);
    }
}

function dodajRed(tBodyId, korisnikId, korisnik){
    let korisnikTr = document.createElement('tr');
    
    let imeTd = document.createElement('td');
    imeTd.innerText = korisnik.ime;
    korisnikTr.appendChild(imeTd);

    let prezimeTd = document.createElement('td');
    prezimeTd.innerText = korisnik.prezime;
    korisnikTr.appendChild(prezimeTd);
    
    let emailTd = document.createElement('td');
    emailTd.innerText = korisnik.email;
    korisnikTr.appendChild(emailTd);

    let profilDgm = document.createElement('a');
    profilDgm.innerText = 'Moj profil';
    profilDgm.onclick = profilStranica;
    profilDgm.setAttribute('data-korisnikId', korisnikId);

    let profilTd = document.createElement('td');
    profilTd.appendChild(profilDgm);
    korisnikTr.appendChild(profilTd);

    let izmenaDgm = document.createElement('a');
    izmenaDgm.innerText = 'Izmeni profil';
    izmenaDgm.onclick = izmeniStranica;
    izmenaDgm.setAttribute('data-korisnikId', korisnikId);

    let izmenaTd = document.createElement('td');
    izmenaTd.appendChild(izmenaDgm);
    korisnikTr.appendChild(izmenaTd);

    let obrisiDgm = document.createElement('a');
    obrisiDgm.innerText = 'Obriši profil';
    obrisiDgm.onclick = obrisiProfil;
    obrisiDgm.setAttribute('data-korisnikId', korisnikId);

    let obrisiTd = document.createElement('td');
    obrisiTd.appendChild(obrisiDgm);
    korisnikTr.appendChild(obrisiTd);
    
    let tBody = document.getElementById(tBodyId);
    tBody.appendChild(korisnikTr);
}

function obrisiProfil(){
    let obrisiDgm = this;
    let korisnikId = obrisiDgm.getAttribute('data-korisnikId');
    let obrisiZahtev = new XMLHttpRequest();
    obrisiZahtev.onreadystatechange = function(){
        if(this.readyState == 4){
            if(this.status == 200){
                tabelaKorisnika();
            }else{
                alert('Greska prilikom brisanja profila!');
            }
        }
    }
    obrisiZahtev.open('DELETE', firebaseUrl.concat('/korisnici/', korisnikId, '.json'));
    obrisiZahtev.send();
}

function izmeniStranica(){
    let kliknutoDgm = this;
    let korisnikId = kliknutoDgm.getAttribute('data-korisnikId');
    window.location.href = 'izmena-korisnika.html?id=' + korisnikId; 
}

function profilStranica(){
    let kliknutoDgm = this;
    let korisnikId = kliknutoDgm.getAttribute('data-korisnikId');
    window.location.href = 'profil.html?id=' + korisnikId;
}