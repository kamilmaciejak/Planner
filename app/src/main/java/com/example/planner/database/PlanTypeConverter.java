package com.example.planner.database;

import org.greenrobot.greendao.converter.PropertyConverter;

class PlanTypeConverter implements PropertyConverter<PlanType, String> {

    @Override
    public PlanType convertToEntityProperty(String databaseValue) {
        return PlanType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(PlanType entityProperty) {
        return entityProperty.name();
    }
}
