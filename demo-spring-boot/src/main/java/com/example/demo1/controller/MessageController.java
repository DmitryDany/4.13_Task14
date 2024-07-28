package com.example.demo1.controller;

import com.example.demo1.dto.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {
    private List<Message> messages = new ArrayList<>(Arrays.asList(
            new Message(1, "Задание 1", "Выполнить задание 14", LocalDateTime.of(2024, 07,28,10,47)),
            new Message(2, "Задание 2", "Перечитать ещё раз теорию по заданию 14", LocalDateTime.of(2024, 07,28,11,00)),
            new Message(3, "Задание 3", "С учётом теории, протестировать задание 14", LocalDateTime.of(2024, 07,28,11,30)),
            new Message(4, "Задание 4", "Отправить на проверку задание 14", LocalDateTime.of(2024, 07,28,12,00))
    ));

    @GetMapping("/message")
    public Iterable<Message> getMessage(){
        return messages;
    }

    @GetMapping("/message/{id}")
    public Optional<Message> findMessageById(@PathVariable int id){
        return messages.stream().filter(p -> p.getId() == id).findFirst();
    }

    @PostMapping("/message")
    public Message addMessage (@RequestBody Message message){
        messages.add(message);
        return message;
    }

    @PutMapping("/message/{id}")
    public ResponseEntity<Message> updateMessage (@PathVariable int id, @RequestBody Message message){
        int index = -1;
        for (Message p: messages){
            if(p.getId() == id){
                index = messages.indexOf(p);
                messages.set(index, message);
            }
        }
        return index == -1
                ? new ResponseEntity<>(addMessage(message), HttpStatus.CREATED)
                : new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/message/{id}")
    public void deleteMessage(@PathVariable int id){
        messages.removeIf(p -> p.getId() == id);
    }
}

