package com.jamesaworo.stocky.features.settings.data.interactor.setting_backup_restore;


import com.jamesaworo.stocky.core.annotations.Interactor;
import com.jamesaworo.stocky.core.mapper.Mapper;
import com.jamesaworo.stocky.features.settings.data.dto.SettingDto;
import com.jamesaworo.stocky.features.settings.data.usecases_impl.SettingBackupUsecase;
import com.jamesaworo.stocky.features.settings.domain.entity.SettingBackUpRestore;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Aworo James
 * @since 4/20/23
 */
@Interactor
@RequiredArgsConstructor
public class SettingBackupInteractor implements ISettingBackupInteractor, Mapper<SettingDto, SettingBackUpRestore> {

    private final SettingBackupUsecase usecase;

    @Override
    public ResponseEntity<SettingDto> get(String key) {
        Optional<SettingBackUpRestore> optional = this.usecase.get(key);
        return optional.map(setting -> ok(this.toRequest(setting))).orElse(of(empty()));
    }

    @Override
    public ResponseEntity<List<SettingDto>> getAll() {
        var settings = this.usecase.all();
        var collection = settings.stream().map(this::toRequest).collect(Collectors.toList());
        return ok(collection);
    }

    @Override
    public ResponseEntity<Boolean> update(SettingDto dto) {
        return ok(this.usecase.update(dto.getSettingKey(), dto.getSettingValue()));
    }

    @Override
    public ResponseEntity<Boolean> updateAll(List<SettingDto> list) {
        var settings = list.stream().map(this::toModel).collect(Collectors.toList());
        return ok(this.usecase.updateMany(settings));
    }

    public SettingDto toRequest(SettingBackUpRestore model) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(model, SettingDto.class);
    }

    public SettingBackUpRestore toModel(SettingDto request) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(request, SettingBackUpRestore.class);
    }
}