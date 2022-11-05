export default class EventService {


  static async remove(participantId, id){
    try{
      const requestOptions = {
          method: 'DELETE',
      };
      await fetch('http://localhost:8080/routine/' + participantId + '/events/' + id, requestOptions);
      } catch(e) {
      alert(e);
    }
  };

  static async change(participantId, id,description, startTime, endTime) {
    try {
      const requestOptions = {
          method: 'PATCH',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({id,startTime, endTime,  description})
      }
      const response = await fetch('http://localhost:8080/routine/' + participantId + '/events', requestOptions);
      return await response.json();
    } catch (e){
      alert(e)
    }
  }


  static async save(id,description, startTime, endTime){
    try{
      const requestOptions = {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({description, startTime, endTime})
      }
      const response = await fetch('http://localhost:8080/routine/' + id + '/events', requestOptions);
      return await response.json();
    } catch (e){
      alert(e)
    }
  }
}
