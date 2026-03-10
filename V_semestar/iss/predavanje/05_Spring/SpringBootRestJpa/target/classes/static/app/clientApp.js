var app = new Vue({ 
    el: '#clients',
    data: {
        clients: null
    },
    mounted () {
        axios
          .get('api/v2/client')
          .then(response => (app.clients = response.data));
    }, 
    methods: {
		getImage : function(client) {
			return 'api/v2/client/' + client.id;
		}
	}
});