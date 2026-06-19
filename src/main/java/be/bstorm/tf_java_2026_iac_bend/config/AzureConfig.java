package be.bstorm.tf_java_2026_iac_bend.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class AzureConfig {

    private static final Logger logger = LoggerFactory.getLogger(AzureConfig.class);

    @Value("${azure.storage.connection-string:}")
    private String connectionString;

    @Bean
    public BlobServiceClient blobServiceClient() {
        if (connectionString == null || connectionString.isBlank()) {
            logger.warn("BLOB_CONNECTION_STRING not configured! Blob operations will fail.");
            throw new IllegalArgumentException("azure.storage.connection-string must be configured");
        }
        logger.info("Initializing BlobServiceClient with connection string");
        return new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }
}

