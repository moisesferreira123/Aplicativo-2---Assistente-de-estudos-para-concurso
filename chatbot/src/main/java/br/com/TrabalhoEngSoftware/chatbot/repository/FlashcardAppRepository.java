package br.com.TrabalhoEngSoftware.chatbot.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.TrabalhoEngSoftwareFramework.framework.entity.FlashcardEntity;
import br.com.TrabalhoEngSoftwareFramework.framework.repository.FlashcardRepository;

public interface FlashcardAppRepository extends FlashcardRepository {
  
  @Query("""
    SELECT f FROM FlashcardEntity f
    WHERE f.deckEntity.id = :deckId
      AND f.deckEntity.userEntity.id = :userId
      AND (
        (TYPE(f) = StandardFlashcardEntity AND TREAT(f AS StandardFlashcardEntity).nextReview < :tomorrow)
        OR
        (TYPE(f) = TrueFalseQuestionEntity AND TREAT(f AS TrueFalseQuestionEntity).nextReview < :tomorrow)
        OR
        (TYPE(f) = RightWrongQuestionEntity AND TREAT(f AS RightWrongQuestionEntity).nextReview < :tomorrow)
        OR
        (TYPE(f) = MultipleChoiceQuestionEntity AND TREAT(f AS MultipleChoiceQuestionEntity).nextReview < :tomorrow)
        OR
        (TYPE(f) = MultipleAnswersQuestionEntity AND TREAT(f AS MultipleAnswersQuestionEntity).nextReview < :tomorrow)
        OR
        (TYPE(f) = DiscursiveQuestionEntity AND TREAT(f AS DiscursiveQuestionEntity).nextReview < :tomorrow)
      )
    ORDER BY CASE
      WHEN TYPE(f) = StandardFlashcardEntity THEN TREAT(f AS StandardFlashcardEntity).nextReview
      WHEN TYPE(f) = TrueFalseQuestionEntity THEN TREAT(f AS TrueFalseQuestionEntity).nextReview
      WHEN TYPE(f) = RightWrongQuestionEntity THEN TREAT(f AS RightWrongQuestionEntity).nextReviewv
      WHEN TYPE(f) = MultipleChoiceQuestionEntity THEN TREAT(f AS MultipleChoiceQuestionEntity).nextReview
      WHEN TYPE(f) = MultipleAnswersQuestionEntity THEN TREAT(f AS MultipleAnswersQuestionEntity).nextReview
      WHEN TYPE(f) = DiscursiveQuestionEntity THEN TREAT(f AS DiscursiveQuestionEntity).nextReview
      ELSE NULL
    END
  """)  
  Page<FlashcardEntity> findNextDueFlashcardByDeckId(@Param("deckId") Long deckId, @Param("userId") Long userId, @Param("tomorrow") LocalDateTime tomorrow, Pageable pageable);

  @Query("SELECT COUNT(f) FROM FlashcardEntity f WHERE f.deckEntity.id = :deckId AND f.deckEntity.userEntity.id = :userId AND f.lastReviewedAt IS NULL")
  long countNewFlashcards(@Param("deckId") Long deckId, @Param("userId") Long userId);

