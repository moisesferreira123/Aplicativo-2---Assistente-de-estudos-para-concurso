package br.com.TrabalhoEngSoftware.chatbot.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.TrabalhoEngSoftwareFramework.framework.dto.FlashcardDTO;

public class TrueFalseQuestionDTO extends FlashcardDTO {

	private String question;
  private List<String> statements;
  private List<Boolean> trueFalseAnswers;
  private LocalDateTime nextReview;
	private int repetition;
	private double easeFactor;
	private int interval;

  public TrueFalseQuestionDTO() {
		super();
		super.setFlashcardType("TRUE_FALSE_QUESTION");
	}

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public List<String> getStatements() {
    return statements;
  }

  public void setStatements(List<String> statements) {
    this.statements = statements;
  }

  public List<Boolean> getTrueFalseAnswers() {
    return trueFalseAnswers;
  }

  public void setTrueFalseAnswers(List<Boolean> trueFalseAnswers) {
    this.trueFalseAnswers = trueFalseAnswers;
  }

  public LocalDateTime getNextReview() {
    return nextReview;
  }

  public void setNextReview(LocalDateTime nextReview) {
    this.nextReview = nextReview;
  }

  public int getRepetition() {
    return repetition;
  }

  public void setRepetition(int repetition) {
    this.repetition = repetition;
  }

  public double getEaseFactor() {
    return easeFactor;
  }

  public void setEaseFactor(double easeFactor) {
    this.easeFactor = easeFactor;
  }

  public int getInterval() {
    return interval;
  }

  public void setInterval(int interval) {
    this.interval = interval;
  }
}
