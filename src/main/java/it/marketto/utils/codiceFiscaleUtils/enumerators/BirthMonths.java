package it.marketto.utils.codiceFiscaleUtils.enumerators;

import java.util.Arrays;

public enum BirthMonths {
    A(0),
    B(1),
    C(2),
    D(3),
    E(4),
    H(5),
    L(6),
    M(7),
    P(8),
    R(9),
    S(10),
    T(11);

    private int matchingMonth;
    BirthMonths(int value) { this.matchingMonth = value; }

    public int toValue() {
        return matchingMonth;
    }

    public char toChar() {
        return toString().charAt(0);
    }

    public static BirthMonths from(char c) {
        return Arrays.stream(values())
                .filter(month -> month.toString().equalsIgnoreCase(String.valueOf(c)))
                .findFirst()
                .orElse(null);
    }
    public static BirthMonths from(int val) {
        return Arrays.stream(values())
                .filter(month -> month.toValue() == val)
                .findFirst()
                .orElse(null);
    }
}
