package jjan_back_renewal.upload;

import jjan_back_renewal.common.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponseDto extends GenericResponse {
    private String identifier;
    private String fileName;
    private String url;
}