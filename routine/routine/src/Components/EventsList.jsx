import React, {useState, useEffect} from 'react';
import EventItem from './EventItem';
import Modal from '../UI/Modal/Modal';
import EventForm from './EventForm';
import {Button} from 'react-bootstrap';

import Loader from '../UI/Loader/Loader';
import EventService from '../API/EventService';

const EventsList = ({/*events, remove, change,*/ dayId}) =>{
  const [modal, setModal] = useState(false);
  const [events, setEvents] = useState([]);
  const [sortedEvents, setSortedEvents] = useState([]);
  const [eventsLoading, setEventsLoading] = useState(false);




 useEffect(()=>{
   setEventsLoading(true);
   setTimeout ( () => {
     fetchEvents();
     setEventsLoading(false);
   }, 1000);
  //
 },[])
 useEffect (()=>{

   setSortedEvents([...events].sort( (a,b) => new Date('1/1/1999 ' + a.date) - new Date('1/1/1999 ' + b.date)))


 },[events]);
 async function fetchEvents() {
  const response = await EventService.getAll(dayId);
  setEvents(response.events)
 };


 const removeEvent = (event) => {
   EventService.removeEvent(event.id);
   setEvents(events.filter(d => d.id !== event.id ))
 };
 const changeEvent = (event) => {
   setEvents([...events.filter(d => d.id !== event.id ), event])
 };
 const addNewEvent = (event) => {
   setEvents([...events,event])
   setModal(false)
 };
  return (
        <div>
          {eventsLoading? (
            <div style = {{display: 'flex', justifyContent: 'center'}}>
              <Loader />
            </div>
          ):(

          <div >
            <div>
              {events.length ? (
                <div className = "day_list">
                  <h3  style = {{textAlign: 'center'}}>
                    "events"
                  </h3>

                  {sortedEvents.map( event =>
                      <div>
                      <EventItem event = {event} remove = {removeEvent} change = {changeEvent} dayId = {dayId} key = {event.id} />
                      </div>
                  )}
                </div>

              ):(
                <h1> events not found! </h1>
              )}
            </div>
            <div>
              <Button onClick = {() => setModal(true) } > add Event </Button>
              <Modal visible = {modal} setVisible = {setModal}> <EventForm createOrUpdate = {addNewEvent} dayId = {dayId}/> </Modal>
            </div>
          </div>
        )}
      </div>
  );
};
export default EventsList;
