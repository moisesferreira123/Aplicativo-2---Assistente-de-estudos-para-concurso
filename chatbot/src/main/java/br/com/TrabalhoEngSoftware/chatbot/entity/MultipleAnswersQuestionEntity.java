package br.com.TrabalhoEngSoftware.chatbot.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import br.com.TrabalhoEngSoftwareFramework.framework.entity.FlashcardEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

@Entity
@DiscriminatorValue("MULTIPLE_ANSWERS_QUESTION")
public class MultipleAnswersQuestionEntity extends FlashcardEntity {
  @Column
	private String question;

  @ElementCollection
  @CollectionTable(name = "multiple_answer_options", joinColumns = @JoinColumn(name = "flashcard_id"))
  @Column
  private List<String> answerOptions;

  @ElementCollection
  @CollectionTable(name = "correct-answer-indices", joinColumns = @JoinColumn(name = "flashcard_id"))
  @Column
	private Set<Integer> correctAnswerIndices;

  @Column(nullable = false)
	private LocalDateTime nextReview;
	
	// Quantidade de vezes que o card foi revisado com sucesso (sequencialmente).
	// Inicia em zero.
	@Column(nullable = false)
	private int repetition;
	
	// Grau de facilidade do flashcard.
	// Começa com 2.5 e muda conforme o desempenho do usuário
	@Column(nullable = false)
	private double easeFactor;
	
	//Quantidade de dias até a próxima revisão.
	//Começa em 1 e cresce conforme o algoritmo.
	// interval *= easeFactor;
	@Column(nullable = false)
	private int interval;

  public MultipleAnswersQuestionEntity() {
    super();
    super.setFlashcardType("MULTIPLE_ANSWERS_QUESTION");
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public List<String> getAnswerOptions() {
    return answerOptions;
  }

  public void setAnswerOptions(List<String> answerOptions) {
    this.answerOptions = answerOptions;
  }

  public Set<Integer> getCorrectAnswerIndices() {
    return correctAnswerIndices;
  }

  public void setCorrectAnswerIndices(Set<Integer> correctAnswerIndices) {
    this.correctAnswerIndices = correctAnswerIndices;
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
