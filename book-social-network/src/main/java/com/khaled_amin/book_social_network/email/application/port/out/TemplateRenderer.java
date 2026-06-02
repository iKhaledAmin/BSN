package com.khaled_amin.book_social_network.email.application.port.out;

import com.khaled_amin.book_social_network.email.exception.EmailTechnicalException;
import com.khaled_amin.book_social_network.email.infrastructure.config.EmailProperties;

import java.util.Map;

/**
 * Outbound port for template rendering.
 *
 * <p>
 * Defines the application boundary for transforming a template identifier
 * and a set of variables into a fully rendered textual representation.
 * </p>
 *
 * <p>
 * This interface represents a <b>port</b> in Clean Architecture and abstracts
 * away any specific template engine (e.g., Thymeleaf, FreeMarker).
 * Implementations are responsible for resolving templates and producing
 * the final content.
 * </p>
 *
 * <h3>Responsibilities</h3>
 * <ul>
 *   <li>Resolve templates based on a logical name or identifier</li>
 *   <li>Apply provided variables to generateToken dynamic content</li>
 *   <li>Return a fully rendered, ready-to-use textual output</li>
 * </ul>
 *
 * <h3>Usage</h3>
 * <ul>
 *   <li>Invoked by application services as part of content generation workflows</li>
 *   <li>Typically used in email, notification, or document generation use cases</li>
 *   <li>Must be consumed via dependency inversion</li>
 * </ul>
 *
 * <h3>Execution Semantics</h3>
 * <ul>
 *   <li>Rendering is deterministic for a given template and variable set</li>
 *   <li>No observable side effects should occur</li>
 * </ul>
 *
 * <h3>Implementation Notes</h3>
 * <ul>
 *   <li>Implementations must be stateless and thread-safe</li>
 *   <li>Input variables must not be mutated</li>
 *   <li>Template resolution strategy is delegated to the underlying engine</li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Failures must be propagated as runtime exceptions</li>
 *   <li>No silent failures are allowed</li>
 *   <li>Typical failure scenarios include missing templates or invalid variables</li>
 * </ul>
 *
 * @see EmailProperties
 */
public interface TemplateRenderer {

    /**
     * Renders a template using the provided variables.
     *
     * <h3>Contract</h3>
     * <ul>
     *   <li>Resolves the specified template</li>
     *   <li>Applies the provided variables to generateToken the final content</li>
     *   <li>Returns a fully rendered string suitable for downstream usage (e.g., email body)</li>
     * </ul>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>{@code templateName} must not be {@code null} or blank</li>
     *   <li>{@code variables} must not be {@code null} (maybe empty)</li>
     *   <li>Variable keys must align with template placeholders</li>
     * </ul>
     *
     * <h3>Side Effects</h3>
     * <ul>
     *   <li>None</li>
     * </ul>
     *
     * <h3>Failure Semantics</h3>
     * <ul>
     * <li>Failures during template resolution or rendering are propagated as runtime exceptions</li>
     * <li>Typical failure scenarios include missing templates, invalid syntax, or unresolved variables</li>
     * <li>No silent failures are allowed</li>
     * <li>Exception translation and handling are the responsibility of the calling application layer</li>
     * </ul>
     *
     * <h3>Failure Handling</h3>
     * <ul>
     *   <li>Throws a runtime exception if rendering fails</li>
     *   <li>Failure scenarios include unresolved templates or invalid input data</li>
     * </ul>
     *
     * @param templateName {@link String} the logical name or identifier of the template
     * @param variables {@link Map(String, Object)} key-value pairs used for template population
     * @return fully rendered template content
     * @throws EmailTechnicalException if rendering fails
     */
    String render(String templateName, Map<String, Object> variables);
}