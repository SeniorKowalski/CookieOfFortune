package com.kowalski.prediction.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "predictions_db")
public class Prediction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "predictionText should not be empty!")
    @Size(min = 2, max = 100, message = "predictionText should be between 2 and 100 characters")
    @Column(name = "prediction")
    private String predictionText;

    public Prediction() {
    }

    public Prediction(int id, String predictionText) {
        this.id = id;
        this.predictionText = predictionText;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPredictionText() {
        return predictionText;
    }

    public void setPredictionText(String predictionText) {
        this.predictionText = predictionText;
    }

}