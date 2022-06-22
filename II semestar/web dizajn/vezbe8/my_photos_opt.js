/*
	Malo optimizivanija verzija resenja.
	Redundanti kod je resen upotrebom funkcija.
*/

// Pamtimo trenutni div sa slikom na koji je kliknuto zbog listanja
let currentImg;
// Web adresa sa koje ucitavamo slike
let photosURL = "https://jsonplaceholder.typicode.com/photos"
    // Spremicemo referencu na 'overlay' div jer ce nam trebati nekoliko puta
let overlay = document.getElementById('overlay');

// Kada se ucita stranica ucitaju se sve slike sa servera
let request = new XMLHttpRequest();

request.onreadystatechange = function() {
    if (this.readyState == 4) {
        if (this.status == 200) {
            let slike = JSON.parse(request.responseText);
            // Prikazacemo samo prvih 100 slika, umesto svih 1000
            for (let i = 0; i <= 100; i++) {
                let slika = slike[i];
                ucitajSliku(slika);
            }
        } else {
            console.error('Doslo je do greske prilikom ucitavanja slika.')
        }
    }
}

request.open('GET', photosURL);
request.send();

// Kada se klikne bilo gde, ugasimo overlay sa slikom
document.body.addEventListener('click', function(e) {
    overlay.style.display = 'none';
});

// Da se overlay ne bi zatletao kada se klikne na njega, 
// zaustavimo porpagaciju klikova na njemu
overlay.addEventListener('click', function(e) {
    e.stopPropagation();
});

// Listanje slika ulevo
let leftArrow = document.getElementById('leftArrow');
leftArrow.addEventListener('click', function(e) {
    e.stopPropagation();
    listaj('levo');
});

// Listanje slika udesno
let rightArrow = document.getElementById('rightArrow');
rightArrow.addEventListener('click', function(e) {
    e.stopPropagation();
    listaj('desno');
});

/*************************************************************************************
																	POMOCNE FUNKCIJE
**************************************************************************************/
// Kreira novi div sa ucitanom slikom sa servera:
function ucitajSliku(slika) {
    let newDiv = document.createElement('div');
    newDiv.classList.add('previewDiv');
    // Upisemo u atribut 'data-imageURL' putanju do velike slike, 
    // a u 'imageDescription' opis slike
    newDiv.setAttribute('data-imageURL', slika.url);
    newDiv.setAttribute('data-imageDescription', slika.title);
    // Postavimo sliku kao pozadinu
    newDiv.style.backgroundImage = 'url("' + slika.thumbnailUrl + '")';

    // Odmah cemo povezati i click listener za divove sa slikama
    // Kada se klikne na njih, treba da prikazemo 'overlay' div sa velikom verzijom 
    // slike i naslovom:
    newDiv.addEventListener('click', function(e) {
        //Zaustavimo propagaciju klika da se ne bi propagirao do body-a 
        // (da ne nestane overlay)
        e.stopPropagation();
        // Postavimo kliknuti div kao trenutni
        currentImg = this;

        prikaziVelikuSliku();

        overlay.style.display = 'block';
    });

    // Novokreirani div ubacimo u div koji ima id 'content'
    let contentDiv = document.getElementById('content');
    contentDiv.appendChild(newDiv);
}

// Otleta overlay sa prikazom verlike verzije slike i opisa
function prikaziVelikuSliku() {
    // Postavimo src atribut img taga na overlayu koji sadrzi veliku sliku
    let imageEl = document.getElementById('imagePlaceholder');
    imageEl.setAttribute('src', currentImg.getAttribute('data-imageURL'));

    // Postavimo naslov slike u paragraf koji sluzi za to
    let titleEl = document.getElementById('titlePlaceholder');
    titleEl.innerText = currentImg.getAttribute('data-imageDescription');
}

function listaj(smer) {
    let sledeci = currentImg.nextElementSibling;
    if (smer == 'levo') {
        sledeci = currentImg.previousElementSibling;
    }

    if (sledeci != null) {
        currentImg = sledeci;
        prikaziVelikuSliku();
    }
}