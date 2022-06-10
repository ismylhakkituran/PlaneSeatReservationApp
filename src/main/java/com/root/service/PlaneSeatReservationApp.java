package com.root.service;
import com.root.model.Ucak;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class PlaneSeatReservationApp implements Runnable {

    public static Thread ticketSeller; // Uçağın biletlerini satışa çıkaran thread
    public static PlaneSeatReservationApp PlaneSeatReservationApp; // Ana uygulamanın sınıfı
    public SenkronSeatStatusPrinter senkronSeatStatusPrinter; // Her 5 sn. de bir koltukları ekrana basan thread
    public static Ucak szfToAnkEveningFlight = new Ucak(); //Ucak sinifından "Samsun->Ankara aksam ucagi" nesnesi

    class CustomerExecutor implements Runnable {

        private AtomicBoolean biletAlindi = new AtomicBoolean(false);
        ReentrantLock lock = new ReentrantLock();

        public void bosKoltukBul() {
            lock.lock();
            System.out.println(Thread.currentThread().getId() + " numaralı thread locked here");
            for (String key : szfToAnkEveningFlight.getKoltukTablosu().keySet()) {
               // System.out.println(Thread.currentThread().getId() + " numaralı thread biletAlindi : " + biletAlindi.get());
                if (biletAlindi.get() == Boolean.FALSE && szfToAnkEveningFlight.musaitIseSatinAl(key, (int) Thread.currentThread().getId()) == true) {
                    System.out.println(key + " numaralı bilet, " + Thread.currentThread().getId() + " numaralı thread tarafından satın alındı.");
                    biletAlindi.set(Boolean.TRUE);
                    //break; //1 adet boş koltuk bulana kadar tara, satın alma yapınca döngüden çık
                }
            }
            System.out.println("after for | id is: " + Thread.currentThread().getId());
            lock.unlock();

//            if(biletAlindi.get() == Boolean.TRUE){ 1 threadin max 1 adet satın almasını istiyorsak
//                    return;}
        }

        public void run()
        {
            while (true){

                System.out.println("id is: "+Thread.currentThread().getId() );
                bosKoltukBul();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void run()
    {
        //Multi-thread olarak koşacağımız nesneyi tanımla
        CustomerExecutor customerExecutor = new CustomerExecutor();

        //50 adet thread oluştur ve CustomerExecutor sınıfının run() metodunu 50 ayrı thread ile koş
        ArrayList<Thread> customerList = new ArrayList<Thread>();

        for (int i=0; i<50; i++) {
                customerList.add(new Thread(customerExecutor ) ) ;
                customerList.get( i ).start();
                System.out.println(i + ". customerList is "+customerList.get( i ).getState() );
        }
        System.out.println("customerList.size() "+ customerList.size() );

        //run metodunu koşan ticketSeller threadinin terminate olmaması için sonsuz döngüye alıyoruz.
        while(true){}
    }


    public static void main(String[] args) throws InterruptedException {

        //Ucak koltuk durumunu her 5 sn. bir ekrana basan sinifi, ayri bir thread olarak calistir
        SenkronSeatStatusPrinter senkronDataPrinter = new SenkronSeatStatusPrinter();
        Thread printerThread = new Thread(senkronDataPrinter);
        printerThread.start();

        PlaneSeatReservationApp = new PlaneSeatReservationApp();

        //PlaneSeatReservationApp sınıfını ticketSeller isimli bir thread ile koştur.
        ticketSeller = new Thread(PlaneSeatReservationApp);

        System.out.println("ticketSeller.getState(): "+ ticketSeller.getState());
        ticketSeller.start();

        while (true){
            //Thread.sleep(1000);
        }

    }

    static class SenkronSeatStatusPrinter implements Runnable {
        public void run() {
            while (true){

                //KOLTUK TABLOSU (Map) veri yapısının her elemanini dongu kullanmadan, Stream API ile ekrana basar.
                System.out.println("KOLTUKLARDA DURUMLAR: ");
                szfToAnkEveningFlight.getKoltukTablosu().keySet().stream()
                        .map(c -> "["+c + ":" +  szfToAnkEveningFlight.getKoltukTablosu().get(c).toString()+"] , " )
                        .forEach(System.out::print);
                System.out.println();

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
