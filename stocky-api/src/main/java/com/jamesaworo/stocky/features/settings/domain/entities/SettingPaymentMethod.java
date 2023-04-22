package com.jamesaworo.stocky.features.settings.domain.entities;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.jamesaworo.stocky.core.constants.DatabaseTable.SETTING_PAYMENT_METHOD;

@Entity
@Table(name = SETTING_PAYMENT_METHOD)
@Data
public class SettingPaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private String title;
    private String description;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public SettingPaymentMethod(String title) {
        this.title = title;
    }

    @PrePersist()
    void prePersist() {
        this.setCreatedAt(LocalDateTime.now());
    }

}