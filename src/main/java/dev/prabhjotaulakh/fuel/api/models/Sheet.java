package dev.prabhjotaulakh.fuel.api.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sheets")
public class Sheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sheetId;

    @Column(nullable = false)
    private String sheetName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "sheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Log> logs;

    public Sheet() {
    }

    public Sheet(Integer sheetId, String sheetName, User user, List<Log> logs) {
        this.sheetId = sheetId;
        this.sheetName = sheetName;
        this.user = user;
        this.logs = logs;
    }

    public Integer getSheetId() {
        return sheetId;
    }

    public void setSheetId(Integer sheetId) {
        this.sheetId = sheetId;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }
}
