package com.cloudage.membercenter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Comment;

//PagingAndSortingRepository具备了翻页的效果
@Repository
public interface ICommentRepository extends PagingAndSortingRepository<Comment, Integer>{

	
	//此中调用的book需要跟Comment类里的定义的一样
	@Query("from Comment comment where comment.book.id = ?1")
	Page<Comment> findCommentNum(int userId,Pageable page);         //找评论
	
	//某个人收到的所有评论
	@Query("from Comment comment where comment.book.user.id = ?1")
	Page<Comment> findAllCommentofAuthor(int userId,Pageable page);
}
