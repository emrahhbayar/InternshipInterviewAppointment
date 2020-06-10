package app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InternshipLocation
{
    @JsonProperty("erasmus")
    ERASMUS("Erasmus"),
    @JsonProperty("abroad")
    ABROAD("Yurtdışı"),
    @JsonProperty("domestic")
    DOMESTIC("Yurtiçi");
    private final String name;
}