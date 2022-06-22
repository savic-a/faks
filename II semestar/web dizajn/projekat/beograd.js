let firebaseUrl = 'https://turisticke-atrakcije-default-rtdb.europe-west1.firebasedatabase.app';

let gradId = dobaviVrednost('id');
let grad = {};
let zahtev = new XMLHttpRequest();

zahtev.onreadystatechange = function(){
    if(this.readyState == 4){
        if(this.status == 200){
            grad = JSON.parse(this.responseText);
            let naslov = document.getElementsByClassName('naslov');
            naslov[0].innerText = 'TURE ' + grad.naziv.toUpperCase();
            naslov[1].innerText = 'TURE ' + grad.naziv.toUpperCase();
            
            let opis = document.getElementsByClassName('text');
            opis[0].innerText = grad.opis;
            opis[1].innerText = grad.opis;

            let pozadina = document.getElementById('beograd');
            pozadina.style.backgroundImage = "url(" + grad.slika + ")";

            let idSvihTura = grad.idAtrakcija;
            let tura = {};
            let pojedinacnaTura = {};
            let i = 0;
            let zahtevTure = new XMLHttpRequest();

            zahtevTure.onreadystatechange = function(){
                if(this.readyState == 4){
                    if(this.status == 200){
                        tura = JSON.parse(this.responseText);
                        for(let id in tura){
                            pojedinacnaTura = tura[id];
                            i ++;
                            
                            podesiPodatke(gradId, idSvihTura, pojedinacnaTura, id, i);
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

function podesiPodatke(gradId, idSvihTura, pojedinacnaTura, idTure, i){
    let responsivDiv = document.createElement('div');
    responsivDiv.classList.add('responsive');

    let galerijaDiv = document.createElement('div');
    galerijaDiv.classList.add('galerija');
    responsivDiv.appendChild(galerijaDiv);

    let linkTure = document.createElement('a');
    linkTure.onclick = stranicaTure;
    linkTure.setAttribute('data-idTure', idTure);
    linkTure.setAttribute('data-idSvihTura', idSvihTura);
    linkTure.setAttribute('data-gradId', gradId);
    galerijaDiv.appendChild(linkTure);

    let slika = document.createElement('img');
    slika.src = pojedinacnaTura.slika;
    linkTure.appendChild(slika);

    let opisDiv = document.createElement('div');
    opisDiv.classList.add('opis');
    galerijaDiv.appendChild(opisDiv);

    let linkNaslov = document.createElement('a');
    linkNaslov.onclick = stranicaTure;
    linkNaslov.setAttribute('data-idTure', idTure);
    linkNaslov.setAttribute('data-idSvihTura', idSvihTura);
    linkNaslov.setAttribute('data-gradId', gradId);
    linkNaslov.innerText = pojedinacnaTura.naziv;
    opisDiv.appendChild(linkNaslov);

    let element = document.createElement('hr');
    linkNaslov.appendChild(element);

    let opis = document.createElement('p');
    opis.innerText = pojedinacnaTura.kratakOpis;
    opisDiv.appendChild(opis);

    let linkVise = document.createElement('a');
    linkVise.onclick = stranicaTure;
    linkVise.setAttribute('data-idTure', idTure);
    linkVise.setAttribute('data-idSvihTura', idSvihTura);
    linkVise.setAttribute('data-gradId', gradId);
    opis.appendChild(linkVise);

    let span = document.createElement('span');
    span.innerText = ' pročitaj više';
    linkVise.appendChild(span);

    document.getElementById('ture').appendChild(responsivDiv);

    if(i % 3 == 0){
        let clearfix = document.createElement('div');
        clearfix.classList.add('clearfix');
        document.getElementById('ture').appendChild(clearfix);
    }
}

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