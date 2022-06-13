package com.root.model;

import java.util.*;
import java.util.stream.Collectors;

public class Ucak{

    public Ucak(){
        //KEY=Koltuk numarasi, VALUE=threadId(müşteri no vb. değer de yapabilirsiniz.)
        KOLTUK_TABLOSU = new TreeMap<>();

        //başlangıç olarak tüm koltuklar 'boş' olarak işaretlendi.
        KOLTUK_TABLOSU.put("1A", "bos");
        KOLTUK_TABLOSU.put("1B", "bos");
        KOLTUK_TABLOSU.put("1C", "bos");
        KOLTUK_TABLOSU.put("1D", "bos");
        KOLTUK_TABLOSU.put("1E", "bos");
        KOLTUK_TABLOSU.put("1F", "bos");
        KOLTUK_TABLOSU.put("2A", "bos");
        KOLTUK_TABLOSU.put("2B", "bos");
        KOLTUK_TABLOSU.put("2C", "bos");
        KOLTUK_TABLOSU.put("2D", "bos");
        KOLTUK_TABLOSU.put("2E", "bos");
        KOLTUK_TABLOSU.put("2F", "bos");
        KOLTUK_TABLOSU.put("3A", "bos");
        KOLTUK_TABLOSU.put("3B", "bos");
        KOLTUK_TABLOSU.put("3C", "bos");
        KOLTUK_TABLOSU.put("3D", "bos");
        KOLTUK_TABLOSU.put("3E", "bos");
        KOLTUK_TABLOSU.put("3F", "bos");
        KOLTUK_TABLOSU.put("4A", "bos");
        KOLTUK_TABLOSU.put("4B", "bos");
        KOLTUK_TABLOSU.put("4C", "bos");
        KOLTUK_TABLOSU.put("4D", "bos");
        KOLTUK_TABLOSU.put("4E", "bos");
        KOLTUK_TABLOSU.put("4F", "bos");
    }
    private static TreeMap<String, String> KOLTUK_TABLOSU;

    /**
     * Satın alma işlemini yapan metot
     * Parametre olarak geçilen koltukNo' ya Müşteri_No değerini <K,V> olarak geçilmelidir.
     * @param koltukNo 1A-4F arasında Ucak() Constructor metodunda oluşturulan koltuk isimleri
     * @param müsteriNo Thread_Id değeri buraya geçilmelidir.
     * @return
     */
    public boolean musaitIseSatinAl(String koltukNo, int müsteriNo) {

        return switch(this.KOLTUK_TABLOSU.get(koltukNo)){
            case "bos" -> { this.KOLTUK_TABLOSU.replace(koltukNo, Integer.toString(müsteriNo)); yield true; }
            default -> false;
        };
    }

    /**
     * İlgili Musteri_No' ya daha onceden alınan bilet yoksa true, bilet almışsa false dön.
     * Parametre olarak geçilen Müşteri_No değerini Map veri yapısının Value değerleri içinde olup olmadığına bakar.
     * @param musteriNo=Thread_Id
     * @return
     */
    public boolean dahaOnceBiletAlmamıs(int musteriNo) {
        return this.KOLTUK_TABLOSU
                .values()
                .stream()
                .filter( v -> Integer.toString(musteriNo).equals(v) )
                .collect(Collectors.toList())
                .size()==0 ? true : false;
    }

    /**
     * Ucakta dolu ise true, boş ise false döner
     * Ucakta value değeri "bos" olan alan kalmamışsa, uçak full demektir.
     * @return
     */
    public static boolean ucakDoluMu() {
        return KOLTUK_TABLOSU
                .values()
                .stream()
                .filter( v -> "bos".equals(v) )
                .collect(Collectors.toList())
                .size()==0 ? true : false;
    }

    /**
     * Parametre olarak geçilen koltuğun durumunu return yapar.
     * @param koltukNo Uçak koltuk numarası
     * @return musteriNo - Kimse satın almamışsa; "bos" değeri döner.
     */
    public static String getKoltukInfo(String koltukNo){
        return KOLTUK_TABLOSU.get(koltukNo);
    }

    /**
     * Ucaktaki koltuk numarası isimlerini return yapar.
     * @return - Koltuk numarası isimler Set veri yapısındadır.
     */
    public static Set<String> getKoltukNumaralariTablosu(){
        return KOLTUK_TABLOSU.keySet();
    }
}
