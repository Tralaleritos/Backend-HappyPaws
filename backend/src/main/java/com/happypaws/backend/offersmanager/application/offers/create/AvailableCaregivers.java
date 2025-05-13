package com.happypaws.backend.offersmanager.application.offers.create;

import com.happypaws.backend.offersmanager.domain.offers.Location;

import java.util.List;

public interface AvailableCaregivers {
    List<Long> getAvailableCaregivers(Location location);
}
