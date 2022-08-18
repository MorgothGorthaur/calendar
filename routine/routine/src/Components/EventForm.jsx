import React, {useState, useEffect} from 'react';
import {Form, Button} from 'react-bootstrap';
import Input from '../UI/Input/Input';
import EventService from '../API/EventService';
const EventForm = ({createOrUpdate,event, dayId}) => {
  const [time, setTime] = useState();
  const [description, setDescription] = useState();
  const[id,setId] = useState();
  const validation = (data) => {
    if (data.errors){
      alert(data.errors);
    } else {
      const id = data.id;
      const dayId = data.dayId;
      const newEvent = {id, time, description, dayId};
      createOrUpdate(newEvent);
    }
  }
  const addNewEvent =  (e) =>{
    e.preventDefault()
    if (event) {
        EventService.changeEvent(id, time, description, dayId).then(data => {
        validation(data)
        })
    } else {
      EventService.addNewEvent(dayId, time, description).then( data => {
      console.log(data)
      validation(data)
      })
    }
  }
  useEffect ( () => {
    if (event) {
      setTime(event.time)
      setDescription(event.description)
      setId(event.id)
    }
  },[event])
  return (
    <Form onSubmit = {addNewEvent}>
      <Input type = "text" placeholder= "description" value = {description} onChange = { e => setDescription(e.target.value)}/>
      <Input type = "time" placeholed = "time" value = {time} onChange = {e => setTime(e.target.value)}/>

      <Button type = "submit"> {event ?  "update" : "create"} </Button>
    </Form>
  )
};
export default EventForm;
