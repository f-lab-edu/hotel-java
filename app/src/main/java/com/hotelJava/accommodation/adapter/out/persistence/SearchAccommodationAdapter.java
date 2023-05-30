package com.hotelJava.accommodation.adapter.out.persistence;

import static com.hotelJava.accommodation.domain.QAccommodation.accommodation;
import static com.hotelJava.accommodation.domain.QRoom.room;

import com.hotelJava.accommodation.application.port.out.persistence.AccommodationSearchCondition;
import com.hotelJava.accommodation.application.port.out.persistence.FindAccommodationPort;
import com.hotelJava.accommodation.application.port.out.persistence.SearchAccommodationsPort;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.common.embeddable.Money;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
class SearchAccommodationAdapter implements FindAccommodationPort, SearchAccommodationsPort {
  private final AccommodationRepository repository;
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Accommodation findById(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new BadRequestException(ErrorCode.ACCOMMODATION_NOT_FOUND));
  }

  @Override
  public List<Accommodation> search(
      AccommodationType type,
      String firstLocation,
      String secondLocation,
      String name,
      LocalDate checkInDate,
      LocalDate checkOutDate,
      int guestCount) {
    return repository.findAccommodations(
        type, firstLocation, secondLocation, name, checkInDate, checkOutDate, guestCount);
  }

  @Override
  public List<Accommodation> search(AccommodationSearchCondition condition) {
    List<Accommodation> accommodations =
        jpaQueryFactory
            .selectFrom(accommodation)
            .distinct()
            .where(
                typeEq(condition.type()),
                nameEq(condition.name()),
                firstAddressEq(condition.firstLocation()),
                secondAddressEq(condition.secondLocation()))
            .join(accommodation.rooms, room)
            .where(roomMaxOccupancyGoe(condition.numberOfGuests()), priceLoe(condition.price()))
            .fetch();

    if (condition.checkDate() != null) {
      accommodations =
          accommodations.stream()
              .filter(
                  a ->
                      a.getRooms().stream()
                          .anyMatch(r -> r.isEnoughStockAtCheckDate(condition.checkDate())))
              .collect(Collectors.toList());
    }

    return accommodations;
  }

  private BooleanBuilder typeEq(AccommodationType type) {
    return nullSafeBuilder(() -> accommodation.type.eq(type));
  }

  private BooleanBuilder nameEq(String name) {
    return nullSafeBuilder(() -> accommodation.name.eq(name));
  }

  private BooleanBuilder firstAddressEq(String firstAddress) {
    return nullSafeBuilder(() -> accommodation.address.firstLocation.eq(firstAddress));
  }

  private BooleanBuilder secondAddressEq(String secondAddress) {
    return nullSafeBuilder(() -> accommodation.address.firstLocation.eq(secondAddress));
  }

  private BooleanBuilder priceLoe(Money price) {
    return nullSafeBuilder(() -> room.price.value.loe(price.longValue()));
  }

  private BooleanBuilder roomMaxOccupancyGoe(Integer numberOfGuests) {
    return nullSafeBuilder(() -> room.maxOccupancy.goe(numberOfGuests));
  }

  private static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
    try {
      return new BooleanBuilder(f.get());
    } catch (IllegalArgumentException | NullPointerException e) {
      return new BooleanBuilder();
    }
  }
}
