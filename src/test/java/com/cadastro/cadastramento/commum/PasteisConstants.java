package com.cadastro.cadastramento.commum;

import com.cadastro.cadastramento.entity.Pasteis;
import com.cadastro.cadastramento.entity.Pasteis.Tamanho;

public class PasteisConstants {

    public static Pasteis pastelCreate = new Pasteis(null, "Calabresa", Tamanho.GRANDE, "Teste", 14.90);
    public static Pasteis pastelCreated = new Pasteis(1L, "Calabresa", Tamanho.GRANDE, "Teste", 14.90);
    public static Pasteis pastelNull = new Pasteis(null, null, null, null, null);
    public static Pasteis pastelEmpty = new Pasteis();
}
