export default class ParticipantService {
  static async getAll() {
    try {
      const response = await fetch('http://localhost:8080/routine');
      return await response.json();
    } catch(e) {
      alert(e);
    }
  }

  static async getFull(id) {
    try {
      const response = await fetch('http://localhost:8080/routine/' + id);
      return await response.json();
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

  static async save(firstName, lastName){
    try {
      const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type' : 'application/json'},
        body: JSON.stringify({firstName, lastName})
      };
      const response = await fetch ('http://localhost:8080/routine', requestOptions);
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
