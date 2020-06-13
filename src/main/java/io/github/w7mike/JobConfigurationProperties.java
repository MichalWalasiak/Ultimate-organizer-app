package io.github.w7mike;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("job")
public class JobConfigurationProperties {

    Template template;

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(final Template template) {
        this.template = template;
    }

    public static class Template{

        private boolean allowMultipleJobs;

        public boolean isAllowMultipleJobs() {
            return allowMultipleJobs;
        }

        public void setAllowMultipleJobs(final boolean allowMultipleJobs) {
            this.allowMultipleJobs = allowMultipleJobs;
        }
    }
}
