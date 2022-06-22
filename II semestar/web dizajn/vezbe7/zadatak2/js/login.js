let loginForm = document.getElementById('loginForm');

loginForm.addEventListener('submit', function(e) {
    e.preventDefault();

    let username = document.getElementById('txtUsername').value.trim();
    let password = document.getElementById('txtPassword').value.trim();

    if(username == '' || password == ''){
        alert('Molimo Vas unesite validne podatke.');
    } else {
        let request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4) {
                if (this.status == 200) {

                    let users = JSON.parse(request.responseText);
                    let loggedUser = '';
                    
                    for (let i = 0; i < users.length; i++) {
                        let user = users[i];
                        
                        if(user.email == username && user.username == password) {
                            loggedUser = user;
                            break; 
                        }
                    } 

                    if (loggedUser == '') {
                        alert('Niste uneli dobre login podatke!');
                    } else {
                        window.location.replace('index.html?user='+loggedUser.name);
                    }

                } else{
                    alert('Doslo je do greske!' + this.status);
                }
            }
        } 

        let url = loginForm.getAttribute('action');
        request.open('GET', url);
        request.send();
    } 
});