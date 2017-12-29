package com.sajorahasan.quizapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Sajora on 27-12-2017.
 */

public class QuizEvent implements Parcelable {

    private int questionNumber;
    private String question;
    private String correctAnswer;
    private List<String> answer = null;

    private int incorrectQueNum;
    private String incorrectAns;

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public int getIncorrectQueNum() {
        return incorrectQueNum;
    }

    public void setIncorrectQueNum(int incorrectQueNum) {
        this.incorrectQueNum = incorrectQueNum;
    }

    public String getIncorrectAns() {
        return incorrectAns;
    }

    public void setIncorrectAns(String incorrectAns) {
        this.incorrectAns = incorrectAns;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.questionNumber);
        dest.writeString(this.question);
        dest.writeString(this.correctAnswer);
        dest.writeStringList(this.answer);
        dest.writeInt(this.incorrectQueNum);
        dest.writeString(this.incorrectAns);
    }

    public QuizEvent() {
    }

    protected QuizEvent(Parcel in) {
        this.questionNumber = in.readInt();
        this.question = in.readString();
        this.correctAnswer = in.readString();
        this.answer = in.createStringArrayList();
        this.incorrectQueNum = in.readInt();
        this.incorrectAns = in.readString();
    }

    public static final Creator<QuizEvent> CREATOR = new Creator<QuizEvent>() {
        @Override
        public QuizEvent createFromParcel(Parcel source) {
            return new QuizEvent(source);
        }

        @Override
        public QuizEvent[] newArray(int size) {
            return new QuizEvent[size];
        }
    };
}
