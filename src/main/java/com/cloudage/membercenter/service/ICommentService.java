package com.cloudage.membercenter.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Comment;

public interface ICommentService {

	Comment save(Comment comment);
	Page<Comment> findCommentNumofBook(int userId, int page); 
	Page<Comment> findAllCommentofAuthor(int userId, int page);

}
