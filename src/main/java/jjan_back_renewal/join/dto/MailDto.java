package jjan_back_renewal.join.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    private String address;
    private String title;
    private String message;
}
