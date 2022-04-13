package com.kenzie.app;

// import necessary libraries
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    //Java Fundamentals Capstone project:
    static String URL = "https://jservice.kenzie.academy/api/clues";

    public static boolean checkAnswer(String userAnswer, String rightAnswer) {
        if (userAnswer.equalsIgnoreCase(rightAnswer)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        try {
            CustomHttpClient questionHttpClient = new CustomHttpClient();
            ObjectMapper mapper = new ObjectMapper();

            String jsonQuestion = questionHttpClient.sendGET(URL);
            QuestionDTO question = mapper.readValue(jsonQuestion, QuestionDTO.class);
            List<Clues> questionList = question.getClues();

            int numOfQuestions = 0;
            int numOfCorrectAnswers = 0;

            do {
                try {
                    Scanner scanner = new Scanner(System.in);
                    int questionNumber = (int) (Math.random() * (questionList.size() - 1));
                    Clues questionClue = questionList.get(questionNumber);
                    Category questionCategory = questionClue.getCategory();

                    System.out.println("Category: " + questionCategory.getTitle() + " " + "Question: " + questionClue.getQuestion());
                    String participantsAnswer = scanner.nextLine();

                    if (checkAnswer(participantsAnswer, questionClue.getAnswer()) == true) {
                        numOfCorrectAnswers++;
                        System.out.println("Correct! Current Score: " + numOfCorrectAnswers + "/10");
                    } else {
                        System.out.println("Incorrect. The correct answer was: " + questionClue.getAnswer() + ". Current Score: " + numOfCorrectAnswers + "/10");
                    }

                    numOfQuestions++;
                }
                catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            while (numOfQuestions < 10);

            System.out.println("All Done! Your total score: " + numOfCorrectAnswers + "/10");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

