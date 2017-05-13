package com.playacademy.user.model;

import com.playacademy.Notification.Notification;

public interface Observer {
	void update(Notification n);
}
