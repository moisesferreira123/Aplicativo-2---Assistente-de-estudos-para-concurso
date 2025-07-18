package br.com.TrabalhoEngSoftware.chatbot.handler;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.com.TrabalhoEngSoftware.chatbot.entity.TrueFalseQuestionEntity;
import br.com.TrabalhoEngSoftwareFramework.framework.handler.FlashcardTypeSearchHandler;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

@Component
public class TrueFalseSearchHandler implements FlashcardTypeSearchHandler<TrueFalseQuestionEntity> {
  
  @Override
  public String supportsType() {
    return "TRUE_FALSE_QUESTION";
  }

  @Override
  public Specification<TrueFalseQuestionEntity> getWordSearchSpecification(String word) {
    return (root, query, criteriaBuilder) -> {
      Predicate wordPredicate = criteriaBuilder.conjunction();
      if(word != null && !word.isEmpty()) {
		  	wordPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("question")), "%" + word.toLowerCase() + "%");
        Join<TrueFalseQuestionEntity, String> statementsJoin = root.join("statements");
        query.distinct(true);
        wordPredicate = criteriaBuilder.or(wordPredicate, criteriaBuilder.like(criteriaBuilder.lower(statementsJoin.get("statements")), "%" + word.toLowerCase() + "%"));
		  }
      return criteriaBuilder.and(wordPredicate);
    };
  }
}
