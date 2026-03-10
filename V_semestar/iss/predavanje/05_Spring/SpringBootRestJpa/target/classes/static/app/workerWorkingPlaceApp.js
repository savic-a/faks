var app = new Vue({ 
    el: '#workers',
    data: {
        workers: null,
        magacinWorkers: null,
        workingPlaceName: null
    },
    mounted () {
        axios
          .get('/api/v2/workerworkingplace')
          .then(response => (app.workers = response.data));
    } , 
    methods: {
		getWorkersFor: function(workingPlace) {
			this.workingPlaceName = workingPlace;
        axios
          .get('/api/v2/workerworkingplace/' + workingPlace)
          .then(response => (app.magacinWorkers = response.data));
		}
	}

});