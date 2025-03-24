package com.PitchMaster.dto;

import lombok.Data;

@Data
public class RatingRequest {
    private Integer uid;


  private Integer pid;


 
  private Integer communication;

  private Integer confidence;

  private Integer content;

  private Integer interaction;

  private Integer liveliness;

  private Integer usageProps;
}
