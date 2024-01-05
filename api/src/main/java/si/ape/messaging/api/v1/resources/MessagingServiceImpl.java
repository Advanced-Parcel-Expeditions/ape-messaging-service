package si.ape.messaging.api.v1.resources;

import si.ape.messaging.api.v1.MessagingServiceOuterClass.*;
import si.ape.messaging.api.v1.MessagingServiceGrpc;
import si.ape.messaging.services.beans.MessagingBean;

import com.kumuluz.ee.grpc.annotations.GrpcService;

import io.grpc.stub.StreamObserver;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The MessagingServiceImpl class is an implementation of the MessagingServiceGrpc.MessagingServiceImplBase class. It
 * provides the implementations of the gRPC endpoints defined in the MessagingService.proto file. It uses the MessagingBean
 * class to handle the business logic.
 */
@GrpcService()
public class MessagingServiceImpl extends MessagingServiceGrpc.MessagingServiceImplBase {

    /** The service's internal business logic bean. */
    @Inject
    private MessagingBean messagingBean;

    /**
     * Handles the request regarding the sending of a message. It attempts to add the message to the database and returns
     * the ID of the message that was added.
     *
     * @param request          The request containing the message to be sent.
     * @param responseObserver The response observer.
     */
    @Override
    public void sendMessage(SendMessageRequest request, StreamObserver<SendMessageResponse> responseObserver) {

        messagingBean = CDI.current().select(MessagingBean.class).get();

        try {
            Integer conversationId = Integer.parseInt(request.getConversationId());
            Integer senderId = Integer.parseInt(request.getSenderId());
            //Instant sentAt = Instant.parse(request.getSentAt());
            String content = request.getMessage();

            Integer messageId = messagingBean.addMessage(conversationId, senderId, Instant.now(), content);

            if (messageId == null) {
                responseObserver.onError(new Exception("Message could not be sent."));
                return;
            }

            SendMessageResponse response = SendMessageResponse.newBuilder()
                    .setMessageId(messageId.toString())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NumberFormatException | DateTimeParseException e) {
            responseObserver.onError(e);
        }

    }

    /**
     * Handles the request regarding the loading of a conversation. It attempts to load the conversation from the database
     * and returns the messages that were found.
     *
     * @param request          The request containing the ID of the conversation to be loaded.
     * @param responseObserver The response observer.
     */
    @Override
    public void loadConversation(LoadConversationRequest request, StreamObserver<LoadConversationResponse> responseObserver) {

        messagingBean = CDI.current().select(MessagingBean.class).get();

        try {
            Integer conversationId = Integer.parseInt(request.getConversationId());
            List<si.ape.messaging.lib.Message> messagesList = messagingBean.getMessages(conversationId);

            if (messagesList == null) {
                responseObserver.onError(new Exception("Conversation could not be loaded."));
                return;
            }

            // Convert DTO messages to protobuf messages.
            ArrayList<Message> messages = messagesList.stream().map(message -> {
                        return Message.newBuilder()
                                .setMessageId(message.getId().toString())
                                .setSenderId(message.getSender().getId().toString())
                                .setSentAt(message.getSentAt().toString())
                                .setMessage(message.getContent())
                                .build();
                    }).collect(Collectors.toCollection(ArrayList::new));

            LoadConversationResponse response = LoadConversationResponse.newBuilder()
                    .addAllMessages(messages)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NumberFormatException e) {
            responseObserver.onError(e);
        }

    }

