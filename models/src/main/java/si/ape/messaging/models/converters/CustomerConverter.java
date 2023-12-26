package si.ape.messaging.models.converters;

import si.ape.messaging.lib.Customer;
import si.ape.messaging.models.entities.CustomerEntity;

public class CustomerConverter {

    public static Customer toDto(CustomerEntity entity) {

        Customer dto = new Customer();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setCompanyName(entity.getCompanyName());
        dto.setTelNum(entity.getTelNum());
        dto.setStreet(StreetConverter.toDto(entity.getStreet()));
        dto.setUser(UserConverter.toDto(entity.getUser()));

        return dto;

    }

    public static CustomerEntity toEntity(Customer dto) {

        CustomerEntity entity = new CustomerEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setCompanyName(dto.getCompanyName());
        entity.setTelNum(dto.getTelNum());
        entity.setStreet(StreetConverter.toEntity(dto.getStreet()));
        entity.setUser(UserConverter.toEntity(dto.getUser()));

        return entity;

    }

}
