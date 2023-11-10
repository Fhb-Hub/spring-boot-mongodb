package com.spring.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.spring.workshopmongo.domain.Post;
import com.spring.workshopmongo.domain.User;
import com.spring.workshopmongo.dto.AuthorDTO;
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
		System.out.println("Iniciando a inicialização dos dados...");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		userRepository.deleteAll();
		postRepository.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		userRepository.saveAll(Arrays.asList(maria, alex, bob));

		List<Post> posts = Arrays.asList(
				new Post(null, sdf.parse("21/11/2023"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!",
						new AuthorDTO(maria)),
				new Post(null, sdf.parse("23/11/2023"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria)));

		postRepository.saveAll(posts);

		maria.getPosts().addAll(posts);
		userRepository.save(maria);

		System.out.println("Dados iniciais foram inseridos com sucesso.");
	}

}
