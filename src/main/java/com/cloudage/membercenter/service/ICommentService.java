package com.cloudage.membercenter.service;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.Comment;

public interface ICommentService {

	Comment save(Comment comment); // 保存评论

	Page<Comment> findCommentNumofBook(int userId, int page); // 找某本书的评论

	Page<Comment> findAllCommentofAuthor(int userId, int page); // 找某个人的所有评论
}
