package si.ape.messaging.models.converters;

import si.ape.messaging.lib.ParcelStatus;
import si.ape.messaging.models.entities.ParcelStatusEntity;

public class ParcelStatusConverter {

    public static ParcelStatus toDto(ParcelStatusEntity entity) {

        ParcelStatus dto = new ParcelStatus();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;

    }

    public static ParcelStatusEntity toEntity(ParcelStatus dto) {

        ParcelStatusEntity entity = new ParcelStatusEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;

    }

}




