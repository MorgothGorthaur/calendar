export default class ParticipantService {
  static async getAll() {
    try {
      const response = await fetch('http://localhost:8080/calendar');
      return await response.json();
    } catch(e) {
      alert(e);
    }
  }

  static async getParticipant(access_token) {
    try {
      const response = await fetch('http://localhost:8080/calendar/user', {
   		   method: 'GET',
			   mode: 'cors',
		     headers:{
			      'Authorization' : 'Bearer ' + access_token
    		 }
		   });
       if( response. ok) {
         return await response.json();
       }
       alert("you must authorize at first!")
       return {hasError : true}
    } catch (e) {
      alert(e);
    }
  }

  static async delete(id){
    try {
        const requestOptions = {
          method: 'DELETE',
        };
        await fetch ('http://localhost:8080/routine/' + id, requestOptions);
    } catch (e) {
      alert(e);
    }
  }

  static async save(firstName, lastName, email, password){
    try {
      const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type' : 'application/json'},
        body: JSON.stringify({firstName, lastName, email, password})
      };
      const response = await fetch ('http://localhost:8080/calendar', requestOptions);
      return await response.json();
    } catch (e) {
      alert(e);
    }
  }

  static async change(id, firstName, lastName){
    try {
      const requestOptions = {
        method: 'PATCH',
        headers: { 'Content-Type' : 'application/json'},
        body: JSON.stringify({id, firstName, lastName})
      };
      const response = await fetch ('http://localhost:8080/routine', requestOptions);
      return await response.json();
    } catch (e) {
      alert(e);
    }
  }
}
