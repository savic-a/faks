let form = document.getElementById('calculator');
form.addEventListener('submit', function(e){

    e.preventDefault();

    let amount = parseInt(document.getElementById('bill').value);
    let tips = 0;

    if (50 <= amount && amount <= 200) {
        tips = amount * 15 / 100;
    }
    else {
        tips = amount * 20 / 100;
    }
    alert("Bil ce ostaviti " + tips + " evra");
})