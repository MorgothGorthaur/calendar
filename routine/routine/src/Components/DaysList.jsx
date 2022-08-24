import React, {useState, useEffect} from 'react';
import DayItem from './DayItem';
import {Button, Modal} from 'react-bootstrap';
import Loader from '../UI/Loader/Loader';
import DayForm from './DayForm';
//import Modal from '../UI/Modal/Modal';
import {CSSTransition,TransitionGroup} from 'react-transition-group';
import DaysService from '../API/DaysService';
const DaysList = (/*{days, remove, update}*/) => {
  const [sortedDays, setSortedDays] = useState([]);
  const [modal, setModal] = useState(false);
  const [days, setDays] =  useState([]);
  const [daysLoading, setDaysLoading] = useState(false);
  useEffect ( () => {
      setSortedDays ([...days].sort( (a,b) => new Date(a.date).getTime() - new Date(b.date).getTime() ))
  },[days]);
  useEffect ( () => {
    setDaysLoading(true);
    setTimeout ( () => {
      fetchDays();
      setDaysLoading(false);
    }, 1000);


  },[]);
  const sortDays = (sort) => {
    if(sort === "date"){
      setSortedDays ([...days].sort( (a,b) => new Date(a.date).getTime() - new Date(b.date).getTime() ))
    }
    if (sort ==="today"){
      setSortedDays([...days.filter(d => d.dayActuality === "TODAY")])
    }
    if (sort === "next"){
      setSortedDays([...days.filter(d => d.dayActuality === "FUTURE")])
    }
  };

  const removeDay = (day) =>{
    DaysService.removeDay(day.id);
    setDays(days.filter(d => d.id !== day.id ))
  };
  const createDay = (newDay) =>  {
        setDays([...days, newDay])
        setModal(false)
  };
  const updateDay = (newDay) => {
    setDays([...days.filter(d => d.id !== newDay.id ), newDay])
    setModal(false)
  }

  async function fetchDays() {
    const response = await DaysService.getAll();
    setDays(response);
  }
  return (

    <div>
      <div className = "day_list">
        <div>
          <select onChange = {(e) => sortDays(e.target.value)}>
            <option value = "date"> all </option>
            <option value = "today"> today </option>
            <option value = "next"> next </option>
          </select>
        </div>
        {daysLoading ?(
          <div style = {{display: 'flex', justifyContent: 'center'}}>
            <Loader />
          </div>
        ):(

          <div>
            {sortedDays.length ? (
              <div>
                <h1 style = {{textAlign: 'center'}}>
                  "days"
                </h1>
                <TransitionGroup>
                {sortedDays.map( day =>
                  <CSSTransition key = {day.id} timeout = {1000} classNames = "element" >
                    <DayItem day = {day} remove = {removeDay} update = {updateDay} />
                  </CSSTransition>
                )}
                </TransitionGroup>
              </div>
            ): ( <h1 className = "day_list"> Days not found! </h1>) }
          </div>
        )}
      </div>
      <div className = "day_item">
        <Modal /*visible = {modal} setVisible = {setModal}*/ show = {modal} onHide = {setModal}> <DayForm createOrUpdate = {createDay}/> </Modal>
        <Button onClick = { () => setModal(true)}> add new day </Button>
      </div>
    </div>
  )
};
export default DaysList;
