package com.socialtracking.ubiss.models;

public class FacebookDataItem {

    private String sessionId;
    private String socialMediaApplication;
    private long sessionStart;
    private double sessionLength;

    public FacebookDataItem(String sessionId, long sessionStart, double sessionLength) {
        this.sessionId = sessionId;
        this.sessionStart = sessionStart;
        this.sessionLength = sessionLength/1000/60%60;
    }

    public FacebookDataItem(String sessionId, String socialMedia, long sessionStart, double sessionLength) {
        this.sessionId = sessionId;
        this.socialMediaApplication = socialMedia;
        this.sessionStart = sessionStart;
        this.sessionLength = sessionLength/1000/60%60;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSocialMediaApplication() {
        return socialMediaApplication;
    }

    public void setSocialMediaApplication(String socialApp) {this.socialMediaApplication = socialApp; }

    public long getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(long sessionStart) {
        this.sessionStart = sessionStart;
    }

    public double getSessionLength() {
        return sessionLength;
    }

    public void setSessionLength(double sessionLength) {
        this.sessionLength = sessionLength/1000/60%60;
    }

}
