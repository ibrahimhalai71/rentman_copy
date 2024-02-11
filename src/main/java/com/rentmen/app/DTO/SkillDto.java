package com.rentmen.app.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SkillDto {

    private Long id;
    
    @JsonProperty("skill_name")
    private String skillName;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}

