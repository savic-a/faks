let photoURL = 'https://jsonplaceholder.typicode.com/photos';

let request = new XMLHttpRequest();
let overlay = document.getElementById('overlay');
let currentImage;

request.onreadystatechange = function(e){
    if(this.readyState == 4){
        if(this.status == 200){

            let photos = JSON.parse(request.responseText);

            for(let i=0; i<100; i++){
                let photo = photos[i];
                kreirajSliku(photo);
            }

        }else{
            alert('Doslo je do greske!');
        }
    }
}

request.open('GET', photoURL);
request.send();

document.body.addEventListener('click', function(e) {
    overlay.style.display = 'none';
});

overlay.addEventListener('click', function(e){
    e.stopPropagation();
});

let leftArrow = document.getElementById('leftArrow');
leftArrow.addEventListener('click', function(e){
    e.stopPropagation();
    listajSliku('levo');
});

let rightArrow = document.getElementById('rightArrow');
rightArrow.addEventListener('click', function(e){
    e.stopPropagation();
    listajSliku('desno');
});

function prikaziOverlaySliku(){
    let imagePlaceholder = document.getElementById('imagePlaceholder');
    imagePlaceholder.setAttribute('src', currentImage.getAttribute('data-imageURL'));

    let titlePlaceholder = document.getElementById('titlePlaceholder');
    titlePlaceholder.innerText = currentImage.getAttribute('data-imageDescription');
}

function listajSliku(smer) {
    let next = currentImage.nextElementSibling;

    if(smer=='levo'){
        next = currentImage.previousElementSibling;
    }

    if(next!=null){
        currentImage = next;
        prikaziOverlaySliku();
    }
}

function kreirajSliku(photo) {
    let photoDiv = document.createElement('div');
    photoDiv.classList.add('previewDiv');

    photoDiv.style.backgroundImage = 'url("'+ photo.thumbnailUrl + '")';

    photoDiv.setAttribute('data-imageURL', photo.url);
    photoDiv.setAttribute('data-imageDescription', photo.title);

    photoDiv.addEventListener('click', function(e){

        e.stopPropagation();
        currentImage = this;

        prikaziOverlaySliku();
        
        overlay.style.display = 'block';
    });

    let content = document.getElementById('content');
    content.appendChild(photoDiv);
}