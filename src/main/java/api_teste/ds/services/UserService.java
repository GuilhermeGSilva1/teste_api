
// Declara o pacote onde a classe está localizada
package api_teste.ds.services;

// Importa a classe Optional para trabalhar com valores que podem ou não existir
import java.util.Optional;

// Permite que o Spring faça a injeção automática de dependências
import org.springframework.beans.factory.annotation.Autowired;

// Permite controlar as transações realizadas no banco de dados
import org.springframework.transaction.annotation.Transactional;

// Importa a anotação que identifica a classe como um serviço do Spring
import org.springframework.stereotype.Service;

// Importa a classe Task, que representa a entidade de tarefas
import api_teste.ds.models.Task;
import api_teste.ds.models.User;
// Importa o repositório responsável pelas operações da entidade Task
import api_teste.ds.repositories.TaskRepository;

// Importa o repositório responsável pelas operações da entidade User
import api_teste.ds.repositories.UserRepository;

// Indica ao Spring que esta classe contém regras de negócio e funciona como um serviço
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private TaskRepository taskRepository;

    public User findById(Long Id){
        Optional<User> user = this.userRepository.findById(Id);

        return user.orElseThrow(()-> new RuntimeException(  "Usuario não encontrado!" + Id + ", Tipo:" + User.class.getName()
    ));
    }

    @Transactional 
    public User create(User obj){
        obj.setId(null);

        obj = this.userRepository.save(obj);

        this.taskRepository.save(obj.getClass());

        return obj;
    }

    @Transactional 
    public User update(User obj){
        User newObj = findById(obj.getId());

        newObj.setPassword(obj.getPassword());

        return this.userRepository.save(newObj);

    }

    public void delete (Long Id){

        findById(Id);

        try{
            this.userRepository.deleteById(Id);

        }catch (Exception e){
            throw new RuntimeException("Não é póssivel exibir pois há entidades relacionadas");
        }
    }


}
