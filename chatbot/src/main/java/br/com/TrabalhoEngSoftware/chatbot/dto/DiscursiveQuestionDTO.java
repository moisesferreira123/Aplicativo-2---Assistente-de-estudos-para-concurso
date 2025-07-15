package br.com.TrabalhoEngSoftware.chatbot.dto;

import java.time.LocalDateTime;

import br.com.TrabalhoEngSoftwareFramework.framework.dto.FlashcardDTO;

public class DiscursiveQuestionDTO extends FlashcardDTO {

	private String question;
	private String exampleCorrectAnswer;
  private LocalDateTime nextReview;
	private int repetition;
	private double easeFactor;
	private int interval;

  public DiscursiveQuestionDTO() {
    super();
		super.setFlashcardType("DISCURSIVE_QUESTION");
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getExampleCorrectAnswer() {
    return exampleCorrectAnswer;
  }

  public void setExampleCorrectAnswer(String exampleCorrectAnswer) {
    this.exampleCorrectAnswer = exampleCorrectAnswer;
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
