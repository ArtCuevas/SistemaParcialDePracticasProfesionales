package com.sppp.model;

public class Project {
    private int idproject;
    private String nameprj;
    private String relatedorg;
    private int quota;

    public Project() {
    }

    public Project(int idproject, String nameprj, String relatedorg, int quota) {
        this.idproject = idproject;
        this.nameprj = nameprj;
        this.relatedorg = relatedorg;
        this.quota = quota;
    }

    public int getIdproject() {
        return idproject;
    }

    public void setIdproject(int idproject) {
        this.idproject = idproject;
    }

    public String getNameprj() {
        return nameprj;
    }

    public void setNameprj(String nameprj) {
        this.nameprj = nameprj;
    }

    public String getRelatedorg() {
        return relatedorg;
    }

    public void setRelatedorg(String relatedorg) {
        this.relatedorg = relatedorg;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }
}
