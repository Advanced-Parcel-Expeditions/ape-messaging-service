package si.ape.messaging.models.converters;

import si.ape.messaging.lib.Conversation;
import si.ape.messaging.models.entities.ConversationEntity;

/**
 * The ConversationConverter class provides static methods for converting between Conversation DTO and Conversation
 * Entity objects.
 */
public class ConversationConverter {

    /**
     * Converts a ConversationEntity object to a Conversation DTO.
     *
     * @param entity The ConversationEntity object to convert.
     * @return The converted Conversation DTO.
     */
    public static Conversation toDto(ConversationEntity entity) {

        Conversation dto = new Conversation();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;

    }

    /**
     * Converts a Conversation DTO to a ConversationEntity object.
     *
     * @param dto The Conversation DTO to convert.
     * @return The converted ConversationEntity object.
     */
    public static ConversationEntity toEntity(Conversation dto) {

        ConversationEntity entity = new ConversationEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;

    }

}
