package br.com.TrabalhoEngSoftware.chatbot.handler;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.com.TrabalhoEngSoftware.chatbot.entity.RightWrongQuestionEntity;
import br.com.TrabalhoEngSoftwareFramework.framework.handler.FlashcardTypeSearchHandler;
import jakarta.persistence.criteria.Predicate;

@Component
public class RightWrongSearchHandler implements FlashcardTypeSearchHandler<RightWrongQuestionEntity> {
  
  @Override
  public String supportsType() {
    return "RIGHT_WRONG_QUESTION";
  }

  @Override
  public Specification<RightWrongQuestionEntity> getWordSearchSpecification(String word) {
    return (root, query, criteriaBuilder) -> {
      Predicate wordPredicate = criteriaBuilder.conjunction();
      if(word != null && !word.isEmpty()) {
		  	wordPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("question")), "%" + word.toLowerCase() + "%");
		  }
      return criteriaBuilder.and(wordPredicate);
    };
  }
}
