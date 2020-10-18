package it.marketto.codiceFiscaleUtils.classes;

import it.marketto.codiceFiscaleUtils.constants.CfOffsets;
import it.marketto.codiceFiscaleUtils.gender.Genders;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class CfMismatchValidator {
	
	private final String codiceFiscale;
	
	public CfMismatchValidator(String codiceFiscale) {
	    this.codiceFiscale = codiceFiscale != null ? codiceFiscale : "";
	}

    private boolean hasLastName() {
        return this.codiceFiscale.length() >= (CfOffsets.LASTNAME_OFFSET + CfOffsets.LASTNAME_SIZE);
    }

    private boolean hasFirstName() {
        return this.codiceFiscale.length() >= (CfOffsets.FIRSTNAME_OFFSET + CfOffsets.FIRSTNAME_SIZE);
    }
    
    private boolean hasBirthYear() {
        return this.codiceFiscale.length() >= (CfOffsets.YEAR_OFFSET + CfOffsets.YEAR_SIZE);
    }
    
    private boolean hasBirthDate() {
        return this.codiceFiscale.length() >= (CfOffsets.DATE_OFFSET + CfOffsets.DATE_SIZE);
    }
    
    private boolean hasGender() {
        return this.codiceFiscale.length() >= (CfOffsets.GENDER_OFFSET + CfOffsets.GENDER_SIZE);
    }
    
    private boolean hasBirthPlace() {
        return this.codiceFiscale.length() >= (CfOffsets.PLACE_OFFSET + CfOffsets.PLACE_SIZE);
    }
    
    private boolean hasCRC() {
        return this.codiceFiscale.length() >= (CfOffsets.CRC_OFFSET + CfOffsets.CRC_SIZE);
    }

    public boolean matchPersonalInfo(PersonalInfo personalInfo) {
        return CfPattern.codiceFiscale(personalInfo).asPredicate().test(this.codiceFiscale);
    }

    public boolean mismatchPersonalInfo(PersonalInfo personalInfo) {
        return !StringUtils.isEmpty(this.codiceFiscale) &&
                personalInfo != null &&
                personalInfo.getLastName() != null &&
                personalInfo.getFirstName() != null &&
                personalInfo.getDate() != null &&
                personalInfo.getGender() != null &&
                personalInfo.getPlace() != null &&
                !this.matchPersonalInfo(personalInfo);
    }

    public boolean matchLastName(String lastName) {
        return this.hasLastName() &&
                CfPattern.lastName(this.codiceFiscale).asPredicate().test(lastName != null ? lastName : "");
    }
    public boolean mismatchLastName(String lastName) {
        return this.hasLastName() && StringUtils.isEmpty(lastName) && !this.matchLastName(lastName);
    }

    public boolean matchFirstName(String firstName) {
        return this.hasFirstName() &&
                CfPattern.firstName(this.codiceFiscale).asPredicate().test(firstName != null ? firstName : "");
    }
    public boolean mismatchFirstName(String firstName) {
        return this.hasFirstName() && StringUtils.isEmpty(firstName) && !this.matchFirstName(firstName);
    }
/*
    public boolean matchBirthDate(birthDate: MultiFormatDate) {
        if (this.hasBirthDate) {
            const parsedCfDate = Parser.cfToBirthDate(this.codiceFiscale);
            const parsedDate = DateUtils.parseDate(birthDate);
            if (parsedCfDate && parsedDate) {
                return moment(parsedCfDate).isSame(parsedDate, "d");
            }
        }
        return false;
    }
    public mismatchBirthDate(birthDate: MultiFormatDate): boolean {
        return this.hasBirthYear && !!DateUtils.parseDate(birthDate) && !this.matchBirthDate(birthDate);
    }
*/
    public boolean matchGender(Genders gender) {
        return this.hasGender() && CfPattern.gender(this.codiceFiscale)
            .asPredicate().test(gender != null ? gender.toString() : "");
    }

    public boolean mismatchGender(Genders gender) {
        return this.hasGender() && gender != null && !this.matchGender(gender);
    }

    /*
    public boolean matchBirthPlace(String birthPlace) {
        if (this.hasBirthPlace() && birthPlace != null) {
            matcher = CfPattern.place(this.codiceFiscale);
            parsedBirthPlace = CfParser.parsePlace(birthPlace);

            return !!parsedBirthPlace && matcher.test(parsedBirthPlace.belfioreCode);
        }
        return false;
    }
    */
    /*
    public mismatchBirthPlace(birthPlace: BelfiorePlace | string): boolean {
        return this.hasBirthPlace && !!birthPlace && !this.matchBirthPlace(birthPlace);
    }
    */
    /**
     * Check the given cf validity by form, birth date/place and digit code
     * @return Generic or specific regular expression
     */
    public boolean isValid() {
        return (
            // Checking length
            this.hasCRC()
            // Checking form validity
            && CfPattern.codiceFiscale().asPredicate().test(this.codiceFiscale)
            // Checking 16th char check digit validity
            && this.codiceFiscale.substring(CfOffsets.CRC_OFFSET, CfOffsets.CRC_OFFSET + CfOffsets.CRC_SIZE)
                .equalsIgnoreCase(CheckDigitizer.checkDigit(this.codiceFiscale).toString())
            // Checking Birth date/place validity
            // && CfParser.cfToBirthPlace(this.codiceFiscale)
        );
    }
}
