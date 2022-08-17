import EventItem from './EventItem';
const EventsList = ({events, remove, change, dayId}) =>{
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
