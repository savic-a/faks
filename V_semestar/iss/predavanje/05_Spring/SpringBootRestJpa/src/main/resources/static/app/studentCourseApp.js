var app = new Vue({ 
    el: '#students',
    data: {
        students: null,
        studentCourses: null,
        studentName: null,
        courseName: null,
        courseStudents: null
    },
    mounted () {
        axios
          .get('/api/v2/student2')
          .then(response => (app.students= response.data));
    } , 
    methods: {
		getCoursesFor: function(student) {
			this.studentName = student.name;
			this.courseName = null;
			this.courseStudents = null;
        axios
          .get('/api/v2/student2/' + student.id)
          .then(response => (app.studentCourses= response.data));
		},
		
		getStudentsFor: function(course) {
			this.courseName = course.name;
        axios
          .get('/api/v2/student2/course/' + course.id)
          .then(response => (app.courseStudents = response.data));
		}
	}

});