    /**
     * Handles the request regarding the adding of a user to a conversation. It attempts to add the user to the database
     * and returns the ID of the conversation that the user was added to.
     *
     * @param request          The request containing the ID of the conversation and the ID of the user to be added.
     * @param responseObserver The response observer.
     */
    @Override
    public void addUserToConversation(AddUserToConversationRequest request, StreamObserver<AddUserToConversationResponse> responseObserver) {

        messagingBean = CDI.current().select(MessagingBean.class).get();

        try {
            Integer conversationId = Integer.parseInt(request.getConversationId());
            Integer userId = Integer.parseInt(request.getUserId());

            Integer successConversationId = messagingBean.addUserToConversation(conversationId, userId);

            if (successConversationId == null) {
                responseObserver.onError(new Exception("User could not be added to conversation."));
                return;
            }

            AddUserToConversationResponse response = AddUserToConversationResponse.newBuilder()
                    .setConversationId(conversationId.toString())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NumberFormatException e) {
            responseObserver.onError(e);
        }
    }

    /**
     * Handles the request regarding the removal of a user from a conversation. It attempts to remove the user from the
     * database and returns the ID of the conversation that the user was removed from.
     *
     * @param request          The request containing the ID of the conversation and the ID of the user to be removed.
     * @param responseObserver The response observer.
     */
    @Override
    public void removeUserFromConversation(RemoveUserFromConversationRequest request, StreamObserver<RemoveUserFromConversationResponse> responseObserver) {

        messagingBean = CDI.current().select(MessagingBean.class).get();

        try {
            Integer conversationId = Integer.parseInt(request.getConversationId());
            Integer userId = Integer.parseInt(request.getUserId());

            Integer successUserId = messagingBean.removeUserFromConversation(conversationId, userId);

            if (successUserId == null) {
                responseObserver.onError(new Exception("User could not be removed from conversation."));
                return;
            }

            RemoveUserFromConversationResponse response = RemoveUserFromConversationResponse.newBuilder()
                    .setConversationId(conversationId.toString())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NumberFormatException e) {
            responseObserver.onError(e);
        }

    }

    /**
     * Handles the request regarding the creation of a conversation. It attempts to create the conversation in the
     * database and returns the ID of the conversation that was created.
     *
     * @param request          The request containing the IDs of the users that are to be added to the conversation.
     * @param responseObserver The response observer.
     */
    @Override
    public void createConversation(CreateConversationRequest request, StreamObserver<CreateConversationResponse> responseObserver) {

        messagingBean = CDI.current().select(MessagingBean.class).get();

        try {
            ArrayList<Integer> userIds = request.getUserIdsList()
                    .stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(ArrayList::new));

            Integer conversationId = messagingBean.createConversation(userIds);

            if (conversationId == null) {
                responseObserver.onError(new Exception("Conversation could not be created."));
                return;
            }

            CreateConversationResponse response = CreateConversationResponse.newBuilder()
                    .setConversationId(conversationId.toString())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NumberFormatException e) {
            responseObserver.onError(e);
        }

    }

    /**
     * Handles the request regarding the loading of conversations. It attempts to load the conversations from the
     * database and returns the IDs of the conversations that were found.
     *
     * @param request          The request containing the ID of the user whose conversations are to be loaded.
     * @param responseObserver The response observer.
     */
    @Override
    public void loadConversations(LoadConversationsRequest request, StreamObserver<LoadConversationsResponse> responseObserver) {

        messagingBean = CDI.current().select(MessagingBean.class).get();

        try {
            Integer userId = Integer.parseInt(request.getUserId());
            List<si.ape.messaging.lib.Conversation> conversationsList = messagingBean.getConversations(userId);

            if (conversationsList == null) {
                responseObserver.onError(new Exception("Conversations could not be loaded."));
                return;
            }

            // Convert DTO conversations to protobuf conversations.
            ArrayList<Conversation> conversations = conversationsList.stream().map(conversation -> {
                return Conversation.newBuilder()
                        .setConversationId(conversation.getId().toString())
                        .build();
            }).collect(Collectors.toCollection(ArrayList::new));

            LoadConversationsResponse response = LoadConversationsResponse.newBuilder()
                    .addAllConversations(conversations)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NumberFormatException e) {
            responseObserver.onError(e);
        }

    }

}
