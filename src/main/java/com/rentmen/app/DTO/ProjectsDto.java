package com.rentmen.app.DTO;

import javax.persistence.Column;

public class ProjectsDto {

    private Long id;

    private String name;

    private String description;

    public ProjectsDto() {
    }

    public ProjectsDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
