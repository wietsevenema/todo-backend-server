package todobackend;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import java.lang.management.ManagementFactory;
import java.util.Optional;

@Controller()
public class TodoController {

    private final TodoRepository todoRepository;

    TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Get()
    public HttpResponse findAll() {
        return HttpResponse.ok(todoRepository.findAll());
    }


    @Get("/{id:[0-9]+}")
    public HttpResponse findById(String id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isEmpty()) {
            return HttpResponse.notFound();
        }
        return HttpResponse.ok(todo);
    }

    @Delete(produces = MediaType.APPLICATION_JSON)
    public HttpResponse delete() {
        todoRepository.deleteAll();
        return HttpResponse.ok();
    }

    @Delete("/{id:[0-9]+}")
    public HttpResponse deleteById(String id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isEmpty()) {
            return HttpResponse.notFound();
        }
        todoRepository.delete(todo.get());
        return HttpResponse.ok();
    }

    @Patch("{id:[0-9]+}")
    public HttpResponse patchById(String id, @Body Todo updatedTodo) {
        Optional<Todo> byId = todoRepository.findById(id);
        if (byId.isEmpty()) {
            return HttpResponse.notFound();
        }
        Todo todo = byId.get();
        if (updatedTodo.getTitle() != null) {
            todo.setTitle(updatedTodo.getTitle());
        }
        if (updatedTodo.getCompleted() != null) {
            todo.setCompleted(updatedTodo.getCompleted());
        }
        if (updatedTodo.getSortOrder() != null) {
            todo.setSortOrder(updatedTodo.getSortOrder());
        }
        todoRepository.update(todo);
        return HttpResponse.ok(todo);
    }

    @Post()
    public HttpResponse<Todo> post(@Body Todo todo) {
        if (todo.getCompleted() == null) {
            todo.setCompleted(false);
        }
        if (todo.getSortOrder() == null) {
            todo.setSortOrder(0);
        }
        todoRepository.save(todo);
        return HttpResponse.created(todo);
    }
}
