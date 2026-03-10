var app = new Vue({
	el: '#login',
    data: {
        username: null,
        password: null
    },
    mounted() {
		if (isLogedIn()) {
			window.document.location = "crud.html";
		}
	},
	methods: {
		login : function() {
			let user = {username: app.username, password: app.password};
			axios
          .post('api/v2/login', user)
          .then(response => {
				window.localStorage.setItem('jwt', response.data.jwt); 
				window.localStorage.setItem('username', response.data.username); 
				window.location = 'crud.html';
		   })  // toast(response.data.username)
          .catch(error => toast(error.response.data.message, true));
		}
	}
});
