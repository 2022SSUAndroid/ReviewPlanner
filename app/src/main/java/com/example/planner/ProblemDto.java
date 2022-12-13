package com.example.planner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ProblemDto implements Serializable {

    private String category;
    private ArrayList<Integer> cycle;
    private ArrayList<String> mySolving;
    private ArrayList<Boolean> ox;
    private String problemImg;
    private String problemName;
    private Long reviewCnt;
    private ArrayList<String> reviewDay;
    private ArrayList<String> reviewTag;
    private String solutionImg;

    ProblemDto(Map<String, Object> map) {
        this.category = (String) map.get("category");
        this.cycle = (ArrayList<Integer>) map.get("cycle");
        this.mySolving = (ArrayList<String>) map.get("mySolving");
        this.ox = (ArrayList<Boolean>) map.get("ox");
        this.problemImg = (String) map.get("problemImg");
        this.problemName = (String) map.get("problemName");
        this.reviewCnt = (Long) map.get("reviewCnt");
        this.reviewDay = (ArrayList<String>) map.get("reviewDay");
        this.reviewTag = (ArrayList<String>) map.get("reviewTag");
        this.solutionImg = (String) map.get("solutionImg");

    }

    public ArrayList<Boolean> getOx() {
        return ox;
    }

    public ArrayList<Integer> getCycle() {
        return cycle;
    }

    public ArrayList<String> getMySolving() {
        return mySolving;
    }

    public ArrayList<String> getReviewDay() {
        return reviewDay;
    }

    public ArrayList<String> getReviewTag() {
        return reviewTag;
    }

    public Long getReviewCnt() {
        return reviewCnt;
    }

    public String getCategory() {
        return category;
    }

    public String getProblemImg() {
        return problemImg;
    }

    public String getProblemName() {
        return problemName;
    }

    public String getSolutionImg() {
        return solutionImg;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCycle(ArrayList<Integer> cycle) {
        this.cycle = cycle;
    }

    public void setMySolving(ArrayList<String> mySolving) {
        this.mySolving = mySolving;
    }

    public void setOx(ArrayList<Boolean> ox) {
        this.ox = ox;
    }

    public void setProblemImg(String problemImg) {
        this.problemImg = problemImg;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public void setReviewCnt(Long reviewCnt) {
        this.reviewCnt = reviewCnt;
    }

    public void setReviewDay(ArrayList<String> reviewDay) {
        this.reviewDay = reviewDay;
    }

    public void setReviewTag(ArrayList<String> reviewTag) {
        this.reviewTag = reviewTag;
    }

    public void setSolutionImg(String solutionImg) {
        this.solutionImg = solutionImg;
    }
}
