package models;

import java.io.Serializable;

/**
 * Created by The Architect on 4/18/2018.
 */

public class ArchitectModel implements Serializable {

    private String archId,archName,archFirm,archShortBio,archCertification,archNumYears,archImage,archPhone,archEmail;
    private int archImageTest;

    public int getArchImageTest() {
        return archImageTest;
    }

    public void setArchImageTest(int archImageTest) {
        this.archImageTest = archImageTest;
    }

    public ArchitectModel() {
    }

    public String getArchImage() {
        return archImage;
    }

    public void setArchImage(String archImage) {
        this.archImage = archImage;
    }

    public String getArchPhone() {
        return archPhone;
    }

    public void setArchPhone(String archPhone) {
        this.archPhone = archPhone;
    }

    public String getArchEmail() {
        return archEmail;
    }

    public void setArchEmail(String archEmail) {
        this.archEmail = archEmail;
    }

    public String getArchId() {
        return archId;
    }

    public void setArchId(String archId) {
        this.archId = archId;
    }

    public String getArchName() {
        return archName;
    }

    public void setArchName(String archName) {
        this.archName = archName;
    }

    public String getArchFirm() {
        return archFirm;
    }

    public void setArchFirm(String archFirm) {
        this.archFirm = archFirm;
    }

    public String getArchShortBio() {
        return archShortBio;
    }

    public void setArchShortBio(String archShortBio) {
        this.archShortBio = archShortBio;
    }

    public String getArchCertification() {
        return archCertification;
    }

    public void setArchCertification(String archCertification) {
        this.archCertification = archCertification;
    }

    public String getArchNumYears() {
        return archNumYears;
    }

    public void setArchNumYears(String archNumYears) {
        this.archNumYears = archNumYears;
    }
}
