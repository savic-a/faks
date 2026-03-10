let form = document.getElementById('form');
form.addEventListener('submit', function(e){
    
    e.preventDefault();  // proveri cemu sluzi

    class Marko {
        constructor() {
            this.weight = parseInt(document.getElementById('mWeight').value);
            this.high = parseInt(document.getElementById('mHigh').value);
        }
        calcBMI() {
            return this.weight/(this.high*this.high);
        }
    }

    const Jovan = function() {
        this.weight = parseInt(document.getElementById('jWeight').value);
        this.high = parseInt(document.getElementById('jHigh').value);
    }
    Jovan.prototype.calcBMI = function() {
        return this.weight/(this.high*this.high);
    }

    const marko = new Marko();
    let mBMI = marko.calcBMI();

    const jovan = new Jovan();
    let jBMI = jovan.calcBMI();
    
    if(mBMI>jBMI) {
        alert('Markov BMI ' + mBMI + ' je veci od Jovanovog BMI ' + jBMI);
    }
    else if(mBMI<jBMI) {
        alert('Jovanov BMI ' + jBMI + ' je veci od Markovog BMI ' + mBMI);
    }    
    else {
        alert('Marko i Jovan imaju jednak BMI ' + jBMI);
    }
})