package com.khaled_amin.book_social_network.core.policy.core;


import com.khaled_amin.book_social_network.identity.core.model.Actor;

/**
 * Marker interface representing the input required for policy evaluation.
 *
 * <p>
 * A {@code BasePolicyContext} encapsulates all data needed to evaluate
 * an authorization decision. Concrete implementations are defined per use case
 * and may include:
 * </p>
 *
 * <ul>
 *   <li>The acting {@link Actor}</li>
 *   <li>The target entity (e.g., Account, Role)</li>
 *   <li>Requested changes or commands</li>
 *   <li>Current vs desired state</li>
 * </ul>
 *
 * <h3>Design Principles</h3>
 * <ul>
 *   <li>Immutable (prefer builder pattern)</li>
 *   <li>Use-case specific do not resolve generic contexts (one for use case or module operations is acceptable)</li>
 *   <li>Explicit over implicit (include all required fields)</li>
 * </ul>
 *
 * <h3>Usage</h3>
 * <ul>
 *   <li>Constructed via factory classes (e.g., PolicyContextFactory)</li>
 *   <li>Consumed exclusively by {@link BasePolicy} implementations</li>
 * </ul>
 */

public interface BasePolicyContext {
}
