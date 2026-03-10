let firebaseUrl = 'https://vezbe-9-default-rtdb.europe-west1.firebasedatabase.app/videos.json';
let titleElement = document.getElementById('titleElement');
let player = document.getElementById('player');
let playList = document.getElementById('videoPlaylist');

let request = new XMLHttpRequest();

request.onreadystatechange = function(e){
    if (this.readyState === 4){
        if (this.status === 200){
            let videos = JSON.parse(this.responseText);

            for(let i=0; i<videos.length; i++){
                let currentVideo = videos[i];
                console.log(currentVideo);

                let videoLi = document.createElement('li');
                
                let orderNumberSpan = document.createElement('span');
                orderNumberSpan.classList.add('orderNumber');
                orderNumberSpan.innerText = i+1;
                videoLi.appendChild(orderNumberSpan);

                let titleSpan = document.createElement('span');
                titleSpan.classList.add('songSpan');
                titleSpan.innerText = currentVideo.title;
                videoLi.appendChild(titleSpan);

                videoLi.setAttribute('data-url', currentVideo.link);

                videoLi.addEventListener('click', function(e){
                    titleElement = this.querySelector('.songSpan').innerText;
                    let link = this.getAttribute('data-url');
                    player.setAttribute('src', link);
                    player.play();
                })
                playList.appendChild(videoLi);
            }
        }else {
            alert('Greska prilikom preuzimanja podataka!')
        }
    }
}
request.open('GET', firebaseUrl);
request.send();