package com.project.WebApp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private static List<Todo> todos =new  ArrayList<Todo>();
    private static int todosCount=0;

    static {
        todos.add(new Todo(todosCount++, "in28minutes","Learn AWS",
                LocalDate.now().plusYears(1), false ));
        todos.add(new Todo(todosCount++, "in28minutes","Learn DevOps",
                LocalDate.now().plusYears(2), false ));
        todos.add(new Todo(todosCount++, "in28minutes","Learn Full Stack Development",
                LocalDate.now().plusYears(3), false ));
        todos.add(new Todo(todosCount++,"utkarsh","learn CSS1",LocalDate.now().plusYears(2),false));
    }
    public List<Todo> findByUsername(String username){
        Predicate<Todo> predicate=todo -> todo.getUsername().equalsIgnoreCase(username);
        return todos.stream().filter(predicate).toList();
    }
    public void addTodo(String username,String description,LocalDate targetDate,boolean isDone){
        Todo newTodo= new Todo(todosCount++,username,description,targetDate,false);
        todos.add(newTodo);
    }
    public void deleteTodo(int id){
        Predicate<Todo> predicate=todo -> todo.getId()==id;
        todos.removeIf(predicate);
    }
    public void updateTodo(@Valid Todo todo){
        deleteTodo(todo.getId());
        todos.add(todo);
    }

    public Todo findById(int id) {
        Predicate<?super Todo> predicate=todo -> todo.getId()==id;
        Todo todo=todos.stream().filter(predicate).findFirst().get();
        return todo;
    }
}
