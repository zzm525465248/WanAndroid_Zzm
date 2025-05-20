package com.example.wanandroid_zzm;

public class Singleton {
//    private static volatile Singleton singleton;
//    private Singleton(){}
//    public static Singleton getInstance(){
//        if (singleton==null){
//            synchronized (Singleton.class){
//                singleton=new Singleton();
//            }
//        }
//        return singleton;
//    }

//    private static  final  Singleton instance=new Singleton();
//    private Singleton(){}
//    public static Singleton getInstance(){
//        return  instance;
//    }

    private static Singleton  instance;
    private Singleton(){}
    public static Singleton getInstance(){
        if(instance==null){
            instance=new Singleton();
        }
        return instance;
    }
}
