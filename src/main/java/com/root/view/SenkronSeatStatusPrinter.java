package com.root.view;

import com.root.model.Ucak;

public class SenkronSeatStatusPrinter implements Runnable {
    public void run() {
        while (  !Ucak.ucakDoluMu() ){

            //KOLTUK TABLOSU (Map) veri yapısının her elemanini dongu kullanmadan, Stream API ile ekrana basar.
            System.out.println("KOLTUKLARDA DURUMLAR: ");
            Ucak
            .getKoltukNumaralariTablosu()
            .stream()
            .map(koltukNo -> "["+koltukNo + ":" +  Ucak.getKoltukInfo(koltukNo) +"]" )
            .forEach(System.out::print);

            System.out.println();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("TÜM BİLETLER SATILMIŞTIR, İLGİNİZ İÇİN TEŞEKKÜR EDERİZ.");
    }
}