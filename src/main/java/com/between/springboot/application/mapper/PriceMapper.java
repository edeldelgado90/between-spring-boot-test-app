package com.between.springboot.application.mapper;

import com.between.springboot.adapter.out.model.PriceEntity;
import com.between.springboot.application.mapper.dto.PriceDTO;
import com.between.springboot.domain.price.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

  Price fromPriceEntityToPrice(PriceEntity price);

  PriceEntity fromPriceToPriceEntity(Price price);

  Price fromPriceDTOToPrice(PriceDTO priceDTO);

  PriceDTO fromPriceToPriceDTO(Price price);
}
