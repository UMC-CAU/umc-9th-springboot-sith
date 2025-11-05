package com.example.umc9th.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnselectedMissionInfo {
    private Long missionId;
    private String storeName;
    private String missionDescription;
    private Integer point;
    private LocalDateTime deadline;
}
