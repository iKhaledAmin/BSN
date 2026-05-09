package com.khaled_amin.book_social_network.core.policy.core;


import com.khaled_amin.book_social_network.core.policy.exception.PolicyException;
import com.khaled_amin.book_social_network.identity.core.model.Actor;

/**
 * Core contract for all business authorization policies in the system.
 *
 * <p>
 * A {@code BasePolicy} defines a <b>single authorization rule</b> for a specific
 * operation within the application layer. It acts as a gatekeeper that validates
 * whether a given {@link Actor}
 * is allowed to perform an action represented by a {@link BasePolicyContext}.
 * </p>
 *
 * <h3>Role in Architecture</h3>
 * <ul>
 *   <li>Represents the <b>policy abstraction</b> in the application layer</li>
 *   <li>Decouples authorization logic from services and domain models</li>
 *   <li>Enables composable and testable access control rules</li>
 * </ul>
 *
 * <h3>Execution Model</h3>
 * <ul>
 *   <li>{@link #check(BasePolicyContext)} is the main entry point</li>
 *   <li>Implementations must validate the context via {@link #validateContext(BasePolicyContext)}</li>
 *   <li>Authorization decisions must either:
 *     <ul>
 *       <li>Allow execution (no exception thrown)</li>
 *       <li>Deny execution by throwing a {@link PolicyException}</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>No boolean returns — failure is always expressed via exceptions</li>
 *   <li>Exceptions must be explicit and meaningful (operation-specific)</li>
 * </ul>
 *
 * @param <C> the type of {@link BasePolicyContext} required by this policy
 *
 * @see BasePolicyContext
 * @see AbstractPolicy
 */
public interface BasePolicy<C extends BasePolicyContext> {

    /**
     * Executes the policy check for the given context.
     *
     * <p>
     * This method is responsible for enforcing authorization rules.
     * It must either complete silently (allowed) or throw an exception (denied).
     * </p>
     *
     * @param context {@link BasePolicyContext} the policy context containing all required data
     */
    void check(C context);

    /**
     * Validates that the provided context contains all required data
     * for policy evaluation.
     *
     * <p>
     * This method should ensure structural correctness of the context
     * (e.g., required fields are not null), but should not perform
     * authorization logic.
     * </p>
     *
     * @param context {@link BasePolicyContext} the policy context to validate
     */
    void validateContext(C context);
}