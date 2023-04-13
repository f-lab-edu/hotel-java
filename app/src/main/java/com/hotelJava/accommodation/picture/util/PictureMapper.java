package com.hotelJava.accommodation.picture.util;

import com.hotelJava.accommodation.picture.domain.Picture;
import com.hotelJava.accommodation.picture.dto.PictureDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PictureMapper {

  Picture toEntity(PictureDto pictureDto);
}
