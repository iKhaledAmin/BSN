package com.khaled_amin.book_social_network.core.logging.audit;

import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.core.logging.core.LogCategory;
import com.khaled_amin.book_social_network.core.logging.core.EventType;
import com.khaled_amin.book_social_network.core.logging.event.BusinessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j(topic = "BusinessEventLogger")
@Component
public class Slf4jBusinessEventLogger implements BusinessEventLogger {

// --------------------- Bootstrap events --------------------- //

    @Override
    public void systemAdminInitialized(String accountCode, String username) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.BOOTSTRAP)
                .addKeyValue("event", BusinessEvent.SYSTEM_ADMIN_INITIALIZED)
                .addKeyValue("accountCode", accountCode)
                .addKeyValue("username", username)
                .log("system admin initialized");
    }

// --------------------- End Bootstrap events --------------------- //


// --------------------- Auth events --------------------- //

    @Override
    public void accountRegistered(String accountCode) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.AUTH)
                .addKeyValue("event", BusinessEvent.ACCOUNT_REGISTERED)
                .addKeyValue("accountCode", accountCode)
                .log("account registered");
    }

    @Override
    public void passwordResetRequested(String accountCode) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.AUTH)
                .addKeyValue("event", BusinessEvent.PASSWORD_RESET_REQUESTED)
                .addKeyValue("accountCode", accountCode)
                .log("password reset requested");
    }

    // --------------------- End Auth events --------------------- //
    // --------------------- Account events --------------------- //
    @Override
    public void accountCreated(String accountCode) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ACCOUNT)
                .addKeyValue("event", BusinessEvent.ACCOUNT_CREATED)
                .addKeyValue("accountCode", accountCode)
                .log("account created");

    }

    @Override
    public void accountUpdated(String accountCode) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ACCOUNT)
                .addKeyValue("event", BusinessEvent.ACCOUNT_UPDATED)
                .addKeyValue("accountCode", accountCode)
                .log("account updated");

    }

    @Override
    public void accountActivated(String accountCode) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ACCOUNT)
                .addKeyValue("event", BusinessEvent.ACCOUNT_ACTIVATED)
                .addKeyValue("accountCode", accountCode)
                .log("account activated");

    }

    @Override
    public void accountViewed(String accountCode) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ACCOUNT)
                .addKeyValue("event", BusinessEvent.ACCOUNT_VIEWED)
                .addKeyValue("accountCode", accountCode)
                .log("account viewed");
    }

    @Override
    public void accountListed(int page, int size, String sortBy, String direction) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ACCOUNT)
                .addKeyValue("event", BusinessEvent.ACCOUNT_LISTED)
                .addKeyValue("page", page)
                .addKeyValue("size", size)
                .addKeyValue("sortBy", sortBy)
                .addKeyValue("direction", direction)
                .log("accounts listed");

    }

    @Override
    public void accountPasswordReset(String accountCode) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ACCOUNT)
                .addKeyValue("event", BusinessEvent.ACCOUNT_PASSWORD_RESET)
                .addKeyValue("accountCode", accountCode)
                .log("account password reset");

    }

    @Override
    public void accountRoleAssigned(String accountCode, String roleName) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ACCOUNT)
                .addKeyValue("event", BusinessEvent.ACCOUNT_ROLE_ASSIGNED)
                .addKeyValue("accountCode", accountCode)
                .addKeyValue("roleName", roleName)
                .log("account role assigned");

    }

    @Override
    public void accountRolesAssigned(String accountCode, List<String> roleNames) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ACCOUNT)
                .addKeyValue("event", BusinessEvent.ACCOUNT_ROLES_ASSIGNED)
                .addKeyValue("accountCode", accountCode)
                .addKeyValue("roleNames", roleNames)
                .log("account roles assigned");

    }

    @Override
    public void accountRolesReplaced(String accountCode, List<String> roleNames) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ACCOUNT)
                .addKeyValue("event", BusinessEvent.ACCOUNT_ROLES_REPLACED)
                .addKeyValue("accountCode", accountCode)
                .addKeyValue("roleNames", roleNames)
                .log("account roles replaced");

    }

    @Override
    public void accountRoleRemoved(String accountCode, String roleName) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ACCOUNT)
                .addKeyValue("event", BusinessEvent.ACCOUNT_ROLE_REMOVED)
                .addKeyValue("accountCode", accountCode)
                .addKeyValue("roleName", roleName)
                .log("account role removed");

    }

    // --------------------- End Account events --------------------- //


    // ---------------------- Email events ----------------------- //
    @Override
    public void emailQueued(Long emailId, String template, String recipient) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.EMAIL)
                .addKeyValue("event", BusinessEvent.EMAIL_QUEUED)
                .addKeyValue("emailId", emailId)
                .addKeyValue("template", template)
                .addKeyValue("recipient", recipient)
                .log("email queued");
    }

    @Override
    public void emailSent(Long emailId, String recipient) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.EMAIL)
                .addKeyValue("event", BusinessEvent.EMAIL_SENT)
                .addKeyValue("emailId", emailId)
                .addKeyValue("recipient", recipient)
                .log("email sent");
    }

    @Override
    public void emailSendFailed(Long emailId, String recipient) {

        log.atWarn()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.EMAIL)
                .addKeyValue("event", BusinessEvent.EMAIL_SEND_FAILED)
                .addKeyValue("emailId", emailId)
                .addKeyValue("recipient", recipient)
                .log("email send failed");
    }

    @Override
    public void emailRetryStarted(Long emailId, int retryCount) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.EMAIL)
                .addKeyValue("event", BusinessEvent.EMAIL_RETRY_STARTED)
                .addKeyValue("emailId", emailId)
                .addKeyValue("retryCount", retryCount)
                .log("email retry started");
    }

    @Override
    public void emailRetrySucceeded(Long emailId, int retryCount) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.EMAIL)
                .addKeyValue("event", BusinessEvent.EMAIL_RETRY_SUCCEEDED)
                .addKeyValue("emailId", emailId)
                .addKeyValue("retryCount", retryCount)
                .log("email retry succeeded");
    }

    @Override
    public void emailRetryFailed(Long emailId, int retryCount) {

        log.atWarn()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.EMAIL)
                .addKeyValue("event", BusinessEvent.EMAIL_RETRY_FAILED)
                .addKeyValue("emailId", emailId)
                .addKeyValue("retryCount", retryCount)
                .log("email retry failed");
    }

