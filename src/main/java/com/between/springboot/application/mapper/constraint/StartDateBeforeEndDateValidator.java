package com.between.springboot.application.mapper.constraint;

import com.between.springboot.application.mapper.dto.PriceDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StartDateBeforeEndDateValidator
    implements ConstraintValidator<StartDateBeforeEndDate, PriceDTO> {

  @Override
  public boolean isValid(PriceDTO price, ConstraintValidatorContext context) {
    return price.getStartDate().isBefore(price.getEndDate());
  }
}
