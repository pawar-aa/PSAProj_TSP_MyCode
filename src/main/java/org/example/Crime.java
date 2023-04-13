package org.example;

public class Crime {
    private String id;
    private String month;
    private String reportedBy;
    private String fallsWithin;
    private String longitude;
    private String latitude;
    private String location;
    private String lsoaCode;
    private String lsoaName;
    private String crimeType;
    private String lastOutcomeCategory;

    public Crime(String id, String month, String reportedBy, String fallsWithin, String longitude, String latitude, String location, String lsoaCode, String lsoaName, String crimeType) {
        this.id = id;
        this.month = month;
        this.reportedBy = reportedBy;
        this.fallsWithin = fallsWithin;
        this.longitude = longitude;
        this.latitude = latitude;
        this.location = location;
        this.lsoaCode = lsoaCode;
        this.lsoaName = lsoaName;
        this.crimeType = crimeType;
        this.lastOutcomeCategory = lastOutcomeCategory;
    }

    // getters and setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getFallsWithin() {
        return fallsWithin;
    }

    public void setFallsWithin(String fallsWithin) {
        this.fallsWithin = fallsWithin;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLsoaCode() {
        return lsoaCode;
    }

    public void setLsoaCode(String lsoaCode) {
        this.lsoaCode = lsoaCode;
    }

    public String getLsoaName() {
        return lsoaName;
    }

    public void setLsoaName(String lsoaName) {
        this.lsoaName = lsoaName;
    }

    public String getCrimeType() {
        return crimeType;
    }

    public void setCrimeType(String crimeType) {
        this.crimeType = crimeType;
    }
}

