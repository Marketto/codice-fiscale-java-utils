package it.marketto.codiceFiscaleUtils.gender;

public enum Genders {
	MALE(0),
	FEMALE(40);

	int genderOffset;
	Genders(int genderOffset) {
		this.genderOffset = genderOffset; 
	}
	public int toValue() {
		return genderOffset;
	}
}
