package com.github.albertocsm.example;

public class Trips {
  // members
  private int id;
  private int day;

  // getters & setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  // public API
  public Trips() {
  }

  public Trips(int id, int day) {
    this.id = id;
    this.day = day;
  }
}
