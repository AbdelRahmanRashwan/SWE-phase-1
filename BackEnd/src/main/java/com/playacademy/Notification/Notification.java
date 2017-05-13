package com.playacademy.Notification;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playacademy.user.model.User;

@Entity
@Inheritance
@DiscriminatorColumn(name = "notification_type")
@Table(name = "notifications")
public class Notification {
	
	@JsonIgnore
	private User receiver;
	
	
	private long notificationId;
	
	@Column(name = "notificationTitle")
	private String notificationTitle;
	
	@Column(name = "notificationDescription")
	private String notificationDescription;

	@ManyToOne
	@JoinColumn(name = "userId",nullable = false)
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}
	public String getNotificationTitle() {
		return notificationTitle;
	}
	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}
	public String getNotificationDescription() {
		return notificationDescription;
	}
	public void setNotificationDescription(String notificationDescription) {
		this.notificationDescription = notificationDescription;
	}
}
