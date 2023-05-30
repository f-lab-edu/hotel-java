package com.hotelJava;

import com.github.javafaker.Faker;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.domain.Picture;
import com.hotelJava.accommodation.domain.PictureInfo;
import com.hotelJava.common.embeddable.Address;
import com.hotelJava.common.embeddable.Money;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

@Slf4j
public class FakerData {

  private static final Faker faker = Faker.instance();
  private static final List<Address> fakeAddress = readAddressFile();

  public static Address address() {
    return faker.options().nextElement(fakeAddress);
  }

  /** 숙소 관련 더미 데이터 */
  public static String accommodationName() {
    String[] accommodation = {
      "어반 부티크", "삼원 프라자", "CS 프리미어", "에비뉴", "코암", "XYM", "프라나스", "메종", "신라", "롯데", "브릿지", "그랜드 하얏트",
      "유탑", "휘닉스", "라마다", "에코랜드", "히든", "페어필드", "유리엔", "베이튼", "라발스", "아바니", "네스트", "로얄", "엠포리움",
      "체스터톤스", "베스트", "힐튼"
    };
    return faker.options().nextElement(accommodation);
  }

  public static AccommodationType accommodationType() {
    return faker.options().option(AccommodationType.class);
  }

  /** 객실 관련 더미 데이터 */
  public static int maxOccupancy() {
    return faker.number().numberBetween(1, 10);
  }

  public static long price() {
    return faker.number().numberBetween(30000, 1000000);
  }

  public static Money money(long min, long max) {
    return Money.of(faker.number().numberBetween(min, max));
  }

  public static String roomName() {
    String[] room = {"싱글룸", "디럭스룸", "스위트룸", "패밀리룸"};
    return faker.options().nextElement(room);
  }

  /** 회원 관련 더미 데이터 */
  public static String email() {
    return faker.internet().emailAddress();
  }

  public static String password() {
    return faker.internet().password();
  }

  public static String personName() {
    String[] last = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오"};
    String[] name = {
      "가", "강", "건", "경", "고", "나", "남", "노", "다", "대", "도", "려", "미", "민", "바", "빛", "사",
      "상", "서", "석", "선", "수", "승", "아", "연", "영", "예", "유", "이", "인", "은", "재", "정", "제",
      "종", "지", "진", "준", "철", "청", "태", "평", "하", "한", "해", "현", "호", "혜", "향", "환"
    };
    return faker.options().nextElement(last)
        + faker.options().nextElement(name)
        + faker.options().nextElement(name);
  }

  /** 공통 데이터 */
  public static String phone() {
    return faker.regexify("(010)-(\\d{4})-(\\d{4})");
  }

  public static Picture picture() {
    PictureInfo pictureInfo =
        PictureInfo.builder()
            .name(faker.file().fileName())
            .saveFileName(faker.file().fileName())
            .originFileName(faker.file().fileName())
            .fileSize(faker.number().randomNumber())
            .fullPath(faker.letterify("dir/example/test"))
            .extension(faker.file().extension())
            .build();
    return new Picture(pictureInfo);
  }

  /**
   * resources/address_road.csv 로부터 대한민국의 모든 주소를 읽어들이고, 가짜 주소를 만들어낸다.
   *
   * @return 주소
   */
  private static List<Address> readAddressFile() {
    List<Address> address = new ArrayList<>();
    ClassPathResource resource = new ClassPathResource("address_road.csv");

    try {
      Path path = Paths.get(resource.getURI());
      List<String> contents = Files.readAllLines(path);
      List<Address> data =
          contents.stream()
              .map(
                  line -> {
                    String[] elements = line.split(",");
                    int streetAddress = faker.number().numberBetween(1, 2000);
                    return new Address(elements[0], elements[1], streetAddress + elements[2]);
                  })
              .toList();
      address.addAll(data);
    } catch (IOException e) {
      log.error("가짜 주소 생성 오류", e);
    }
    return address;
  }
}
