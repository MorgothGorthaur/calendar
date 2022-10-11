export default class ParticipantService {
  static async getAll() {
    try {
      const response = await fetch('http://localhost:8080/routine');
      const data = await response.json();
      return data;
    } catch(e) {
      alert(e);
    }
  }

  static async getFull(id) {
    try {
      const response = await fetch('http://localhost:8080/routine/' + id);
      const data = await response.json();
      return data;
    } catch (e) {
      alert(e);
    }
  }

  static async delete(id){
    try {
        const requestOptions = {
          method: 'DELETE',
        };
        const response = await fetch ('http://localhost:8080/routine/' + id, requestOptions);
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
      const data = await response.json();
      return data;
    } catch (e) {
      alert(e);
    }
  }
}
