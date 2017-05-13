package com.example.rashwan.playacademy.Models;

public class Comment {
	
	
	private int commentID;
	private String description;
	private User commentor;

	public Comment(int commentID,User user, String description) {
		this.commentor=user;
		this.description=description;
		this.commentID=commentID;
	}

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

	public User getCommentor() {
		return commentor;
	}
	public void setCommentor(User commentor) {
		this.commentor = commentor;
	}

	
}
