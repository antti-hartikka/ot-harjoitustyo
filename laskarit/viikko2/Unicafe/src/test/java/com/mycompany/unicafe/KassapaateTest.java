package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate kassapaate;
    Maksukortti maksukortti;

    @Before
    public void setUp(){
        kassapaate = new Kassapaate();
        maksukortti = new Maksukortti(1000);
    }

    @Test
    public void rahaOikeinAlussa() {assertEquals(100000, kassapaate.kassassaRahaa());}

    @Test
    public void lounaitaMyytyAlussaNolla() {
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kateisellaMaksuToimii() {
        int vaihtoraha1 = kassapaate.syoEdullisesti(1000);
        int vaihtoraha2 = kassapaate.syoMaukkaasti(1000);
        assertEquals(760, vaihtoraha1);
        assertEquals(600, vaihtoraha2);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void liianVahanKateistaEiTeeMuutoksia() {
        int vaihtoraha1 = kassapaate.syoEdullisesti(20);
        int vaihtoraha2 = kassapaate.syoMaukkaasti(20);
        assertEquals(20, vaihtoraha1);
        assertEquals(20, vaihtoraha2);
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void korttimaksuToimii() {
        boolean b1 = kassapaate.syoEdullisesti(maksukortti);
        boolean b2 = kassapaate.syoMaukkaasti(maksukortti);
        assertTrue(b1);
        assertTrue(b2);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void mikaanEiMuutuKunEiTarpeeksiKatetta() {
        maksukortti = new Maksukortti(20);
        boolean b1 = kassapaate.syoEdullisesti(maksukortti);
        boolean b2 = kassapaate.syoMaukkaasti(maksukortti);
        assertFalse(b1);
        assertFalse(b2);
        assertEquals(20, maksukortti.saldo());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void rahamaaraEiMuutuKortillaMaksettaessa() {
        kassapaate.syoEdullisesti(maksukortti);
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void kortinLatausOnnistuu() {
        kassapaate.lataaRahaaKortille(maksukortti, 2000);
        assertEquals(3000, maksukortti.saldo());
        assertEquals(102000, kassapaate.kassassaRahaa());
    }

    @Test
    public void kortinLatausEiOnnistuNegatiivisellaSummalla() {
        kassapaate.lataaRahaaKortille(maksukortti, -2000);
        assertEquals(1000, maksukortti.saldo());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

}