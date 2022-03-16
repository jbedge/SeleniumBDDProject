package com.base;

public class UIElement {


    public UILocatorType getLocatorType() {
        return locatorType;
    }

    private UILocatorType locatorType;
    private String locator;

    public UIElement(UILocatorType locatorType, String locator) {
        this.locatorType = locatorType;
        this.locator = locator;
    }

    public String getLocator() {
        return locator;
    }

    public UIElement setLocator(String tempValue,String runTimeValue){
        this.locator=this.locator.replace(tempValue,runTimeValue);
        return this;
    }

    public UIElement setOriginalLocator(String originalLocator){
        this.locator=originalLocator;
        return this;
    }

}

