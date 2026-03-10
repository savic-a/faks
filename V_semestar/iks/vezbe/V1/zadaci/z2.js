let form = document.getElementById('results');
form.addEventListener('submit', function(e) {

    e.preventDefault();

    // First team
    let fFirstRound = parseInt(document.getElementById('fFirstRound').value);
    let fSecondRound = parseInt(document.getElementById('fSecondRound').value);
    let fThirdRound = parseInt(document.getElementById('fThirdRound').value);
    let fFourthRound = parseInt(document.getElementById('fFourthRound').value);
    let fAverage = (fFirstRound + fSecondRound + fThirdRound + fFourthRound) / 4;

    // Second team
    let sFirstRound = parseInt(document.getElementById('sFirstRound').value);
    let sSecondRound = parseInt(document.getElementById('sSecondRound').value);
    let sThirdRound = parseInt(document.getElementById('sThirdRound').value);
    let sFourthRound = parseInt(document.getElementById('sFourthRound').value);
    let sAverage = (sFirstRound + sSecondRound + sThirdRound + sFourthRound) / 4;

    let output = '';
    if (fAverage < sAverage) {
        output = "Prvi tim ima u proseku " + fAverage + ", a drugi " + sAverage + " poena. Drugi tim je pobedio!";
    }
    else if (fAverage > sAverage) {
        output = "Prvi tim ima u proseku " + fAverage + ", a drugi " + sAverage + " poena. Prvi tim je pobedio!";
    }
    else {
        output = "Oba tima u proseku imaju "+ fAverage + " poena. Rezultat je izjednacen!";
    }

    if(fAverage<50) {
        output += " Prvi tim ima manje od 50 poena.";
    }
    if(sAverage<50) {
        output += " Drugi tim ima manje od 50 poena.";
    }

    alert(output);
})