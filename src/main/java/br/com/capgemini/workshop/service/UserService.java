package br.com.capgemini.workshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.capgemini.workshop.domain.User;
import br.com.capgemini.workshop.dto.UserDTO;
import br.com.capgemini.workshop.repository.UserRepository;
import br.com.capgemini.workshop.service.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public User findById(String id) {
		Optional<User> user = userRepository.findById(id);
		
		return user.orElseThrow(() -> new ObjectNotFoundException("Objeto Não Encontrado"));
	}
	
	public User insert(User user) {
		return userRepository.insert(user);
	}
	
	public void delete(String id) {
		findById(id);
		userRepository.deleteById(id);
	}
	
	public User update(User user) {
		User newUser = findById(user.getId());
		
		updateData(newUser, user);
		
		return userRepository.save(newUser);
	}
	
	private void updateData(User newUser, User user) {
		newUser.setName(user.getName());
		newUser.setEmail(user.getEmail());
	}
	
	public User fromDTO(UserDTO userDTO) {
		return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail());
	}
}