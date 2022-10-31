package com.memories.app.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.memories.app.commun.CoreConstant.Validation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LocationDto extends GenericDto{
	
	
	@Max(value = (long) Validation.LOCATION_LONGITUDE_MAX)
	@Min(value = (long) Validation.LOCATION_LONGITUDE_MIN)
	private double longitude;
	@Max(value = (long) Validation.LOCATION_LATITUDE_MAX)
	@Min(value = (long) Validation.LOCATION_LATITUDE_MIN)
	private double latitude;
}
