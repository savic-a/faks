let firebaseUrl = 'https://vezbe-9-default-rtdb.europe-west1.firebasedatabase.app/songs.json';

let audioPlaylist = document.getElementById('audioPlaylist');
let player = document.getElementById('player');

let request = new XMLHttpRequest();

request.onreadystatechange = function () {
    if (this.readyState == 4) {
        if (this.status == 200) {
            let songs = JSON.parse(request.responseText);

            for (let i = 0; i < songs.length; i++) {
                let song = songs[i];
                 console.log(song);

                let songLi = document.createElement('li');

                let numberSpan = document.createElement('span');
                numberSpan.classList.add('orderNumber');
                numberSpan.innerText = i + 1;
                songLi.appendChild(numberSpan);

                let artistSpan = document.createElement('span');
                artistSpan.classList.add('artistSpan');
                artistSpan.innerText = song.artist;
                songLi.appendChild(artistSpan);

                let songSpan = document.createElement('span');
                songSpan.classList.add('songSpan');
                songSpan.innerText = song.title;
                songLi.appendChild(songSpan);

                songLi.setAttribute('data-url', song.link);

                songLi.addEventListener('click', function(e) {
                    let artist = this.querySelector('.artistSpan').innerText;
                    let playerArtist = document.getElementById('artistElement');
                    playerArtist.innerText = artist;

                    document.getElementById('titleElement').innerText = this.querySelector('.songSpan').innerText;

                    let url = this.getAttribute('data-url');
                    player.setAttribute('src', url);
                    player.play();
                });

                
                audioPlaylist.appendChild(songLi);
            }

        } else {
            alert('Error happend during request for songs');
        }

    }
}

request.open('GET', firebaseUrl);
request.send();
