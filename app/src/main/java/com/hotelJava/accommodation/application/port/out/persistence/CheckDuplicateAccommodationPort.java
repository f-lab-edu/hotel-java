package com.hotelJava.accommodation.application.port.out.persistence;


public interface CheckDuplicateAccommodationPort {
  boolean isDuplicateByName(String name);
}
