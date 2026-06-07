package com.khaled_amin.book_social_network.core.logging.audit;

import java.util.List;

public interface BusinessEventLogger {

    // Bootstrap events
    void systemAdminInitialized(String accountCode, String username);


    // Auth events
    void accountRegistered(String accountCode);
    void passwordResetRequested(String accountCode);

    // Account events
    void accountCreated(String accountCode);
    void accountUpdated(String accountCode);
    void accountActivated(String accountCode);
    void accountViewed(String accountCode);
    void accountListed(int page, int size, String sortBy, String direction);
    void accountPasswordReset(String accountCode);
    void accountRoleAssigned(String accountCode, String roleName);
    void accountRolesAssigned(String accountCode, List<String> roleNames);
    void accountRolesReplaced(String accountCode, List<String> roleNames);
    void accountRoleRemoved(String accountCode, String roleName);


    // Email events
    void emailQueued(Long emailId, String template, String recipient);
    void emailSent(Long emailId, String recipient);
    void emailSendFailed(Long emailId, String recipient);
    void emailRetryStarted(Long emailId, int retryCount);
    void emailRetrySucceeded(Long emailId, int retryCount);
    void emailRetryFailed(Long emailId, int retryCount);


    // Verification events
    void verificationTokenGenerated(Long tokenId, String tokenType, String targetActorType, String targetActorCode);
    void verificationTokenVerified(Long tokenId, String tokenType, String targetActorType, String targetActorCode);



    // Role events
    void businessRoleCreated(String roleName);
    void systemRoleCreated(String roleName);
    void roleUpdated(String roleName);
    void roleDeleted(String roleName);
    void roleViewed(String roleName);
    void roleListed();
    void roleCapabilityAssigned(String roleName, String capabilityCode);
    void roleCapabilityRemoved(String roleName, String capabilityCode);


    // Capability events
    void capabilityInitialized(String capabilityCode);
    void capabilityViewed(String capabilityCode);
    void capabilityListed(String module);
}