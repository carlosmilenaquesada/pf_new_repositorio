package com.example.tfg_carlosmilenaquesada.models.desk;

public class FirstAndLast <T>{
    T first;
    T last;

    public FirstAndLast() {

    }

    public FirstAndLast(T first, T last) {
        this.first = first;
        this.last = last;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getLast() {
        return last;
    }

    public void setLast(T last) {
        this.last = last;
    }

}
