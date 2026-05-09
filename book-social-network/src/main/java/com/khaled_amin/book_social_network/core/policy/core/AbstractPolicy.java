package com.khaled_amin.book_social_network.core.policy.core;

import com.khaled_amin.book_social_network.core.policy.exception.PolicyException;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;

/**
 * Base implementation of {@link BasePolicy} providing a standardized
 * authorization workflow based on {@link ActorType}.
 *
 * <p>
 * This class implements a <b>template method pattern</b> to enforce a consistent
 * structure for all policy checks across the system.
 * </p>
 *
 * <h3>Execution Flow</h3>
 * <ol>
 *   <li>Validate context (null + structural validation)</li>
 *   <li>Extract actor from context</li>
 *   <li>Route execution based on {@link ActorType}</li>
 *   <li>Delegate to specific handler:
 *     <ul>
 *       <li>{@link #handleAccount(BasePolicyContext)}</li>
 *       <li>{@link #handleAnonymous(BasePolicyContext)}</li>
 *       <li>{@link #handleSystem(BasePolicyContext)}</li>
 *     </ul>
 *   </li>
 * </ol>
 *
 * <h3>Default Behavior</h3>
 * <ul>
 *   <li>All actor types are denied by default</li>
 *   <li>Subclasses must explicitly allow required scenarios</li>
 * </ul>
 *
 * <h3>Extension Guidelines</h3>
 * <ul>
 *   <li><b>Must implement:</b>
 *     <ul>
 *       <li>{@link #extractActor(BasePolicyContext)}</li>
 *       <li>{@link #deny(String)}</li>
 *       <li>{@link #getOperationName()}</li>
 *     </ul>
 *   </li>
 *
 *   <li><b>Should override:</b>
 *     <ul>
 *       <li>One or more of:
 *         <ul>
 *           <li>{@link #handleAccount(BasePolicyContext)}</li>
 *           <li>{@link #handleAnonymous(BasePolicyContext)}</li>
 *           <li>{@link #handleSystem(BasePolicyContext)}</li>
 *         </ul> Otherwise,the target operation deny by default
 *       </li>
 *     </ul>
 *   </li>
 *
 *   <li><b>Optional:</b>
 *     <ul>
 *       <li>{@link #validateContext(BasePolicyContext)} for context integrity</li>
 *     </ul> Depend on the policy decision need data from the context or only actor type needed. (need data then should implement it)
 *   </li>
 * </ul>
 *
 * <h3>Allow vs Deny Semantics</h3>
 * <ul>
 *   <li>{@link #allow()} → no-op (explicit success signal)</li>
 *   <li>{@link #deny(String)} → must throw a domain-specific {@link  PolicyException}</li>
 * </ul>
 *
 * <h3>Actor Type Handling</h3>
 * <ul>
 *   <li>ACCOUNT → handled by {@link #handleAccount(BasePolicyContext)}</li>
 *   <li>ANONYMOUS → handled by {@link #handleAnonymous(BasePolicyContext)}</li>
 *   <li>SYSTEM → handled by {@link #handleSystem(BasePolicyContext)}</li>
 * </ul>
 *
 * <h3>Extending Actor Types</h3>
 * <p>
 * When introducing a new {@code ActorType}:
 * </p>
 * <ul>
 *   <li>Add a corresponding case in {@link #check(BasePolicyContext)}</li>
 *   <li>Introduce a new handler method (e.g., {@code handleX})</li>
 *   <li>Provide a secure default (deny)</li>
 * </ul>
 *
 * @param <C> the type of policy context
 */

public abstract class AbstractPolicy<C extends BasePolicyContext> implements BasePolicy<C> {


    /**
     * Explicit allow signal.
     *
     * <p>
     * This method performs no operation and exists purely for readability
     * and semantic clarity when defining policy rules.
     * </p>
     */
    protected final void allow() {}


    /**
     * Template method for executing the policy check.
     *
     * <p>
     * This method must not be overridden.
     * </p>
     *
     * @param context {@link C}the policy context
     * @throws PolicyException if the policy check fails
     *
     */
    @Override
    public final void check(C context) {

        if (context == null)
            throw PolicyException.invalidPolicyContext()
                    .withDetail("reason", "Context cannot be null")
                    .withDetail("operationName",getOperationName());

        validateContext(context);


        Actor actor = extractActor(context);
        if (actor == null)
            throw PolicyException.invalidPolicyContext()
                    .withDetail("reason", "Actor cannot be null")
                    .withDetail("operationName",getOperationName());



        switch (actor.getType()) {
            case ACCOUNT -> handleAccount(context);
            case ANONYMOUS -> handleAnonymous(context);
            case SYSTEM -> handleSystem(context);
            default -> deny("Unsupported account type");
        }
    }


    /**
     * Extracts the account from the policy context.
     *
     * @param context {@link BasePolicyContext} the policy context
     * @return {@link Actor} the extracted account
     *
     */
    protected abstract Actor extractActor(C context);


    /**
     * Denies the operation with a specific reason.
     *
     * <p>
     * Implementations must throw a domain-specific exception.
     * </p>
     *
     * @param reason {@link String} the reason for the denial
     * @throws PolicyException if the operation is denied
     */
    protected abstract void deny(String reason);


    /**
     * Provides a human-readable name of the operation.
     *
     * <p>
     * Used for debugging and error reporting.
     * </p>
     *
     * @return {@link String} the operation name
     *
     */
    protected abstract String getOperationName();


    /**
     * Default SYSTEM handler — denies access.
     */
    protected void handleSystem(C context) {
        deny("SYSTEM not allowed to " + getOperationName());
    }

    /**
     * Default ACCOUNT handler — denies access.
     */
    protected void handleAccount(C context) {
        deny("ACCOUNT not allowed to " + getOperationName());
    }

    /**
     * Default ANONYMOUS handler — denies access.
     */
    protected void handleAnonymous(C context) {
        deny("ANONYMOUS not allowed to " + getOperationName());
    }


}