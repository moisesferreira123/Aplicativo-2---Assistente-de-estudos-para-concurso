package br.com.TrabalhoEngSoftware.chatbot.handler;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.com.TrabalhoEngSoftware.chatbot.entity.MultipleChoiceQuestionEntity;
import br.com.TrabalhoEngSoftwareFramework.framework.handler.FlashcardTypeSearchHandler;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

@Component
public class MultipleChoiceSearchHandler implements FlashcardTypeSearchHandler<MultipleChoiceQuestionEntity> {
  
  @Override
  public String supportsType() {
    return "MULTIPLE_CHOICE_QUESTION";
  }

  @Override
  public Specification<MultipleChoiceQuestionEntity> getWordSearchSpecification(String word) {
    return (root, query, criteriaBuilder) -> {
      Predicate wordPredicate = criteriaBuilder.conjunction();
      if(word != null && !word.isEmpty()) {
		  	wordPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("question")), "%" + word.toLowerCase() + "%");
        Join<MultipleChoiceQuestionEntity, String> answerOptionsJoin = root.join("answerOptions");
        query.distinct(true);
        wordPredicate = criteriaBuilder.or(wordPredicate, criteriaBuilder.like(answerOptionsJoin.get("answerOptions"), "%" + word.toLowerCase() + "%"));
		  }
      return criteriaBuilder.and(wordPredicate);
    };
  }
}
