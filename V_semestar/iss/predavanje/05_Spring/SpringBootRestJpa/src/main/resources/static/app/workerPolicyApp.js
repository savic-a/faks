var app = new Vue({ 
    el: '#workers',
    data: {
        workers: null
    },
    mounted () {
        axios
          .get('/api/v2/workerpolicy')
          .then(response => (app.workers = response.data));
    } 
});