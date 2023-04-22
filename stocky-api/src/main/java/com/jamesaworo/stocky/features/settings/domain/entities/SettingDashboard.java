package com.jamesaworo.stocky.features.settings.domain.entities;

import com.jamesaworo.stocky.core.enumconstants.SettingField;

import javax.persistence.Entity;
import javax.persistence.Table;

import static com.jamesaworo.stocky.core.constants.DatabaseTable.SETTING_DASHBOARD;

@Entity
@Table(name = SETTING_DASHBOARD)
public class SettingDashboard extends Setting {

    public SettingDashboard(String key, String value, SettingField field, String[] options) {
        super(key, value, field, options);
    }
}