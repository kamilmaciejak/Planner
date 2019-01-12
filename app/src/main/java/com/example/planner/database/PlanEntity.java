package com.example.planner.database;

import org.greenrobot.greendao.annotation.*;

import java.util.Date;

@Entity(nameInDb = "plan")
public class PlanEntity {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "title")
    private String title;

    @Property(nameInDb = "description")
    private String description;

    @Property(nameInDb = "deadline")
    private Date deadline;

    @Property(nameInDb = "creation_date")
    private Date creationDate;

    @Property(nameInDb = "position")
    private Long position;

    @Property(nameInDb = "type")
    @Convert(converter = PlanTypeConverter.class, columnType = String.class)
    private PlanType type;

    @Generated(hash = 911938406)
    public PlanEntity(Long id, String title, String description, Date deadline,
            Date creationDate, Long position, PlanType type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.creationDate = creationDate;
        this.position = position;
        this.type = type;
    }

    @Generated(hash = 8992154)
    public PlanEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return this.deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getPosition() {
        return this.position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public PlanType getType() {
        return this.type;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

}
