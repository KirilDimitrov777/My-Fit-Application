package com.myfit.service;

import com.myfit.entity.Client;
import com.myfit.entity.ClientQuestionnaire;
import com.myfit.repository.QuestionnaireRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;

    public QuestionnaireService(QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }

    public ClientQuestionnaire saveQuestionnaire(Client client, ClientQuestionnaire q) {
        q.setClient(client);
        return questionnaireRepository.save(q);
    }
}
