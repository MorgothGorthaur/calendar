import React, {useState, useEffect} from 'react';
import {Card, Button} from 'react-bootstrap';
import EventItem from './EventItem';
import EventService from '../API/EventService';
import Modal from '../UI/Modal/Modal';
import EventForm from './EventForm';
const EventsList = ({events, remove, change, dayId}) =>{
 const [modal, setModal] = useState(false)


 const openEventForm = (event) => {
   setModal(true);

 }
 if (! events.length) {
   return (
     <h1> events not found! </h1>
   )
 }

  return (

        <div className = "day_list">
          <div>
            <h3  style = {{textAlign: 'center'}}>
              "events"
            </h3>
          </div>
          <div>
            {events.map( event =>
              <div>
              <EventItem event = {event} remove = {remove} change = {change} dayId = {dayId} key = {event.id} />


              </div>
            )}

          </div>










        </div>
  );
};
export default EventsList;
