package com.myfit.repository;

import com.myfit.entity.Message;
import com.myfit.entity.Client;
import com.myfit.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByClientAndTrainerOrderByTimestampAsc(Client client, Trainer trainer);
}
