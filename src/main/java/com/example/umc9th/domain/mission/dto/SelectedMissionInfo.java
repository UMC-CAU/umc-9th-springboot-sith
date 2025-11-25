package com.example.umc9th.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectedMissionInfo {
    private Long memberMissionId;
    private String storeName;
    private String missionDescription;
    private Integer point;
    private Boolean isCompleted;
}
