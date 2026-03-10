var app = new Vue({ 
    el: '#studenti',
    data: {
        students: null,
        username: null,
        cities: null,
        title: "CRUD operacije",
        mode: "BROWSE",
        selectedStudent: {id:'', firstName:'', lastName:'', birthCity:{id:'', name:''}},
        searchField: ""
    },
    mounted () {
		this.username = window.localStorage.getItem('username');
        axios
          .get('api/v2/student', {
			headers:{
			    Authorization: 'Bearer ' + window.localStorage.getItem('jwt')
			  }
			})
          .then(response => (app.students = response.data))
          .catch(error => (window.location="index.html"));
        axios
          .get('api/v2/city')
          .then(response => (app.cities = response.data));
          
    },
    methods: {
    	selectStudent : function(student) {
    		if (this.mode == 'BROWSE') {
    			this.selectedStudent = student;
    		}    
    	},
    	newStudent : function() {
    		this.mode = 'NEW';
    		this.selectedStudent.id = '';
    		this.selectedStudent.firstName = '';
    		this.selectedStudent.lastName = '';
    	},
    	editStudent : function() {
    		if (this.selectedStudent.id == '')
    			return;
    		this.backup = [this.selectedStudent.firstName, this.selectedStudent.lastName, this.selectedStudent.birthCity];
    		this.mode = 'EDIT';
    	},
    	updateStudent : async function(student) {
			if (this.mode === 'NEW') {
				student.birthCity = {id : 1};
				const firstRequest = await axios.post("api/v2/student", student, {
					headers:{
					    Authorization: 'Bearer ' + window.localStorage.getItem('jwt')
					  }
					})
	    		.catch(error => alert(error.response.data.message));
	    		if (firstRequest) {
		    		let added = firstRequest.data;
		    		toast('Student ' + added.firstName + ' ' + added.lastName  + ' added');
	      			const secondRequest = await axios
			          .get('api/v2/student', {
						headers:{
						    Authorization: 'Bearer ' + window.localStorage.getItem('jwt')
						  }
						});
					if (secondRequest) {
	      				app.students = secondRequest.data;
	      				app.selectedStudent.firstName = '';
						app.selectedStudent.lastName = '';
						app.selectedStudent.birthCity = {id:'', name:''};
						app.mode = 'BROWSE';
	      			}
	      		} else {
			        axios
			          .get('api/v2/student', {
						headers:{
						    Authorization: 'Bearer ' + window.localStorage.getItem('jwt')
						  }
						})
			          .then(response => (app.students = response.data))
			          .catch(error => (window.location="index.html"));
				}
			} else {
	    		axios
	    		.put("api/v2/student", student, {
					headers:{
					    Authorization: 'Bearer ' + window.localStorage.getItem('jwt')
					  }
					})
	    		.then(response => {toast('Student updated');app.mode = 'BROWSE';})
	    		.catch(error => alert(error.response.data.message));
    		}
    	},
    	deleteStudent : async function(student) {
			if (confirm('This will delete the student with id ' + student.id + '. Are you sure?'))
			{
				const firstRequest = await axios
	    		.delete("api/v2/student/" + student.id, {
					headers:{
					    Authorization: 'Bearer ' + window.localStorage.getItem('jwt')
					  }
					})
	    		.catch(error => alert(error.response.data.message));
				if (firstRequest) {
		    		let deleted = firstRequest.data;
		    		toast('Student ' + deleted.firstName + ' ' + deleted.lastName + ' deleted');
		    		app.mode = 'BROWSE';
		    		const secondRequest = await axios
			          .get('api/v2/student', {
						headers:{
						    Authorization: 'Bearer ' + window.localStorage.getItem('jwt')
						  }
						});
					if (secondRequest) {
	      				app.students = secondRequest.data;
	      			}
		    	}
			}
		},
    	cancelEditing : function() {
			if (app.mode === 'NEW') {
	    		app.selectedStudent.firstName = '';
	    		app.selectedStudent.lastName = '';
	    		app.selectedStudent.birthCity = {id:'', name:''};
			} else {
	    		app.selectedStudent.firstName = app.backup[0];
	    		app.selectedStudent.lastName = app.backup[1];
	    		app.selectedStudent.birthCity = app.backup[2];
    		}
    		app.mode = 'BROWSE';
    	},
	    logout : function() {
			window.localStorage.removeItem('jwt');
			window.localStorage.removeItem('username');
			window.location = "index.html";
		}
    }
});