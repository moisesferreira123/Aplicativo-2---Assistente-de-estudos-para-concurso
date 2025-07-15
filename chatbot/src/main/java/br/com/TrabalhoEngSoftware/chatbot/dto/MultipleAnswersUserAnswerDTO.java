package br.com.TrabalhoEngSoftware.chatbot.dto;

import java.util.List;

import br.com.TrabalhoEngSoftwareFramework.framework.dto.UserAnswerDTO;

public class MultipleAnswersUserAnswerDTO extends UserAnswerDTO {
  
  private List<Integer> optionsChosen;

  public MultipleAnswersUserAnswerDTO() {
    super();
  }

  public MultipleAnswersUserAnswerDTO(Long flashcardId, String flashcardType, List<Integer> optionsChosen) {
    super(flashcardId, flashcardType);
    this.optionsChosen = optionsChosen;
  }

  public List<Integer> getOptionsChosen() {
    return optionsChosen;
  }

  public void setOptionsChosen(List<Integer> optionsChosen) {
    this.optionsChosen = optionsChosen;
  }
}
