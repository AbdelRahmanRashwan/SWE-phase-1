package com.playacademy.comments;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playacademy.game.model.Game;
import com.playacademy.user.model.User;

@Entity
@Table(name="comments")
public class Comment {
	
	
	private int commentID;

	@Column(name="description")
	private String description;
	
	private User commentor;
	
	@JsonIgnore
	private Game game;
	
	public Comment(){}
	
	public Comment(User user, Game game, String description) {
		this.commentor=user;
		this.game=game;
		this.description=description;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne
	@JoinColumn(name="userId", nullable=false)
	public User getCommentor() {
		return commentor;
	}
	public void setCommentor(User commentor) {
		this.commentor = commentor;
	}
	
	@ManyToOne
	@JoinColumn(name="gameId", nullable=false)
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
}
