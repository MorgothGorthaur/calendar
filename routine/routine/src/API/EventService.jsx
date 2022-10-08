export default class EventService {

  static async getAll(id){
    try{
      const response = await fetch('http://localhost:8080/routine/' + id);
      const data = await response.json();
      return data;
    } catch (e){
      alert(e);
    }

    //alert(data.errors);

  }
  static async removeEvent(id){
    const requestOptions = {
        method: 'DELETE',
    };

    const response = await fetch('http://localhost:8080/routine/events/' + id, requestOptions);
    const data = await response.json();
    alert(data)
    return data;
  }

  static async changeEvent(id, time, description,dayId) {
    if (time){
      time = time+":00"
    }
    const requestOptions = {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({id, description, time, dayId})
    }
    const response = await fetch('http://localhost:8080/routine/events', requestOptions);
    try {
      const data = await response.json();
      return data;
    } catch (e){
      return "ok"
    }
  }


  static async addNewEvent(dayId, time, description){
    if (time){
      time = time+":00"
    }
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({dayId, description, time})
    }
    const response = await fetch('http://localhost:8080/routine/events', requestOptions);
    try {
      const data = await response.json();
      console.log(data)
      return data;
    } catch (e){
      return "ok"
    }
  }
}
