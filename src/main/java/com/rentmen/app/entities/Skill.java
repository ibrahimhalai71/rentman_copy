package com.rentmen.app.entities;

import javax.persistence.*;

@Entity
@Table(
		name = "skill"
)
public class Skill implements Comparable<Skill> {
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY
	)
	@Column(
			name = "id"
	)
	private Long id;
	@Column(
			name = "skill_name"
	)
	private String skillName;

	public Skill() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.skillName;
	}

	public void setName(String skillName) {
		this.skillName = skillName;
	}

	public int compareTo(Skill o) {
		return this.skillName.compareTo(o.skillName);
	}
}
