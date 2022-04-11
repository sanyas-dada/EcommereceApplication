package com.sanyasdada.ecommerceapplication.models;

public class AddressModel {
    private String userAddress;
    private boolean isSelected;

    public AddressModel() {
    }

    public AddressModel(String userAddress, boolean isSelected) {
        this.userAddress = userAddress;
        this.isSelected = isSelected;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
