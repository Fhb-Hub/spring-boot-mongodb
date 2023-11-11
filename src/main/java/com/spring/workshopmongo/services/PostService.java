package com.spring.workshopmongo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.workshopmongo.domain.Post;
import com.spring.workshopmongo.repository.PostRepository;
import com.spring.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository repo;

	public Post findById(String id) {
		Optional<Post> post = repo.findById(id);
		return post.orElseThrow(() -> new ObjectNotFoundException("Post não encontrado"));
	}

	public List<Post> findByTitle(String text) {
		return repo.findByTitleContainingIgnoreCase(text);
	}

	public List<Post> findByBody(String body) {
		return repo.findByBody(body);
	}

	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		// Converte as datas para LocalDate
		LocalDate minLocalDate = minDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate maxLocalDate = maxDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		// Define a hora inicial (00:00) e hora final (23:59:59) do dia
		LocalDateTime minDateTime = minLocalDate.atTime(LocalTime.MIN);
		LocalDateTime maxDateTime = maxLocalDate.atTime(LocalTime.MAX);

		// Converte as LocalDateTime para Date
		minDate = Date.from(minDateTime.atZone(ZoneId.systemDefault()).toInstant());
		maxDate = Date.from(maxDateTime.atZone(ZoneId.systemDefault()).toInstant());

		return repo.fullSearch(text, minDate, maxDate);
	}
}
