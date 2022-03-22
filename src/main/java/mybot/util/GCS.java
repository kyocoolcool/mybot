package mybot.util;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

//@Component
public class GCS {
    @Value("gs://${gcs-resource-test-bucket}/test")
    private Resource gcsFile;

    @Autowired
    private Storage storage;

    public void createFile(String fileName, Path path) throws IOException {
//        Bucket bucket = storage.create(BucketInfo.of("fur-seal"));
//        storage.create(
//                BlobInfo.newBuilder("fur-seal", fileName).build(),
//                "file contents".getBytes()
//        );

//        String bucketName = "my-unique-bucket";
//        String fileName = "readme.txt";
//        BlobId blobId = BlobId.of(bucketName, fileName);

//        storage.createFrom(blobInfo, Paths.get(fileName));
//        Blob blob = storage.get("fur-seal", fileName);
        BlobId blobId  =  BlobId.of("fur-seal2", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentEncoding("UTF-8").setContentType("text/plain; charset=utf-8").build();
        storage.createFrom(blobInfo, path);
    }

    public void downloadFile(String fileName, Path path) {
        Blob blob = storage.get("fur-seal2", fileName);
        blob.downloadTo(path);
    }

    public void deleteFile(String fileName) {
//        Bucket bucket = storage.create(BucketInfo.of("fur-seal"));
        BlobId blobId = BlobId.of("fur-seal2", fileName);
        boolean deleted = storage.delete(blobId);
    }
}
