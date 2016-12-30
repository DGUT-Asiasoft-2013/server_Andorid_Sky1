package com.cloudage.membercenter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Comment;

//PagingAndSortingRepository
@Repository
public interface ICommentRepository extends PagingAndSortingRepository<Comment, Integer>{
	//get count of comments
	@Query("select count(*) from Comment comment where comment.book.id = ?1")
	int countCommentsNumber(int comment_id);
	
	//��ĳ�������������
	@Query("from Comment comment where comment.book.id = ?1")
	Page<Comment> findCommentNum(int userId,Pageable page);        
	
	//��ĳ���˵���������
	@Query("from Comment comment where comment.book.user.id = ?1")
	Page<Comment> findAllCommentofAuthor(int userId,Pageable page);
}
