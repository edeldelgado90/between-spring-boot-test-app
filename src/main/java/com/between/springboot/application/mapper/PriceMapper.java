package com.between.springboot.application.mapper;

import com.between.springboot.adapter.out.model.PriceEntity;
import com.between.springboot.domain.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

  Price toModel(PriceEntity price);

  PriceEntity toEntity(Price price);
}
