//package com.hotelJava.member.adapter.out.persistence;
//
//import com.github.javafaker.Faker;
//import com.hotelJava.accommodation.adapter.persistence.AccommodationRepository;
//import com.hotelJava.accommodation.domain.Accommodation;
//import com.hotelJava.accommodation.domain.AccommodationType;
//import com.hotelJava.common.embeddable.Address;
//import com.hotelJava.inventory.application.port.out.BatchUpdateInventoryPort;
//import com.hotelJava.inventory.domain.Inventory;
//import com.hotelJava.member.domain.Member;
//import com.hotelJava.picture.domain.Picture;
//import com.hotelJava.picture.domain.PictureInfo;
//import com.hotelJava.room.domain.Room;
//import com.hotelJava.room.repository.RoomRepository;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.LinkedList;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class DataLoader implements CommandLineRunner {
//
//  private final MemberRepository memberRepository;
//  private final AccommodationRepository accommodationRepository;
//  private final BatchUpdateInventoryPort batchUpdateInventoryPort;
//  private final RoomRepository roomRepository;
//  private final Faker faker = Faker.instance();
//
//  @Override
//  public void run(String... args) {
//    //        memberRepository.saveAllAndFlush(loadMembers(10000));
//    //    accommodationRepository.saveAllAndFlush(loadAccommodations(1000));
//    batchUpdateInventoryPort.saveAll(loadInventory(180), 10000);
//  }
//
//  private List<Accommodation> loadAccommodations(int dataSize) {
//    List<Accommodation> accommodations = new LinkedList<>();
//    for (int i = 0; i < dataSize; i++) {
//      accommodations.add(getAccommodation(10));
//    }
//    return accommodations;
//  }
//
//  private List<Member> loadMembers(int dataSize) {
//    List<Member> members = new LinkedList<>();
//    for (int i = 0; i < dataSize; i++) {
//      members.add(getMember());
//    }
//    return members;
//  }
//
//  private List<Inventory> loadInventory(int duration) {
//    List<Room> rooms = roomRepository.findAll();
//    List<Inventory> inventories = new LinkedList<>();
//    for (Room room : rooms) {
//      LocalDate.now()
//          .datesUntil(LocalDate.now().plusDays(duration))
//          .forEach(
//              d -> {
//                Inventory inventory = getInventory(d, faker.number().numberBetween(0, 5));
//                inventory.setCreatedDateTime(LocalDateTime.now());
//                inventory.setModifiedDateTime(LocalDateTime.now());
//                inventory.setRoom(room);
//                inventories.add(inventory);
//              });
//    }
//    return inventories;
//  }
//
//  /** domain test fixture * */
//  public Member getMember() {
//    return new Member(
//        faker.internet().emailAddress(),
//        faker.name().name(),
//        faker.internet().password(),
//        faker.phoneNumber().phoneNumber());
//  }
//
//  public Address getAddress() {
//    return Address.builder()
//        .firstLocation(faker.address().streetName())
//        .secondLocation(faker.address().cityName())
//        .fullAddress(faker.address().buildingNumber())
//        .build();
//  }
//
//  public PictureInfo getPictureInfo() {
//    return PictureInfo.builder()
//        .name(faker.file().fileName())
//        .saveFileName(faker.file().fileName())
//        .originFileName(faker.file().fileName())
//        .fileSize(faker.number().randomNumber())
//        .fullPath(faker.letterify("????/????/????"))
//        .extension(faker.file().extension())
//        .build();
//  }
//
//  public Picture getPicture() {
//    return Picture.builder().pictureInfo(getPictureInfo()).build();
//  }
//
//  public Accommodation getAccommodation(int roomNumber) {
//    Picture picture = getPicture();
//    Accommodation accommodation =
//        Accommodation.builder()
//            .name(faker.commerce().productName())
//            .type(faker.options().option(AccommodationType.class))
//            .rating(faker.number().numberBetween(1, 5))
//            .phoneNumber(faker.phoneNumber().phoneNumber())
//            .description(faker.shakespeare().hamletQuote())
//            .address(getAddress())
//            .build();
//
//    // mapping rooms & picture
//    List<Room> rooms = new LinkedList<>();
//    for (int i = 0; i < roomNumber; i++) {
//      int maxOccupancy = faker.number().numberBetween(1, 5);
//      rooms.add(getRoom(maxOccupancy));
//    }
//    accommodation.createAccommodation(rooms, picture);
//
//    return accommodation;
//  }
//
//  public Room getRoom(int maxOccupancy) {
//    Picture picture = getPicture();
//    Room room =
//        Room.builder()
//            .name(faker.name().name())
//            .price(faker.number().numberBetween(50000, 1000000))
//            .maxOccupancy(maxOccupancy)
//            .build();
//
//    room.addPicture(picture);
//
//    return room;
//  }
//
//  private Inventory getInventory(LocalDate date, int quantity) {
//    return new Inventory(date, quantity);
//  }
//}
