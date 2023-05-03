package com.hotelJava.picture.util;

import com.hotelJava.picture.domain.Picture;
import com.hotelJava.picture.dto.PictureDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PictureMapper {

  Picture toEntity(PictureDto pictureDto);
}
