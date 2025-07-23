package br.com.TrabalhoEngSoftware.chatbot.dto;

import java.util.Set;

import br.com.TrabalhoEngSoftwareFramework.framework.dto.UserAnswerDTO;

public class MultipleAnswersUserAnswerDTO extends UserAnswerDTO {
  
  private Set<Integer> optionsChosen;

  public MultipleAnswersUserAnswerDTO() {
    super();
  }

  public MultipleAnswersUserAnswerDTO(Long flashcardId, String flashcardType, Set<Integer> optionsChosen) {
    super(flashcardId, flashcardType);
    this.optionsChosen = optionsChosen;
  }

  public Set<Integer> getOptionsChosen() {
    return optionsChosen;
  }

  public void setOptionsChosen(Set<Integer> optionsChosen) {
    this.optionsChosen = optionsChosen;
  }
}
