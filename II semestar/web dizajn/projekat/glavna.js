let firebaseUrl = 'https://turisticke-atrakcije-default-rtdb.europe-west1.firebasedatabase.app';

let zahtev = new XMLHttpRequest();

zahtev.onreadystatechange = function(){
    if(this.readyState == 4){
        if(this.status == 200){

            let gradovi = JSON.parse(this.responseText);

            for(let id in gradovi){
                let grad = gradovi[id];
                dodajGrad(grad, id);
            }

        }else{
            alert('Greška prilikom preuzimanja podataka!');
        }
    }
}

zahtev.open('GET', firebaseUrl.concat('/gradovi.json'));
zahtev.send();

function dodajGrad(grad, gradId){
    let kolonaDiv = document.createElement('div');
    kolonaDiv.classList.add('kolona');

    let link = document.createElement('a');
    link.onclick = stranicaSvihTura;
    link.setAttribute('data-gradId', gradId);
    kolonaDiv.appendChild(link);

    let slika = document.createElement('img');
    slika.src = grad.slika;
    link.appendChild(slika);

    let opisTure = document.createElement('div');
    opisTure.classList.add('opis-ture');
    link.appendChild(opisTure);

    let nazivDiv = document.createElement('div');
    nazivDiv.innerText = grad.naziv;
    nazivDiv.classList.add('naziv');
    opisTure.appendChild(nazivDiv);

    let brTuraDiv = document.createElement('div');
    brTuraDiv.innerText = grad.brojAtrakcija + ' tura';
    brTuraDiv.classList.add('broj-tura');
    opisTure.appendChild(brTuraDiv);

    document.getElementById('gradovi').appendChild(kolonaDiv);
}

function stranicaSvihTura(){
    let klik = this;
    let gradId = klik.getAttribute('data-gradId');
    window.location.href = 'preporucene-ture.html?id=' + gradId; 
}

