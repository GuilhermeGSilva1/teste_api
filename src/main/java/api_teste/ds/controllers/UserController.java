// Declaração do pacote onde esta classe controller está localizada
package api_teste.ds.controllers;

// Import de classe utilitária para manipular e representar URIs/URLs
import java.net.URI;

// Anotações e classes do Spring Framework para injeção de dependência e respostas HTTP
import org.springframework.beans.factory.annotation.Autowired; // Injeção automática de dependências pelo Spring
import org.springframework.http.ResponseEntity; // Classe para construir e customizar respostas HTTP completas (código de status, cabeçalhos e corpo)

// Import para ativar a validação de objetos/parâmetros anotados
import org.springframework.validation.annotation.Validated;

// Mapeamentos de requisições HTTP (Verbos REST)
import org.springframework.web.bind.annotation.DeleteMapping; // Mapeia requisições HTTP DELETE
import org.springframework.web.bind.annotation.GetMapping;    // Mapeia requisições HTTP GET
import org.springframework.web.bind.annotation.PathVariable; // Extrai variáveis da URL (ex: /users/{id})
import org.springframework.web.bind.annotation.PostMapping;   // Mapeia requisições HTTP POST
import org.springframework.web.bind.annotation.PutMapping;    // Mapeia requisições HTTP PUT
import org.springframework.web.bind.annotation.RequestBody;   // Converte o corpo da requisição (JSON) em um objeto Java
import org.springframework.web.bind.annotation.RequestMapping;// Define a rota/caminho base do controller
import org.springframework.web.bind.annotation.RestController; // Marca a classe como um controller REST (retorna dados diretamente no corpo da resposta, em JSON/XML)

// Utilitário para construir URIs dinamicamente a partir do contexto da requisição atual (útil para o cabeçalho Location ao criar recursos com POST)
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import api_teste.ds.models.User;
import api_teste.ds.models.User.CreateUser;
import api_teste.ds.models.User.UpdateUser;
import api_teste.ds.services.UserService;
/**
 * Classe responsável por expor os endpoints REST relacionados aos usuários.
 * Nota: Geralmente deve ser anotada com @RestController e @RequestMapping("/caminho").
 */
import org.springframework.web.bind.annotation.RequestParam;

@RestController //define a classe controlador REST que retorna respostas em JSON
@RequestMapping ("/user")//define que todas as rotas desta classe terão como prefixo o caminho "/user"
@Validated //ativa a verificação de validações nos parametros recebidos no controller

public class UserController {
    
    @Autowired 
    private UserService userService;

    @GetMapping("/{id}") //mapeia requisições HTTP GET na rota "/user/{id}"
    public ResponseEntity<User> findById(@PathVariable long Id){ //metodo para buscar usuario por id capturado da url
        User obj=this.userService.findById(Id); //invoca a busca do usuario atraves do id recebido
        return ResponseEntity.ok().body(obj); //retorna codig HTTP 200(ok) com o objeto User no corpo de resposta
    } //fim do metodo findById
    @PostMapping
    public ResponseEntity<void> create(@Validated (CreateUser.class) @RequestBody User obj){
        this.userService.create(obj);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(url).build();
    

}