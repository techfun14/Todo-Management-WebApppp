package com.project.WebApp.todo;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name")
public class TodoControllerJpa  {
    public TodoControllerJpa(TodoRepository todoRepository1) {
        super();

        this.todoRepository=todoRepository1;
    }
    private TodoRepository todoRepository;


    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap model) {
        String username= getLoggedInUsername(model);

        List<Todo> todos = todoRepository.findByUsername(username);
        model.addAttribute("todos", todos);

        return "listTodos";
    }

    private static String getLoggedInUsername(ModelMap model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @RequestMapping(value="add-todo",method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap map) {
        String username= getLoggedInUsername(map);
        Todo todo=new Todo(0,username," ", LocalDate.now().plusYears(2),false);
        map.put("todo",todo);
        return "todo";
    }
    @RequestMapping(value="add-todo",method = RequestMethod.POST)
    public String addNewTodoPage(ModelMap map, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors()){
            return "todo";
        }
        String username= getLoggedInUsername(map);
        todo.setUsername(username);
        todoRepository.save(todo);
//        todoService.addTodo(getLoggedInUsername(map),todo.getDescription(), todo.getTargetDate(), todo.isDone());
        return "redirect:list-todos";
    }
    @RequestMapping("delete-todo")
    public String deleteATodos(@RequestParam int id) {
        todoRepository.deleteById(id);
//        todoService.deleteTodo(id--);
        return "redirect:list-todos";
    }
    @RequestMapping(value = "update-todo",method = RequestMethod.GET)
    public String showUpdateTodosPage(@RequestParam int id,ModelMap map) {
        Todo todo=todoRepository.findById(id).get();
        map.addAttribute("todo",todo);
        return "todo";
    }
    @RequestMapping(value = "update-todo",method = RequestMethod.POST)
    public String updateTodoDetails(ModelMap map,@Valid Todo todo,BindingResult result){
        if (result.hasErrors()){
            return "todo";
        }
        String username= getLoggedInUsername(map);
        todo.setUsername(username);
        todoRepository.save(todo);
//        todoService.updateTodo(todo);
        return "redirect:list-todos";
    }

}
