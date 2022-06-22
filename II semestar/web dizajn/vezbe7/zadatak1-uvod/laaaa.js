let kocka = document.getElementById('glavnaKocka');
console.log(kocka);
 
let hideBtn = document.getElementById('hideButton');
hideBtn.addEventListener('click', function(e){
    kocka.style.display = 'none';
});

let showBtn = document.getElementById('showButton');
showBtn.addEventListener('click', function(e) {
    kocka.style.display = 'block';
});

let classBtn = document.getElementById('classButton');
classBtn.addEventListener('click', function(e){
    kocka.classList.toggle('velikaCrvenaKocka');
    //console.log(kocka.classList);
});

let textBtn = document.getElementById('textButton');
textBtn.addEventListener('click', function(e){
    kocka.innerText = 'TEXT'
});

let createBtn = document.getElementById('createButton');
createBtn.addEventListener('click', function(e) {
    let newKocka = document.createElement('div');
    newKocka.classList.add('kockica');
    let body = document.getElementsByTagName('body')[0];
    body.appendChild(newKocka);
})

let alertBtn = document.getElementById('alertButton');
alertBtn.addEventListener('click', function(e) {
    alert('ALERT TEXT');
})

let consoleBtn = document.getElementById('consoleButton')
consoleBtn.addEventListener('click', function(e){
    console.log('some log message');
    console.warn('some warning message');
    console.error('some error message')
    console.table(['table data 1', 'table data 2'])
}) 

let dockButtons = document.getElementsByClassName('dockButton');
console.log(dockButtons);