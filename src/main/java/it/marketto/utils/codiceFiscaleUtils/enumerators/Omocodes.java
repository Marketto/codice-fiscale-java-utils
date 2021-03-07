package it.marketto.utils.codiceFiscaleUtils.enumerators;

import java.util.Arrays;

public enum Omocodes {
    L(0),
    M(1),
    N(2),
    P(3),
    Q(4),
    R(5),
    S(6),
    T(7),
    U(8),
    V(9);

    int omocodeValue;

    Omocodes(int omocodeValue) {
        this.omocodeValue = omocodeValue;
    }

    public int toValue() {
        return omocodeValue;
    }

    public char toChar() {
        return toString().charAt(0);
    }

    public static Omocodes from(char crcChar) {
        return Arrays.stream(values())
                .filter(omocode -> omocode.toString().equalsIgnoreCase(String.valueOf(crcChar)))
                .findFirst()
                .orElse(null);
    }

    public static Omocodes from(int value) {
        return Arrays.stream(values())
                .filter(omocode -> omocode.toValue() == value)
                .findFirst()
                .orElse(null);
    }
}
