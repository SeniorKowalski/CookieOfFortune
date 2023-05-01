package com.kowalski.prediction.repositories;

import com.kowalski.prediction.models.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Integer> {
    @Query(value = "SELECT * FROM predictions_db ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Prediction findRandomPrediction();
}