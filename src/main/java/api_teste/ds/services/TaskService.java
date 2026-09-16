package api_teste.ds.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api_teste.ds.models.Task;
import api_teste.ds.models.User;
import api_teste.ds.repositories.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {

        Optional<Task> task = this.taskRepository.findById(id);

        return task.orElseThrow(() -> new RuntimeException(
            "Tarefa não encontrada! Id: " + id +
            ", Tipo: " + Task.class.getName()
        ));
    }

    public List<Task> findByUserId(Long userId) {

        this.userService.findById(userId);

        return this.taskRepository.findByUserId(userId);
    }

    @Transactional
    public Task create(Task obj) {

        User user = this.userService.findById(obj.getUser().getId());

        obj.setId(null);

        obj.setUser(user);

        obj = this.taskRepository.save(obj);

        return obj;
    }

    @Transactional
    public Task update(Task obj) {

        Task newObj = findById(obj.getId());

        newObj.setDescription(obj.getDescription());

        return this.taskRepository.save(newObj);
    }
}