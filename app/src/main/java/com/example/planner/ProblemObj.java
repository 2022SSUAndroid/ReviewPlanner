package com.example.planner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProblemObj implements Serializable {
    private String problemName;
    private String category;
    private List<Integer> cycle = new ArrayList<>();
    private String problemImg;  // 자료형 정확하지 않음
    private String solutionImg; // 자료형 정확하지 않음


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
}
