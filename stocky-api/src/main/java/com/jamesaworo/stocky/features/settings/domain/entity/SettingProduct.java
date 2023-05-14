package com.jamesaworo.stocky.features.settings.domain.entity;


import com.jamesaworo.stocky.core.constants.enums.SettingField;

import javax.persistence.Entity;
import javax.persistence.Table;

import static com.jamesaworo.stocky.core.constants.Table.SETTING_PRODUCT;

@Entity
@Table(name = SETTING_PRODUCT)
public class SettingProduct extends Setting {

    public SettingProduct() {
    }

    public SettingProduct(String key, String value, SettingField field, String[] options, String title) {
        super(key, value, field, options, title);
    }

    public SettingProduct(String key, String value, SettingField field, String[] options, String title, String hint) {
        super(key, value, field, options, title, hint);
    }
}