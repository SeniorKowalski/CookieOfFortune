package com.kowalski.prediction.services;

import com.kowalski.prediction.models.Prediction;
import com.kowalski.prediction.repositories.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PredictionService {

    private final PredictionRepository predictionRepository;

    @Autowired
    public PredictionService(PredictionRepository predictionRepository) {
        this.predictionRepository = predictionRepository;
    }

    public List<Prediction> findAll() {
        return predictionRepository.findAll();
    }

    public Prediction findOne(int id) {
        Optional<Prediction> foundPrediction = predictionRepository.findById(id);
        return foundPrediction.orElse(null);
    }

    public Prediction findOneRandomPrediction() {
        Prediction foundPrediction = predictionRepository.findRandomPrediction();
        return foundPrediction;
    }

    @Transactional
    public void save(Prediction prediction) {
        predictionRepository.save(prediction);
    }

    @Transactional
    public void update(int id, Prediction updatedPrediction) {
        updatedPrediction.setId(id);
        predictionRepository.save(updatedPrediction);
    }

    @Transactional
    public void delete(int id) {
        predictionRepository.deleteById(id);
    }
}