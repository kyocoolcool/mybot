//package mybot.controllers;
//
//import com.google.cloud.storage.*;
//import com.google.rpc.context.AttributeContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.WritableResource;
//import org.springframework.util.StreamUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.nio.charset.Charset;
//import java.nio.file.Path;
//
//@RestController
//public class WebController {
//
//    @Value("gs://${gcs-resource-test-bucket}/test")
//    private Resource gcsFile;
//
//    @Autowired
//    private Storage storage;
//
//    public void createFile(String fileName, Path path) throws IOException {
////        Bucket bucket = storage.create(BucketInfo.of("fur-seal"));
////        storage.create(
////                BlobInfo.newBuilder("fur-seal", fileName).build(),
////                "file contents".getBytes()
////        );
//        Blob blob = storage.get("fur-seal", fileName);
//        storage.createFrom(blob, path);
//    }
//
//    public void downloadFile(String fileName, Path path) {
//        Blob blob = storage.get("fur-seal", fileName);
//        blob.downloadTo(path);
//    }
//
//    public void deleteFile(String fileName) {
////        Bucket bucket = storage.create(BucketInfo.of("fur-seal"));
//        BlobId blobId = BlobId.of("fur-seal", fileName);
//        boolean deleted = storage.delete(blobId);
//    }
//
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String readGcsFile() throws IOException {
//        return StreamUtils.copyToString(
//                this.gcsFile.getInputStream(),
//                Charset.defaultCharset()) + "\n";
//    }
//
//    @RequestMapping(value = "/", method = RequestMethod.POST)
//    String writeGcs(@RequestBody String data) throws IOException {
//        try (OutputStream os = ((WritableResource) this.gcsFile).getOutputStream()) {
//            os.write(data.getBytes());
//        }
//        return "file was updated\n";
//    }
//}
//
