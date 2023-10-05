package edu.school21.viewmodels.handlers.wrappers;

import edu.school21.enums.RotationPeriod;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "history")
public class HistoryWrapper {

    private List<String> history;
    private String periodRotation;

    @XmlElement(name = "item")
    public List<String> getHistory() {
        return history;
    }

    @XmlElement(name = "periodRotation")
    public String getPeriodRotation() {
        return periodRotation == null ? RotationPeriod.HOURLY.getName() : periodRotation;
    }

    public void setHistory(final List<String> history) {
        this.history = history;
    }

    public void setPeriodRotation(final String periodRotation) {
        this.periodRotation = periodRotation;
    }
}
