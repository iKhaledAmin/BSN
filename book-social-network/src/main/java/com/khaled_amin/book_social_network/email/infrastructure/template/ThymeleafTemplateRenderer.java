package com.khaled_amin.book_social_network.email.infrastructure.template;

import com.khaled_amin.book_social_network.email.application.port.out.TemplateRenderer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ThymeleafTemplateRenderer implements TemplateRenderer {

    private final TemplateEngine templateEngine;

    @Override
    public String render(String templateName, Map<String, Object> variables) {

        Context context = new Context();
        context.setVariables(variables);

        return templateEngine.process(templateName, context);
    }
}