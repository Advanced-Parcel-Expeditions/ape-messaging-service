package si.ape.messaging.models.converters;

import si.ape.messaging.lib.Message;
import si.ape.messaging.models.entities.MessageEntity;

/**
 * The MessageConverter class provides static methods for converting between Message DTO and Message Entity objects.
 */
public class MessageConverter {

    /**
     * Converts a MessageEntity object to a Message DTO.
     *
     * @param entity The MessageEntity object to convert.
     * @return The converted Message DTO.
     */
    public static Message toDto(MessageEntity entity) {

        Message dto = new Message();
        dto.setId(entity.getId());
        dto.setConversation(ConversationConverter.toDto(entity.getConversation()));
        dto.setSender(UserConverter.toDto(entity.getSender()));
        dto.setSentAt(entity.getSentAt());
        dto.setContent(entity.getContent());
        return dto;

    }

    /**
     * Converts a Message DTO to a MessageEntity object.
     *
     * @param dto The Message DTO to convert.
     * @return The converted MessageEntity object.
     */
    public static MessageEntity toEntity(Message dto) {

        MessageEntity entity = new MessageEntity();
        entity.setId(dto.getId());
        entity.setConversation(ConversationConverter.toEntity(dto.getConversation()));
        entity.setSender(UserConverter.toEntity(dto.getSender()));
        entity.setSentAt(dto.getSentAt());
        entity.setContent(dto.getContent());
        return entity;

    }

}
