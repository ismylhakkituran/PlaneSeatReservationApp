package com.root.service;

import com.root.model.Ucak;
import com.root.view.SenkronSeatStatusPrinter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class PlaneSeatReservationApp implements Runnable {

    public static Thread ticketSeller; // Uçağın biletlerini satışa çıkaran thread
    public static PlaneSeatReservationApp PlaneSeatReservationApp; // Ana uygulamanın sınıfı
    public SenkronSeatStatusPrinter senkronSeatStatusPrinter; // Her 5 sn. de bir koltukları ekrana basan thread
    public static Ucak samsunAnkara19Ucagi = new Ucak(); //Ucak sinifından "Samsun->Ankara aksam ucagi" nesnesi

    class CustomerExecutor implements Runnable {

        ReentrantLock lock = new ReentrantLock();

        public void bosKoltukBul() {
            System.out.println("Thread Id: " + Thread.currentThread().getId() + " is gonna lock here");
            lock.lock();
            int musteriNo = (int)Thread.currentThread().getId();
            System.out.println("Thread Id: " + musteriNo + " locked the critical zone");
            samsunAnkara19Ucagi
                    .getKoltukNumaralariTablosu()
                    .stream()
                    .takeWhile( koltukNo -> samsunAnkara19Ucagi.dahaOnceBiletAlmamıs( musteriNo ) )
                    .filter(koltukNo -> samsunAnkara19Ucagi.musaitIseSatinAl(koltukNo, musteriNo ) )
                    .forEach( t -> System.out.println("satin alma sonucu: "+t+ " koltukNo, şu musteri tarafından alındı: "+musteriNo));

            lock.unlock();
            System.out.println("Thread Id: " +Thread.currentThread().getId() + " unlocked the critical zone");
            for(int i=0; i<10000000; i++){}
        }

        public boolean isAllThreadsRunning(){
            return THREAD_TABLOSU.keySet().size() == 50;
        }

        public void run()
        {
            THREAD_TABLOSU.put( Thread.currentThread().getId(), Thread.currentThread().getState() ) ;
            while (true) {

                //50 threadin tamamı da RUNNING moduna geçene kadar diğer threadleri oyala
                while (!isAllThreadsRunning()) {  }

                //Threadlerin concurrent koşmaları için boş bir döngü oluştur.
                for (int i = 0; i < 10000000; i++) { }

                //Ucak dolu ise, dolu olduğunu gören current threadi kapat
                if (samsunAnkara19Ucagi.ucakDoluMu()){
                    try {
                        System.out.println("Thread Id: " +Thread.currentThread().getId() + " kapanıyor.");
                        Thread.currentThread().join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                bosKoltukBul();

                try {
                    Thread.sleep(9000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Map THREAD_TABLOSU = new ConcurrentHashMap<>();

    public void run()
    {
        //Multi-thread olarak koşacağımız bilet satın almak isteyen nesneyi tanımla
        CustomerExecutor customerExecutor = new CustomerExecutor();

        //50 adetlik thread-havuzu oluştur ve CustomerExecutor sınıfının run() metodunu 50 ayrı thread ile koş
        //ArrayList<Thread> customerList = new ArrayList<Thread>();
        List<Thread> customerList = Arrays.asList(new Thread[50]);
        customerList.forEach(d -> new Thread(customerExecutor).start() );
        System.out.println("Satin almak isteyen müşteri(thread) sayısı: "+ customerList.size() );

        //run metodunu koşan ticketSeller threadinin terminate olmaması için sonsuz döngüye alıyoruz.
        while(true){}
    }

    public static void main(String[] args) throws InterruptedException {

        //Ucak koltuk durumunu her 2 sn. bir ekrana basan sinifi, ayri bir thread olarak calistir
        SenkronSeatStatusPrinter senkronDataPrinter = new SenkronSeatStatusPrinter();
        Thread printerThread = new Thread(senkronDataPrinter);
        printerThread.start();

        //PlaneSeatReservationApp ana sınıfımızı ticketSeller isimli bir thread olarak da calistir
        PlaneSeatReservationApp = new PlaneSeatReservationApp();
        ticketSeller = new Thread(PlaneSeatReservationApp);
        ticketSeller.start();

        // Yeni başlatılan threadlerin çalışması ve programın açık kalması için, programin main-threadini sonsuz döngüye al.
        while (true){ /*Thread.sleep(1000);*/ }
    }
}
