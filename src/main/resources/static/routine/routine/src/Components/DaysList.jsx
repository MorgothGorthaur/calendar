import React, {useState, useEffect} from 'react';
import DayItem from './DayItem';
const DaysList = ({days, remove, update}) => {
  const [sortedDays, setSortedDays] = useState('')
  useEffect ( () => {
      setSortedDays ([...days].sort( (a,b) => new Date(a.date).getTime() - new Date(b.date).getTime() ))
  },[days])
  const sortDays = (sort) => {
    //setDays ([...days].sort( (a,b) => a[sort].localeToCompare(b[sort])))
    const current = new Date();
    const date = `${current.getFullYear()}-${current.getMonth()+1}-${current.getDate()}`;
    if(sort === "date"){
      setSortedDays ([...days].sort( (a,b) => new Date(a.date).getTime() - new Date(b.date).getTime() ))
    }
    if (sort ==="today"){

      setSortedDays([...days.filter(d => new Date(d.date).getTime() === new Date(date).getTime())])
    }
    if (sort === "next"){
        setSortedDays([...days.filter(d => new Date(d.date).getTime() !== new Date(date).getTime())])
    }
  }

  return (


    <div className = "day_list">
      <div>
        <select onChange = {(e) => sortDays(e.target.value)}>
          <option value = "date"> all </option>
          <option value = "today"> today </option>
          <option value = "next"> next </option>
        </select>
      </div>

      {sortedDays.length ? (
        <div>
        <h1 style = {{textAlign: 'center'}}>
          "days"
        </h1>

        {sortedDays.map( day =>
          <div>
          <DayItem day = {day} remove = {remove} update = {update} key = {day.id} />
          </div>
        )}
        </div>
      ): ( <h1 className = "day_list"> Days not found! </h1>) }
    </div>

  )
};
export default DaysList;
