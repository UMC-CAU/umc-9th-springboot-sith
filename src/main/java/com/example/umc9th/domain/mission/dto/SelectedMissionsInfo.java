package com.example.umc9th.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectedMissionsInfo {
    private Long memberMissionId;
    private String storeName;
    private String missionDescription;
    private Integer point;
    private Boolean is_completed;
}