// ---------------------- End Email events ----------------------- //

// --------------------- Verification events --------------------- //

    @Override
    public void verificationTokenGenerated(Long tokenId, String tokenType, String targetActorType, String targetActorCode) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.VERIFICATION)
                .addKeyValue("event", BusinessEvent.VERIFICATION_TOKEN_GENERATED)
                .addKeyValue("tokenId", tokenId)
                .addKeyValue("tokenType", tokenType)
                .addKeyValue("targetActorType", targetActorType)
                .addKeyValue("targetActorCode", targetActorCode)
                .log("verification token generated");
    }

    @Override
    public void verificationTokenVerified(Long tokenId, String tokenType, String targetActorType, String targetActorCode) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.VERIFICATION)
                .addKeyValue("event", BusinessEvent.VERIFICATION_TOKEN_VERIFIED)
                .addKeyValue("tokenId", tokenId)
                .addKeyValue("tokenType", tokenType)
                .addKeyValue("targetActorType", targetActorType)
                .addKeyValue("targetActorCode", targetActorCode)
                .log("verification token verified");
    }

// --------------------- End Verification events --------------------- //


// --------------------- Role events --------------------- //

    @Override
    public void businessRoleCreated(String roleName) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ROLE)
                .addKeyValue("event", BusinessEvent.BUSINESS_ROLE_CREATED)
                .addKeyValue("roleName", roleName)
                .log("business role created");
    }

    @Override
    public void systemRoleCreated(String roleName) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ROLE)
                .addKeyValue("event", BusinessEvent.SYSTEM_ROLE_CREATED)
                .addKeyValue("roleName", roleName)
                .log("system role created");
    }

    @Override
    public void roleUpdated(String roleName) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ROLE)
                .addKeyValue("event", BusinessEvent.ROLE_UPDATED)
                .addKeyValue("roleName", roleName)
                .log("role updated");
    }

    @Override
    public void roleDeleted(String roleName) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ROLE)
                .addKeyValue("event", BusinessEvent.ROLE_DELETED)
                .addKeyValue("roleName", roleName)
                .log("role deleted");
    }

    @Override
    public void roleViewed(String roleName) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ROLE)
                .addKeyValue("event", BusinessEvent.ROLE_VIEWED)
                .addKeyValue("roleName", roleName)
                .log("role viewed");
    }

    @Override
    public void roleListed() {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ROLE)
                .addKeyValue("event", BusinessEvent.ROLE_LISTED)
                .log("roles listed");
    }

    @Override
    public void roleCapabilityAssigned(String roleName, String capabilityCode) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ROLE)
                .addKeyValue("event", BusinessEvent.ROLE_CAPABILITY_ASSIGNED)
                .addKeyValue("roleName", roleName)
                .addKeyValue("capabilityCode", capabilityCode)
                .log("role capability assigned");
    }

    @Override
    public void roleCapabilityRemoved(String roleName, String capabilityCode) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.ROLE)
                .addKeyValue("event", BusinessEvent.ROLE_CAPABILITY_REMOVED)
                .addKeyValue("roleName", roleName)
                .addKeyValue("capabilityCode", capabilityCode)
                .log("role capability removed");
    }

// --------------------- End Role events --------------------- //


// --------------------- Capability events --------------------- //

    @Override
    public void capabilityInitialized(String capabilityCode) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.CAPABILITY)
                .addKeyValue("event", BusinessEvent.CAPABILITY_INITIALIZED)
                .addKeyValue("capabilityCode", capabilityCode)
                .log("capability initialized");
    }

    @Override
    public void capabilityViewed(String capabilityCode) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.CAPABILITY)
                .addKeyValue("event", BusinessEvent.CAPABILITY_VIEWED)
                .addKeyValue("capabilityCode", capabilityCode)
                .log("capability viewed");
    }

    @Override
    public void capabilityListed(String module) {

        log.atInfo()
                .addKeyValue("category", LogCategory.EVENT)
                .addKeyValue("type", EventType.BUSINESS)
                .addKeyValue("domain", SystemDomain.CAPABILITY)
                .addKeyValue("event", BusinessEvent.CAPABILITY_LISTED)
                .addKeyValue("module", module)
                .log("capabilities listed");
    }

// --------------------- End Capability events --------------------- //

}