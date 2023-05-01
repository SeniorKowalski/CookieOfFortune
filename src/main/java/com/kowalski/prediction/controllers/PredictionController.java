package com.kowalski.prediction.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kowalski.prediction.models.Prediction;
import com.kowalski.prediction.services.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/fortuneCookies")
public class PredictionController {

    private final PredictionService predictionService;

    @Autowired
    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @GetMapping()
    public String home(Model model) {
        Prediction prediction = predictionService.findOneRandomPrediction();
        model.addAttribute("prediction", prediction.getPredictionText());
        return "fortuneCookies/home";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("allPredictions", predictionService.findAll());
        return "fortuneCookies/index";
    }

    @GetMapping(value = "/random-prediction", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> randomPrediction() {
        Prediction prediction = predictionService.findOneRandomPrediction();
        ObjectMapper mapper = new ObjectMapper();
        String predictionJson;
        try {
            predictionJson = mapper.writeValueAsString(prediction.getPredictionText());
            return ResponseEntity.ok(predictionJson);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при преобразовании предсказания в JSON");
        }
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("prediction", predictionService.findOne(id));
        return "fortuneCookies/show";
    }

    @GetMapping("/new")
    public String newPrediction(@ModelAttribute("prediction") Prediction prediction) {
        return "fortuneCookies/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("prediction") @Valid Prediction prediction,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fortuneCookies/new";
        }
        predictionService.save(prediction);
        return "redirect:fortuneCookies/index";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("prediction", predictionService.findOne(id));
        return "fortuneCookies/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("prediction") @Valid Prediction prediction,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "fortuneCookies/edit";
        }
        predictionService.update(id, prediction);
        return "redirect:/fortuneCookies";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        predictionService.delete(id);
        return "redirect:/fortuneCookies/index";
    }
}