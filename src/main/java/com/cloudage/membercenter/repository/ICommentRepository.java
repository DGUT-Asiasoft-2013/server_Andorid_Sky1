package com.cloudage.membercenter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Comment;

//PagingAndSortingRepository�߱��˷�ҳ��Ч��
@Repository
public interface ICommentRepository extends PagingAndSortingRepository<Comment, Integer>{

	
	//���е��õ�book��Ҫ��Comment����Ķ����һ��
	@Query("from Comment comment where comment.book.id = ?1")
	Page<Comment> findCommentNum(int userId,Pageable page);         //������
	
	//ĳ�����յ�����������
	@Query("from Comment comment where comment.book.user.id = ?1")
	Page<Comment> findAllCommentofAuthor(int userId,Pageable page);
}
