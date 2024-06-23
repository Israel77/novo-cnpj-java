package com.example.CNPJ;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CNPJ
 */
public class CNPJ {

    private static long MAX_CNPJ_ANTIGO = 999_999_999_999_999L;
    private Long cnpj;

    public CNPJ(long cnpj) {
        this.cnpj = cnpj;
    }

    public CNPJ(String cnpjString) {
        boolean isAlfanumerico = cnpjString
                .chars()
                .allMatch(c -> Character.isDigit(c) || Character.isAlphabetic(c));

        if (!isAlfanumerico) {
            throw new IllegalArgumentException("CNPJ deve ser alfanumerico");
        }

        // Inicialização dos acumuladores multiplicativo e aditivo
        Long acumuladorMultiplicativo = 1L;
        Long acumuladorAditivo;
        try {
            Long.parseLong(cnpjString);
            acumuladorAditivo = 0L;
        } catch (NumberFormatException e) {
            acumuladorAditivo = MAX_CNPJ_ANTIGO;
        }

        Long base = cnpjString.chars().allMatch(c -> Character.isDigit(c)) ? 10L : 36L;

        List<Integer> caracteresCNPJInvertido;
        {
            caracteresCNPJInvertido = cnpjString
                    .chars()
                    .boxed()
                    .collect(Collectors.toList());
            Collections.reverse(caracteresCNPJInvertido);
        }

        for (Integer charCode : caracteresCNPJInvertido) {
            if (charCode >= 65) {
                acumuladorAditivo += acumuladorMultiplicativo * (charCode - 55);
            } else {
                acumuladorAditivo += acumuladorMultiplicativo * (charCode - 48);
            }

            acumuladorMultiplicativo *= base;
        }

        this.cnpj = acumuladorAditivo;
    }

    public Long asLong() {
        return this.cnpj;
    }

    public String asString() {
        if (this.cnpj <= MAX_CNPJ_ANTIGO) {
            return String.format("%014d", this.cnpj);
        }

        Integer base = 36;
        Integer expoente = 11;
        Long resto = this.cnpj - MAX_CNPJ_ANTIGO;

        Long acumuladorMultiplicativo = 1L;
        Long quociente = 0L;
        StringBuilder cnpjString = new StringBuilder();

        // acumuladorMultiplicativo = base^expoente
        for (int i = 0; i < expoente; i++) {
            acumuladorMultiplicativo *= base;
        }

        while (acumuladorMultiplicativo > 0) {
            quociente = resto / acumuladorMultiplicativo;
            resto -= quociente * acumuladorMultiplicativo;

            Character caractere = (char) (quociente < 10 ? quociente + 48 : quociente + 55);
            cnpjString.append(caractere);

            acumuladorMultiplicativo /= base;
        }

        return cnpjString.toString();
    }
}