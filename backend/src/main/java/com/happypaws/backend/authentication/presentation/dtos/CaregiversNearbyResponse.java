package com.happypaws.backend.authentication.presentation.dtos;

public class CaregiversNearbyResponse {
    private final Long caregiverId;
    private final String userName;
    private final String imgUrl;
    private final double latitude;
    private final double longitude;
    private final String type = "CAREGIVER_AVAILABLE";

    public CaregiversNearbyResponse(Long caregiverId, String userName, String imgUrl,
                                    double latitude, double longitude) {
        this.caregiverId = caregiverId;
        this.userName = userName;
        this.imgUrl = imgUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public Long getCaregiverId() { return caregiverId; }
    public String getUserName() { return userName; }
    public String getImgUrl() { return imgUrl; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getType() { return type; }
}
