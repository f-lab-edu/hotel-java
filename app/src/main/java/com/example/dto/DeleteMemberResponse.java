package com.example.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeleteMemberResponse {
    private String email;
    private String name;
}
