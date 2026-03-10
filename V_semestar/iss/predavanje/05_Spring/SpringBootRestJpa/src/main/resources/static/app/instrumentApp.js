var app = new Vue({ 
    el: '#instruments',
    data: {
        instruments: null
    },
    mounted () {
        axios
          .get('api/v2/instrument')
          .then(response => (app.instruments = response.data));
    } 
});