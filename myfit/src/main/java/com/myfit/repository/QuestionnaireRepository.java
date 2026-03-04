package com.myfit.repository;

import com.myfit.entity.ClientQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnaireRepository extends JpaRepository<ClientQuestionnaire, Long> {}
