package com.helios.helios.objects.election;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by kepuss on 16.05.15.
 */

public class Question {
    private List<String> answer_urls;
    private List<String> answers;
    private String choice_type;
    private int max;
    private int min;
    private String question;
    private String result_type;

    private String short_name;
    private String tally_type;


    public List<String> getAnswer_urls() {
        return answer_urls;
    }

    public void setAnswer_urls(List<String> answer_urls) {
        this.answer_urls = answer_urls;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getChoice_type() {
        return choice_type;
    }

    public void setChoice_type(String choice_type) {
        this.choice_type = choice_type;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getResult_type() {
        return result_type;
    }

    public void setResult_type(String result_type) {
        this.result_type = result_type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getTally_type() {
        return tally_type;
    }

    public void setTally_type(String tally_type) {
        this.tally_type = tally_type;
    }
}
