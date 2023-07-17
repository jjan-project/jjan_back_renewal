package com.team.jjan.upload.dto;

import com.team.jjan.common.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse extends GenericResponse {
    private String identifier;
    private String fileName;
    private String url;
}