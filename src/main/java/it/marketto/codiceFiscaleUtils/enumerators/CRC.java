package it.marketto.codiceFiscaleUtils.enumerators;

import java.util.Arrays;

public enum CRC {
    B(0),
    A(1),
    K(2),
    P(3),
    L(4),
    C(5),
    Q(6),
    D(7),
    R(8),
    E(9),
    V(10),
    O(11),
    S(12),
    F(13),
    T(14),
    G(15),
    U(16),
    H(17),
    M(18),
    I(19),
    N(20),
    J(21),
    W(22),
    Z(23),
    Y(24),
    X(25);

	private int crcValue;
	CRC(int value) {
		this.crcValue = value;
	}
	
	public int toValue() {
		return crcValue;
	}
	
	public char toChar() {
		return toString().charAt(0);
	}

	public static CRC from(char c) {
		return Arrays.stream(values())
			.filter(crc -> crc.toString().equalsIgnoreCase(String.valueOf(c)))
			.findFirst()
            .orElse(null);
	}
	public static CRC from(int val) {
		return Arrays.stream(values())
			.filter(crc -> crc.toValue() == val)
			.findFirst()
            .orElse(null);
	}
}
