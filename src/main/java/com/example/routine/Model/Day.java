package com.example.routine.Model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "days")
public class Day {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  
	  //@Temporal(TemporalType.DATE)
	  @Column(name = "day_date")
	  private Date date;
	  
	  @Column(name = "day_name")
	  private String name;
	  @OneToMany(mappedBy = "dayId", cascade = CascadeType.ALL, orphanRemoval = true)
	  //@JoinColumn(name = "day_id")
	  //https://www.baeldung.com/jpa-joincolumn-vs-mappedby - вспомнить
	  private List<Event> events=new ArrayList<>();
	  @Column(name = "day_actuality")
	  private DayActuality dayActuality;
	  
	  
	  public DayActuality getDayActuality() {
		return dayActuality;
	}

	public void setDayActuality(DayActuality dayActuality) {
		this.dayActuality = dayActuality;
	}

	public List<Event> getEvents() {
	  return events;
	  }

	  public void addEvent(Event event) {
		  events.add(event);
		  //event.setDay(this);
		  event.setDayId(id);
	  }
	  public void removeEvent(Event event) {
		  System.out.println(events.size());
		  events.remove(event);
		  System.out.println(events.size());
		  //event.setDay(null);
		  event.setDayId(null);
		  event.setDescription("g");
	  }
		public Long getId() {
			return id;
		}
	
		public void setId(Long id) {
			this.id = id;
		}
	
		public Date getDate() {
			return date;
		}
	
		public void setDate(Date date) {
			this.date = date;
		}
	
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}
	
	  
}
