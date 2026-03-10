var app = new Vue({ 
    el: '#persons',
    data: {
        persons: null,
        personAccounts: null,
        personName: null
    },
    mounted () {
        axios
          .get('/api/v2/person')
          .then(response => (app.persons = response.data));
    } , 
    methods: {
		getAccountsFor: function(person) {
			this.personName = person.name;
        axios
          .get('/api/v2/person/' + person.id)
          .then(response => (app.personAccounts= response.data));
		}
	}

});