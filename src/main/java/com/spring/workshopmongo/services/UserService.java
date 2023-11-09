package com.spring.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.workshopmongo.domain.User;
import com.spring.workshopmongo.dto.UserDTO;
import com.spring.workshopmongo.repository.UserRepository;
import com.spring.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public User insert(User user) {
		return repo.insert(user);
	}

	public User update(User changes) {
		User user = findById(changes.getId());
		updateData(user, changes);
		return repo.save(user);
	}

	private void updateData(User user, User changes) {
		user.setName(changes.getName());
		user.setEmail(changes.getEmail());
	}

	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}

	public User fromDTO(UserDTO dto) {
		return new User(dto.getId(), dto.getName(), dto.getEmail());
	}
}
