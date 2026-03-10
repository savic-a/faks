function report() {
	alert('AAAA');
}

var app = new Vue({ 
    el: '#studenti',
    data: {
        students: null,
        cities: null,
        title: "Primer Vue.js tehnologije na spisku studenata",
        mode: "BROWSE",
        selectedStudent: {id:'', firstName:'', lastName:'', birthCity:{id:'', name:''}},
        searchField: ""
    },
    mounted () {
        axios
          .get('api/v2/student')
          .then(response => (app.students = response.data));
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
    	updateStudent : function(student) {
			if (this.mode === 'NEW') {
				student.birthCity = {id : 1};
				$.ajax({
		    		url: "api/v2/student",
		    		type:"POST",
		    		data: JSON.stringify(student),
		    		contentType:"application/json",
		    		dataType:"json",
		    		complete: function(data) {
						if (data.status == 200) {
							$.ajax({
					    		url: "api/v2/student",
					    		type:"GET",
					    		contentType:"application/json",
					    		dataType:"json",
					    		complete: function(data2) {
					    			app.students = JSON.parse(data2.responseText);
					    			toast('Student added');
		    			    		app.selectedStudent.firstName = '';
	    							app.selectedStudent.lastName = '';
	    							app.selectedStudent.birthCity = {id:'', name:''};
	    							app.mode = 'BROWSE';
					    		}
					    	});
					    } else {
							alert(JSON.parse(data.responseText).message);
							axios.get('api/v2/student')
          					.then(response => (app.students = response.data));
						} 			
		    		}
		    	});
			} else {
	    		axios
	    		.put("api/v2/student", student)
	    		.then(response => {toast('Student updated');app.mode = 'BROWSE';})
	    		.catch(error => alert(error.response.data.message));
    		}
    	},
    	deleteStudent : function(student) {
			if (confirm('This will delete the student with id ' + student.id + '. Are you sure?'))
			{
				$.ajax({
		    		url: "api/v2/student/" + student.id,
		    		type:"DELETE",
		    		contentType:"application/json",
		    		dataType:"json",
		    		complete: function(data) {
						$.ajax({
				    		url: "api/v2/student",
				    		type:"GET",
				    		contentType:"application/json",
				    		dataType:"json",
				    		complete: function(data2) {
				    			app.students = JSON.parse(data2.responseText);
				    			toast('Student deleted');
				    		}
				    	}); 			
		    		}
		    	});
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
    	}
    }
});