  @Query("""
    SELECT COUNT(f) FROM FlashcardEntity f 
    WHERE f.deckEntity.id = :deckId  
    AND f.deckEntity.userEntity.id = :userId 
    AND f.lastReviewedAt BETWEEN :startOfDay AND :endOfDay 
    AND (
        (TYPE(f) = StandardFlashcardEntity AND TREAT(f AS StandardFlashcardEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = TrueFalseQuestionEntity AND TREAT(f AS TrueFalseQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = RightWrongQuestionEntity AND TREAT(f AS RightWrongQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = MultipleChoiceQuestionEntity AND TREAT(f AS MultipleChoiceQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = MultipleAnswersQuestionEntity AND TREAT(f AS MultipleAnswersQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = DiscursiveQuestionEntity AND TREAT(f AS DiscursiveQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
    )
  """)
  long countLearningFlashcards(@Param("deckId") Long deckId, @Param("userId") Long userId, @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

  @Query("""
    SELECT COUNT(f) FROM FlashcardEntity f 
    WHERE f.deckEntity.id = :deckId  
    AND f.deckEntity.userEntity.id = :userId 
    AND f.lastReviewedAt < :startOfToday
    AND (
        (TYPE(f) = StandardFlashcardEntity AND TREAT(f AS StandardFlashcardEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = TrueFalseQuestionEntity AND TREAT(f AS TrueFalseQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = RightWrongQuestionEntity AND TREAT(f AS RightWrongQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = MultipleChoiceQuestionEntity AND TREAT(f AS MultipleChoiceQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = MultipleAnswersQuestionEntity AND TREAT(f AS MultipleAnswersQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = DiscursiveQuestionEntity AND TREAT(f AS DiscursiveQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
    )
  """)
  long countReviewFlashcards(@Param("deckId") Long deckId, @Param("userId") Long userId, @Param("startOfToday") LocalDateTime startOfToday, @Param("endOfToday") LocalDateTime endOfToday);

  @Query("""
  SELECT f FROM FlashcardEntity f
  WHERE f.deckEntity.id = :deckId
    AND f.deckEntity.userEntity.id = :userId
    AND (
        (TYPE(f) = StandardFlashcardEntity AND TREAT(f AS StandardFlashcardEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = TrueFalseQuestionEntity AND TREAT(f AS TrueFalseQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = RightWrongQuestionEntity AND TREAT(f AS RightWrongQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = MultipleChoiceQuestionEntity AND TREAT(f AS MultipleChoiceQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = MultipleAnswersQuestionEntity AND TREAT(f AS MultipleAnswersQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = DiscursiveQuestionEntity AND TREAT(f AS DiscursiveQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
    )
  ORDER BY 
    CASE
      WHEN TYPE(f) = StandardFlashcardEntity THEN TREAT(f AS StandardFlashcardEntity).nextReview
      WHEN TYPE(f) = TrueFalseQuestionEntity THEN TREAT(f AS TrueFalseQuestionEntity).nextReview
      WHEN TYPE(f) = RightWrongQuestionEntity THEN TREAT(f AS RightWrongQuestionEntity).nextReviewv
      WHEN TYPE(f) = MultipleChoiceQuestionEntity THEN TREAT(f AS MultipleChoiceQuestionEntity).nextReview
      WHEN TYPE(f) = MultipleAnswersQuestionEntity THEN TREAT(f AS MultipleAnswersQuestionEntity).nextReview
      WHEN TYPE(f) = DiscursiveQuestionEntity THEN TREAT(f AS DiscursiveQuestionEntity).nextReview
      ELSE NULL
    END ASC
  """)
  Page<FlashcardEntity> findNextDueFlashcard(@Param("userId") Long userId, @Param("tomorrow") LocalDateTime tomorrow, Pageable pageable);

  @Query("SELECT COUNT(f) FROM FlashcardEntity f WHERE f.deckEntity.userEntity.id = :userId AND f.lastReviewedAt IS NULL")
  long countAllNewFlashcards(@Param("userId") Long userId);

  @Query("""
    SELECT COUNT(f) FROM FlashcardEntity f 
    WHERE f.deckEntity.userEntity.id = :userId 
    AND f.lastReviewedAt BETWEEN :startOfDay AND :endOfDay 
    AND (
        (TYPE(f) = StandardFlashcardEntity AND TREAT(f AS StandardFlashcardEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = TrueFalseQuestionEntity AND TREAT(f AS TrueFalseQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = RightWrongQuestionEntity AND TREAT(f AS RightWrongQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = MultipleChoiceQuestionEntity AND TREAT(f AS MultipleChoiceQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = MultipleAnswersQuestionEntity AND TREAT(f AS MultipleAnswersQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = DiscursiveQuestionEntity AND TREAT(f AS DiscursiveQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
    )
  """)
  long countAllLearningFlashcards(@Param("userId") Long userId, @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

  @Query("""
    SELECT COUNT(f) FROM FlashcardEntity f 
    WHERE f.deckEntity.userEntity.id = :userId 
    AND f.lastReviewedAt < :startOfToday
    AND (
        (TYPE(f) = StandardFlashcardEntity AND TREAT(f AS StandardFlashcardEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = TrueFalseQuestionEntity AND TREAT(f AS TrueFalseQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = RightWrongQuestionEntity AND TREAT(f AS RightWrongQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = MultipleChoiceQuestionEntity AND TREAT(f AS MultipleChoiceQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = MultipleAnswersQuestionEntity AND TREAT(f AS MultipleAnswersQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
        OR
        (TYPE(f) = DiscursiveQuestionEntity AND TREAT(f AS DiscursiveQuestionEntity).nextReview BETWEEN :startOfDay AND :endOfDay)
    )
  """)
  long countAllReviewFlashcards(@Param("userId") Long userId, @Param("startOfToday") LocalDateTime startOfToday, @Param("endOfToday") LocalDateTime endOfToday);
}
