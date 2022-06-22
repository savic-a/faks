let firebaseUrl = 'https://cars-projekat-default-rtdb.europe-west1.firebasedatabase.app';

getCars();

function getCars(){
    let request = new XMLHttpRequest();

    request.onreadystatechange = function(){
        if(this.readyState == 4){
            if(this.status == 200){

                removeTableRows('allCars');

                let cars = JSON.parse(this.responseText);
                console.log(cars);

                for(let id in cars){
                    let car = cars[id];
                    appendCarRow('allCars', id, car);
                }

            }else{
                alert('Greska!')
            }
        }
    }

    request.open('GET', firebaseUrl.concat('/cars.json'));
    request.send();
}

function appendCarRow(tbodyId, carId, car){
    let carTr = document.createElement('tr');

    let makeTd = document.createElement('td');
    makeTd.innerText = car.make;
    carTr.appendChild(makeTd);

    let modelTd = document.createElement('td');  // pravimo element
    modelTd.innerText = car.model;  //dodajemo mu vrednost
    carTr.appendChild(modelTd);

    let yearTd = document.createElement('td');  // pravimo element
    yearTd.innerText = car.year;  //dodajemo mu vrednost
    carTr.appendChild(yearTd);

    let editBtn = document.createElement('button');
    editBtn.type = 'button';
    editBtn.innerText = 'Izmeni';
    editBtn.onclick = showEditPage;
    editBtn.setAttribute('data-carId', carId);

    let editTd = document.createElement('td');
    editTd.appendChild(editBtn);
    carTr.appendChild(editTd);

    let deleteBtn = document.createElement('button');
    deleteBtn.type = 'button';
    deleteBtn.innerText = 'Obrisi';
    deleteBtn.onclick = deleteCar;
    deleteBtn.setAttribute('data-carId', carId);

    let deleteTd = document.createElement('td');
    deleteTd.appendChild(deleteBtn);
    carTr.appendChild(deleteTd);

    let tbody = document.getElementById(tbodyId);
    tbody.appendChild(carTr);
}

function deleteCar(){
    let clickedBtn = this;
    // console.log(clickedBtn);

    let carId = clickedBtn.getAttribute('data-carId');

    let deleteRequest = new XMLHttpRequest();
    deleteRequest.onreadystatechange = function(){
        if(this.readyState == 4){
            if(this.status == 200){
                getCars();
            }else{
                alert('Greska!');
            }
        }
    }
    deleteRequest.open('DELETE', firebaseUrl.concat('/cars/', carId, '.json'));
    deleteRequest.send();
}

function showEditPage(){
    let clickedBtn = this;
    let carId = clickedBtn.getAttribute('data-carId');
    window.location.href = 'edit.html?id=' + carId;
}

function removeTableRows(tBody) {
    let tbody = document.getElementById(tBody);
    while (tbody.firstChild) {
        tbody.removeChild(tbody.lastChild);
    }
}