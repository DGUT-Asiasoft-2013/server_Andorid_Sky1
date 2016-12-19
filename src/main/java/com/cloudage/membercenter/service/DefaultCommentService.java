package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Comment;
import com.cloudage.membercenter.repository.ICommentRepository;

/*
 * 实现类
 */
@Component
@Service
@Transactional
public class DefaultCommentService implements ICommentService {

	@Autowired
	ICommentRepository iCommentRepository;

	@Override
	public Comment save(Comment comment) {
		return iCommentRepository.save(comment);
	}

	@Override
	public Page<Comment> findCommentNumofBook(int userId, int page) {
		// ����
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest request = new PageRequest(page, 50, sort); // �����20Ϊ�������������20��

		return iCommentRepository.findCommentNum(userId, request);
	}

	@Override
	public Page<Comment> findAllCommentofAuthor(int userId, int page) {
		// ����
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest request = new PageRequest(page, 60, sort);

		return iCommentRepository.findAllCommentofAuthor(userId, request);
	}
}
