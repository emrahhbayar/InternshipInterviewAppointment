package app.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InternshipType
{
    HARDWARE("Donanım"),SOFTWARE("Yazılım");
    private final String name;
}