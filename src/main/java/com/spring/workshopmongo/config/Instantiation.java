package com.spring.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.spring.workshopmongo.domain.Post;
import com.spring.workshopmongo.domain.User;
import com.spring.workshopmongo.dto.AuthorDTO;
import com.spring.workshopmongo.dto.CommentDTO;
import com.spring.workshopmongo.repository.PostRepository;
import com.spring.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Limpa todos os usuários e postagens existentes
		userRepository.deleteAll();
		postRepository.deleteAll();

		// Cria usuários
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		// Salva os usuários no repositório
		userRepository.saveAll(Arrays.asList(maria, alex, bob));

		// Cria postagens
		Post post1 = new Post(null, sdf.parse("21/11/2023"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!",
				new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("23/11/2023"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));

		// Cria comentários
		CommentDTO c1 = new CommentDTO("Tenha uma boa viagem!", sdf.parse("21/11/2023"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite", sdf.parse("22/11/2023"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("23/11/2023"), new AuthorDTO(alex));

		// Associa os comentários às postagens
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));

		// Salva as postagens no repositório
		postRepository.saveAll(Arrays.asList(post1, post2));

		// Associa as postagens ao usuário "Maria"
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}

}
