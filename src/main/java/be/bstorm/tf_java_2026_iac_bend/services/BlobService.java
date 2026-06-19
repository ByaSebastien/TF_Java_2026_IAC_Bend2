package be.bstorm.tf_java_2026_iac_bend.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class BlobService {

    private final BlobServiceClient blobServiceClient;
    private final String containerName = "images";

    public BlobService(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    /**
     * Upload un fichier vers le Blob Storage
     */
    public String uploadBlob(String blobName, MultipartFile file) throws IOException {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        blobClient.upload(file.getInputStream(), file.getSize(), true);
        blobClient.setHttpHeaders(new BlobHttpHeaders()
                .setContentType(file.getContentType() != null ? file.getContentType() : "application/octet-stream"));

        return blobClient.getBlobUrl();
    }

    /**
     * Télécharge un fichier depuis le Blob Storage
     */
    public byte[] downloadBlob(String blobName) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        
        return blobClient.downloadContent().toBytes();
    }

    /**
     * Supprime un blob
     */
    public void deleteBlob(String blobName) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        
        blobClient.delete();
    }

    /**
     * Retourne l'URL du blob
     */
    public String getBlobUrl(String blobName) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        return blobClient.getBlobUrl();
    }

    public String getBlobContentType(String blobName) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        String contentType = blobClient.getProperties().getContentType();
        return contentType != null && !contentType.isBlank() ? contentType : "application/octet-stream";
    }
}
