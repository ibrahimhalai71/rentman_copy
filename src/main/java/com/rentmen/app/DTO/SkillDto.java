package com.rentmen.app.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SkillDto implements Comparable<SkillDto>{

    private Long id;
    
    @JsonProperty("skill_name")
    private String skillName;

    // Getters and setters
    
    public SkillDto() {

	}

    public SkillDto(Long id2, String skillName2) {
		this.id = id2;
		this.skillName = skillName2;
	}

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

	@Override
	public int compareTo(SkillDto o) {
		// TODO Auto-generated method stub
		return this.skillName.compareTo(o.skillName);
	}
}

