package com.root.model;

import java.util.*;

public class Ucak{

    public Ucak(){
        //KEY=Koltuk numarasi, VALUE=threadId(müşteri no vb. değer de yapabilirsiniz.)
        KOLTUK_TABLOSU = new TreeMap<String,String>();

        //başlangıç olarak tüm koltuklar 'boş' olarak işaretlendi.
        this.KOLTUK_TABLOSU.put("1A", "bos");
        this.KOLTUK_TABLOSU.put("1B", "bos");
        this.KOLTUK_TABLOSU.put("1C", "bos");
        this.KOLTUK_TABLOSU.put("1D", "bos");
        this.KOLTUK_TABLOSU.put("1E", "bos");
        this.KOLTUK_TABLOSU.put("1F", "bos");
        this.KOLTUK_TABLOSU.put("2A", "bos");
        this.KOLTUK_TABLOSU.put("2B", "bos");
        this.KOLTUK_TABLOSU.put("2C", "bos");
        this.KOLTUK_TABLOSU.put("2D", "bos");
        this.KOLTUK_TABLOSU.put("2E", "bos");
        this.KOLTUK_TABLOSU.put("2F", "bos");
        this.KOLTUK_TABLOSU.put("3A", "bos");
        this.KOLTUK_TABLOSU.put("3B", "bos");
        this.KOLTUK_TABLOSU.put("3C", "bos");
        this.KOLTUK_TABLOSU.put("3D", "bos");
        this.KOLTUK_TABLOSU.put("3E", "bos");
        this.KOLTUK_TABLOSU.put("3F", "bos");
        this.KOLTUK_TABLOSU.put("4A", "bos");
        this.KOLTUK_TABLOSU.put("4B", "bos");
        this.KOLTUK_TABLOSU.put("4C", "bos");
        this.KOLTUK_TABLOSU.put("4D", "bos");
        this.KOLTUK_TABLOSU.put("4E", "bos");
        this.KOLTUK_TABLOSU.put("4F", "bos");
    }
    private static TreeMap<String, String> KOLTUK_TABLOSU;

    public boolean musaitIseSatinAl(String key, int threadId) {
        if (this.getKoltukTablosu().get(key) == "bos" ){
            this.KOLTUK_TABLOSU.replace(key, Integer.toString(threadId));
            return true;
        }
        else
            return false;
    }

    public Map<String, String> getKoltukTablosu(){
        return KOLTUK_TABLOSU;
    }


}
