package br.com.capgemini.workshop.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.capgemini.workshop.domain.Post;
import br.com.capgemini.workshop.domain.User;
import br.com.capgemini.workshop.dto.UserDTO;
import br.com.capgemini.workshop.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/users")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value="Busca Tudo")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findAll(){
		List<User> list = userService.findAll();
		List<UserDTO> listDTO = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@ApiOperation(value="Busca por Id")
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<UserDTO> findById(@PathVariable String id){
		User user = userService.findById(id);
		
		return ResponseEntity.ok().body(new UserDTO(user));
	}
	
	@ApiOperation(value="Insere um Usuario")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody UserDTO userDTO){
		User user = userService.fromDTO(userDTO);
		user = userService.insert(user);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value="Deleta o Usuario")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Não é Possivel Excluir um Usuario que Possui Posts e Comentarios"),
			@ApiResponse(code = 404, message = "Código Inexistente")
	})
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id){
		userService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value="Atualiza o Usuario")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody UserDTO userDTO, @PathVariable String id){
		User user = userService.fromDTO(userDTO);
		
		user.setId(id);
		user = userService.update(user);
		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value="Busca pelos Posts")
	@RequestMapping(value="/{id}/posts", method=RequestMethod.GET)
	public ResponseEntity<List<Post>> findPosts(@PathVariable String id){
		User user = userService.findById(id);
		
		return ResponseEntity.ok().body(user.getPosts());
	}
}