package com.example;

import com.example.CNPJ.CNPJ;

public class Main {
    public static void main(String[] args) {
        System.out.println(new CNPJ("UM1000DEPEAO").asLong());
        System.out.println(new CNPJ("ZZZZZZZZZZZZ").asLong());
        System.out.println(new CNPJ("123456789011").asLong());

        System.out.println(new CNPJ(4030188161716827807L).asString());
        System.out.println(new CNPJ(4739381338321616894L).asString());
        System.out.println(new CNPJ(123456789011L).asLong());
    }
}