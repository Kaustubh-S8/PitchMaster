package com.PitchMaster.dto;


import lombok.Data;

@Data
public class RatingResponse {
    private Integer rid;
    private Integer uid;
    private Integer pid;
    private Integer communication;
    private Integer content;
    private Integer interaction;
    private Integer liveliness;
    private Integer usageProps;
    private Double totalScore;
}
