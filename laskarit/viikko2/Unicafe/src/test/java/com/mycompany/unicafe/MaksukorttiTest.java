package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }

    @Test
    public void saldoOikeinAlussa() {
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void lataaminenKasvattaaSaldoa() {
        kortti.lataaRahaa(100);
        assertEquals("saldo: 1.10", kortti.toString());
    }

    @Test
    public void saldoVaheneeOikein() {
        kortti.otaRahaa(1);
        assertEquals("saldo: 0.9", kortti.toString());
    }

    @Test
    public void saldoEiMuutuJosOtetaanLiikaa() {
        kortti.otaRahaa(100);
        assertEquals("saldo: 0.10", kortti.toString());
    }

    @Test
    public void palauttaaTrueKunSaldoRiittaa() { assertTrue(kortti.otaRahaa(1)); }

    @Test
    public void palauttaaFalseKunSaldoEiRiita() { assertFalse(kortti.otaRahaa(100));}

}
