let firebaseUrl = 'https://cars-projekat-default-rtdb.europe-west1.firebasedatabase.app';

let carId = getParamValue('id');
let car = {};
let getRequest = new XMLHttpRequest();

getRequest.onreadystatechange = function(){
    if(this.readyState == 4){
        if(this.status == 200){
            car = JSON.parse(this.responseText);
            console.log(car);

            setFormData(car);

        }else{
            alert('Greska!');
        }
    }
}
getRequest.open('GET', firebaseUrl.concat('/cars/', carId, '.json'));
getRequest.send();

let editForm = document.getElementById('editForm');
editForm.addEventListener('submit', function(e){

    e.preventDefault();

    let make = document.getElementById('make').value.trim();
    let model = document.getElementById('model').value.trim();
    let year = document.getElementById('year').valueAsNumber;

    if(make != ''){
        car.make = make;
    }

    if(model != ''){
        car.model = model;
    }

    if(!isNaN(year)){
        car.year = year;
    }

    let putRequest = new XMLHttpRequest();

    putRequest.onreadystatechange = function(e){
        if(this.readyState == 4){
            if(this.status == 200){
                window.location.href='index.html';
            }else{
                alert('Greska prilikom izmene automobila.')
            }
        }
    }

    putRequest.open('PUT', firebaseUrl.concat('/cars/', carId, '.json'));
    putRequest.send(JSON.stringify(car));
})

function setFormData(car){
    let makeInput = document.getElementById('make');
    makeInput.value = car.make;

    let modelInput = document.getElementById('model');
    modelInput.value = car.model;

    let yearInput = document.getElementById('year');
    yearInput.value = car.year;
}


function getParamValue(name) {
    let location = decodeURI(window.location.toString());
    let index = location.indexOf("?") + 1;
    let subs = location.substring(index, location.length);
    let splitted = subs.split("&");

    for (i = 0; i < splitted.length; i++) {
        let s = splitted[i].split("=");
        let pName = s[0];
        let pValue = s[1];
        if (pName == name) {
            return pValue;
        }
    }
}