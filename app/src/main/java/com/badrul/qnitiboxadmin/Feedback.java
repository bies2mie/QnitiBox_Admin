package com.badrul.qnitiboxadmin;

public class Feedback {


    private int feedbackID;
    private String nameID;
    private String phoneID;
    private String matrixID;
    private String userFeedback;


    public Feedback(int feedbackID, String nameID, String phoneID, String matrixID, String userFeedback) {
        this.feedbackID = feedbackID;
        this.nameID = nameID;
        this.phoneID = phoneID;
        this.matrixID = matrixID;
        this.userFeedback = userFeedback;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public String getNameID() {
        return nameID;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public String getMatrixID() {
        return matrixID;
    }

    public String getUserFeedback() {
        return userFeedback;
    }

}