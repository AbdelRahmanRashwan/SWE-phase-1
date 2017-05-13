package com.playacademy.course.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playacademy.Notification.NewGameNotification;
import com.playacademy.Notification.Notification;
import com.playacademy.courseattendance.model.CourseAttendance;
import com.playacademy.game.controller.Subject;
import com.playacademy.game.model.Game;
import com.playacademy.user.model.*;

@Entity
@Table(name = "courses")
public class Course implements Subject {

	private long courseId;

	@Column(name = "courseName")
	private String courseName;

	@Column(name = "courseDescription")
	private String courseDescription;
	
	@JsonIgnore
	private Set<Game> games;
	
	private Teacher creator;
	
	
	@JsonIgnore
    private Set<CourseAttendance> attendance_sheets;

	public Course() {
		attendance_sheets = new HashSet<CourseAttendance> ();
	}

	public Course(String courseName, String courseDescription) {
		this.courseName = courseName;
		this.courseDescription = courseDescription;
	}

	public Course(long courseID, String courseName, String courseDescription) {
		this.courseId = courseID;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
	}

	// Setters
	public void setCourseId(long courseID) {
		this.courseId = courseID;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	
	public void setGames(Set<Game> games) {
		this.games = games;
	}
	
	public void setCreator(Teacher creator) {
		this.creator = creator;
	}

	public void setAttendance_sheets(Set<CourseAttendance> attendance_sheets) {
		this.attendance_sheets = attendance_sheets;
	}

	
	// add
	public void addGame(Game game) {
		game.setCourse(this);
		games.add(game);
		notifyObservers("New Game", "Teacher ( "+creator.getFirstName()+" ) added a game ( "+ game.getName()+" ) to course ( "+courseName+" ).");
	}
	
	public void addAttendance(CourseAttendance attendance) {
		attendance.setCourse(this);
		attendance_sheets.add(attendance);
	}

	// Getters

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getCourseId() {
		return courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	// Relations
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "course")
	public Set<Game> getGames() {
		return games;
	}
	@OneToMany(mappedBy="course", cascade=CascadeType.ALL)
	public Set<CourseAttendance> getAttendance_sheets() {
		return attendance_sheets;
	}
	@ManyToOne
    @JoinColumn(name="creatorId", nullable=false)
	public Teacher getCreator() {
		return creator;
	}

	@Override
	public void notifyObservers(String gameTitle,String gameDescription) {
		// TODO Auto-generated method stub
		Iterator<CourseAttendance> it =attendance_sheets.iterator();
		Notification n = new NewGameNotification();
		n.setNotificationTitle(gameTitle);
		n.setNotificationDescription(gameDescription);
		
		while(it.hasNext())
			it.next().getStudent().update(n);
	}

	
}
