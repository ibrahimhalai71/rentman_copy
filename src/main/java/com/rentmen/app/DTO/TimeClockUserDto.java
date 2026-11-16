package com.rentmen.app.DTO;

public class TimeClockUserDto {

private String name;
private Long spId;
private String pin;

    public TimeClockUserDto(String name, Long spId, String pin) {
        this.name = name;
        this.spId = spId;
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSpId() {
        return spId;
    }

    public void setSpId(Long spId) {
        this.spId = spId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
