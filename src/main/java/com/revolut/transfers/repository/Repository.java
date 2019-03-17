package com.revolut.transfers.repository;

public interface Repository {

  String nextId();

  boolean checkExists(String id);
}
