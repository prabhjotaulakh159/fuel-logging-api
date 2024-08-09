package dev.prabhjotaulakh.fuel.api.data;

public class LocationResponse {
    private Integer locationId;
    private String country;
    private String state;
    private String postalCode;
    private Integer doorNumber;

    public LocationResponse() {
    }

    public LocationResponse(Integer locationId, String country, String state, String postalCode, Integer doorNumber) {
        this.locationId = locationId;
        this.country = country;
        this.state = state;
        this.postalCode = postalCode;
        this.doorNumber = doorNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(Integer doorNumber) {
        this.doorNumber = doorNumber;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }
}

