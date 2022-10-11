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
      return data.events;
    } catch (e) {
      alert(e);
    }
  }
}
