package com.example.madooding.healthpy.interfaces;

/**
 * Created by madooding on 12/2/2016 AD.
 */

public interface AppEnvSubject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();
}
