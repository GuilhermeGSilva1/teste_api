// Declaração do pacote onde esta classe controller está localizada
package api_teste.ds.controllers;

// Import de classe utilitária para manipular e representar URIs/URLs
import java.net.URI;

// Anotações e classes do Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

// Import para ativar a validação
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
// Mapeamentos de requisições HTTP
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Utilitário para construir URIs dinamicamente
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// Import das classes do projeto
import api_teste.ds.models.User;
import api_teste.ds.models.User.CreateUser;
import api_teste.ds.services.UserService;
import org.springframework.web.bind.annotation.PutMapping;


// Classe responsável pelos endpoints REST relacionados aos usuários
@RestController

// Define que todas as rotas desta classe terão o prefixo "/user"
@RequestMapping("/user")

// Ativa a validação dos parâmetros recebidos
@Validated
public class UserController {

    // Injeta automaticamente o UserService nesta classe
    @Autowired
    private UserService userService;

    // Mapeia requisições HTTP GET na rota "/user/{id}"
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable long id) {

        // Busca o usuário através do ID recebido pela URL
        User obj = this.userService.findById(id);

        // Retorna HTTP 200 (OK) com o usuário no corpo da resposta
        return ResponseEntity.ok().body(obj);
    } // Fim do Método FindyId

    // Mapeia requisições HTTP POST na rota "/user" (Criação de Novo Usuario)
    @PostMapping
    public ResponseEntity<Void> create(
            @Validated(CreateUser.class) @RequestBody User obj) {
            // Valida regra de CreateUser e dessarealiza e o corpo JSON

        // Envia o usuário para o service realizar o cadastro
        this.userService.create(obj);

        // Monta a URL do usuário que acabou de ser criado
        URI url = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();

        // Retorna O código HTTP 201 (Created) e a URL do novo usuário no cabeçalho Location
        return ResponseEntity.created(url).build();
    }

    @PutMapping("/{id")// Mapeia requisições HTTP Put na rota base "/user/{id}" (atualização de usuario)
    public ResponseEntity<Void> update(@Validated(UpdateUser.class)@RequestBody User obj, @PathVariable Long id){
        obj.setId(id);// Garente que o ID do objeto ao ser atualizado, Garantir ao ID informado na URL
        this.userService.update(obj); // Executa a atualização da Senha do User no banco de dados
        return ResponseEntity.noContent().build();// Retorna Código HTTP 204(No Content) indicaando Sucesso sem corpo de resposta
    }

    @DeleteMapping ("/{id}") // Mapeia requisições HTTP DELETE na rosa "/user/{id}"
    public ResponseEntity<Void>delete(@PathVariable Long id){
        this.userService.delete(id); // Invoca Método de deletação do Serviço
        return ResponseEntity.noContent().build(); // Retorna Código HTTP 204 (No Content) confirmando a Exclusão
    }


}