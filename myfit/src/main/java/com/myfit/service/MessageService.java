package com.myfit.service;

import com.myfit.entity.Client;
import com.myfit.entity.Message;
import com.myfit.entity.Trainer;
import com.myfit.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository repo;

    public MessageService(MessageRepository repo) {
        this.repo = repo;
    }

    /**
     * Връща чат историята между конкретен клиент и треньор,
     * подредена по време.
     */
    public List<Message> chat(Client client, Trainer trainer) {
        return repo.findByClientAndTrainerOrderByTimestampAsc(client, trainer);
    }

    /**
     * Записва съобщение в базата – ползва се и от WebSocket контролера.
     */
    public void send(Message msg) {
        repo.save(msg);
    }
}
