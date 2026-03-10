function isLogedIn() {
	let user = window.localStorage.getItem('user');
	if (user) {
		if (user.logedIn) {
			return true;
		}
	} 
	return false;
}