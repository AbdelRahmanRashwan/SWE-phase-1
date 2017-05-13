package com.playacademy.Notification;

import org.springframework.data.repository.CrudRepository;

public interface NotificationsRepo <T extends Notification> extends CrudRepository<T, Long> {

}
