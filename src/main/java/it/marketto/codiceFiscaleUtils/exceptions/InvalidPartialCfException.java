package it.marketto.codiceFiscaleUtils.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvalidPartialCfException extends Exception {
    String partialCf;
}
