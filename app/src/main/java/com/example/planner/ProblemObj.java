package com.example.planner;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemObj implements Serializable {
    private String problemName = "";
    private String category = "";
    private List<Integer> cycle = new ArrayList<>();
    private String problemImg = "";
    private String solutionImg = "";
    private int reviewCnt = 0;
    private List<String> reviewDay = new ArrayList<>();
    private List<Boolean> ox = new ArrayList<>(Arrays.asList(false));
    private List<String> reviewTag = new ArrayList<>();
    private List<String> mySolving = new ArrayList<>(Arrays.asList(""));

    public ProblemObj() {

        this.problemName = "";
        this.category = "";
        this.cycle = new ArrayList<>();
        this.problemImg = "";
        this.solutionImg = "";
        this.reviewCnt = 0;
        this.reviewDay = new ArrayList<>();
        this.ox = new ArrayList<>(Arrays.asList(false));
        this.reviewTag = new ArrayList<>();
        this.mySolving = new ArrayList<>(Arrays.asList(""));
    }

    public ProblemObj(Map<String, Object> map) {

        this.category = (String) map.get("category");
        this.cycle = (List<Integer>) map.get("cycle");
        this.mySolving = (List<String>) map.get("mySolving");
        this.ox = (List<Boolean>) map.get("ox");
        this.problemImg = (String) map.get("problemImg");
        this.problemName = (String) map.get("problemName");
        this.reviewCnt = Integer.parseInt(String.valueOf(map.get("reviewCnt")));
        this.reviewDay = (List<String>) map.get("reviewDay");
        this.reviewTag = (List<String>) map.get("reviewTag");
        this.solutionImg = (String) map.get("solutionImg");
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Integer> getCycle() {
        return cycle;
    }

    public void setCycle(List<Integer> cycle) {
        this.cycle = cycle;
    }

    public String getProblemImg() {
        return problemImg;
    }

    public void setProblemImg(String problemImg) {
        this.problemImg = problemImg;
    }

    public String getSolutionImg() {
        return solutionImg;
    }

    public void setSolutionImg(String solutionImg) {
        this.solutionImg = solutionImg;
    }

    public int getReviewCnt() {
        return reviewCnt;
    }

    public void setReviewCnt(int recycleCnt) {
        this.reviewCnt = recycleCnt;
    }

    public List<String> calculateReviewDay () {
        List<String> reviewDay = new ArrayList<>();
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        for (int i = 0; i < cycle.size(); i++) {
            cal.add(Calendar.DATE, cycle.get(i));
            reviewDay.add(format.format(cal.getTime()));
            cal.add(Calendar.DATE, -cycle.get(i));
        }

        return reviewDay;
    }

    public List<Boolean> getOX() {
        return ox;
    }

    public void setOX(List<Boolean> ox) {
        this.ox = ox;
    }

    public void addOX(Boolean tf) {
        this.ox.add(tf);
        reviewCnt++;
    }

    public void addMySolving(String img) {
        this.mySolving.add(img);
    }

    public List<String> getReviewTag() {
        return reviewTag;
    }

    public void setReviewTag(List<String> reviewTag) {
        this.reviewTag = reviewTag;
    }

    public void addReviewTag(String reviewTag) {
        this.reviewTag.add(reviewTag);
    }

    public List<String> getMySolving() {
        return mySolving;
    }

    public void setMySolving(List<String> mySolving) {
        this.mySolving = mySolving;
    }

    public List<String> getReviewDay() {
        return reviewDay;
    }

    public void setReviewDay(List<String> reviewDay) {
        this.reviewDay = reviewDay;
    }
}