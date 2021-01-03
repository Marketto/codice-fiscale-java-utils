package it.marketto.codiceFiscaleUtils.classes;

import it.marketto.codiceFiscaleUtils.constants.PersonalInfoMatchers;
import it.marketto.codiceFiscaleUtils.enumerators.Genders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo {
    @NotEmpty
    @Pattern(regexp = "^(" + PersonalInfoMatchers.FIRST_NAME + ")$")
	private String firstName;

    @NotEmpty
    @Pattern(regexp = "^(" + PersonalInfoMatchers.LAST_NAME + ")$")
    private String lastName;

    @PastOrPresent
    private ZonedDateTime date;

    @NotNull
    private Genders gender;

    @NotNull
    @Pattern(regexp = "^(" + PersonalInfoMatchers.BELFIORE_CODE_MATCHER + ")$")
    private String placeCode;

    @PositiveOrZero
    @Max(255)
    private int omocodeId = 0;
}
