package net.springboot.minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import net.springboot.common.util.Utils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MinioService {
    @Autowired
    MinioClient minioClient;
    @Value("${minio.bucket.name}")
    public String defaultBucketName;

    @Value("${minio.default.folder}")
    String defaultBaseFolder;

    @Value("${minio.url}")
    String minioUrl;

    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public boolean uploadFile(String bucket, String name, byte[] content, String contentType) {
        try {
            // Check if the bucket already exists
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucket)
                                .build());

                String policyJson = "{\n" +
                        "    \"Statement\": [\n" +
                        "        {\n" +
                        "            \"Action\": [\n" +
                        "                \"s3:GetBucketLocation\",\n" +
                        "                \"s3:ListBucket\"\n" +
                        "            ],\n" +
                        "            \"Effect\": \"Allow\",\n" +
                        "            \"Principal\": \"*\",\n" +
                        "            \"Resource\": \"arn:aws:s3:::" + bucket + "\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"Action\": \"s3:GetObject\",\n" +
                        "            \"Effect\": \"Allow\",\n" +
                        "            \"Principal\": \"*\",\n" +
                        "            \"Resource\": \"arn:aws:s3:::" + bucket + "/*\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"Version\": \"2012-10-17\"\n" +
                        "}\n";


                minioClient.setBucketPolicy(
                        SetBucketPolicyArgs.builder().bucket(bucket).config(policyJson).build());
            }

//            minioClient.putObject(bucket, name, new ByteArrayInputStream(content), contentType);

            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucket).object(name).stream(
                            new ByteArrayInputStream(content), -1, 10485760)
                            .contentType(contentType)
                            .build());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    private String getPresignedUrl(String bucketName, String objectName) throws IOException, InvalidKeyException, InvalidResponseException,
            InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(10, TimeUnit.MINUTES)
                        .build());
    }

    public String getPresignedUrlReport(String bucketName, String objectName) throws IOException, InvalidKeyException, InvalidResponseException,
            InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(7, TimeUnit.DAYS)
                        .build());
    }


    public String getObjectUrl(String bucketName, String objectName, String serverAddr) {
        try {
//            String returndata = minioClient.getObjectUrl(bucketName.toLowerCase(), objectName);
            String returndata = this.getPresignedUrl(bucketName, objectName);

            String[] data=minioUrl.split("//");
            String[] url=data[1].split("/");
            if(serverAddr.indexOf("119.40.93.30")>-1){
                returndata = returndata.replace(url[0], serverAddr+":8092");
            }
            return returndata;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getObjectUrl(String bucketName, String objectName) {
        try {
            return this.getPresignedUrl(bucketName, objectName);
//            return this.getObjectUrl(bucketName, objectName);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public List<String> listOfObjectName(String bucketName) {
        List<String> objectNameList = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).build());

            for (Result<Item> result : results) {
                Item item = result.get();
                objectNameList.add(item.objectName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return objectNameList;
    }

    public void removeObject(String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(defaultBucketName).object(objectName).build());
        } catch (Exception ex) {
        }
    }

    public byte[] getFile(String bucketName,String key) {
        try {
            InputStream obj =
                    minioClient.getObject(
                            GetObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(defaultBaseFolder + "/" + key)
                                    .build());

//                    minioClient.getObject(bucketName, defaultBaseFolder + "/" + key);
            byte[] content = IOUtils.toByteArray(obj);
            obj.close();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public MinioResponse getByte(MinioRequest minioRequest){
        try {
            if(Utils.isOk(minioRequest) && !Utils.isOk(minioRequest.getKey())){
                return  new MinioResponse(false,"Key Missing");
            }
            byte[] object= getFile(defaultBucketName,minioRequest.getKey());
            return new MinioResponse(false,"Successful",object);
        }catch (Throwable t){
            t.printStackTrace();
            return  new MinioResponse(false,"SERVICE_ERROR");
        }
    }
}
