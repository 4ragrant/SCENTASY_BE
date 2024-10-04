package fouragrant.scentasy.biz.mypage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(String fileName, MultipartFile multipartFile) throws IOException {
        // 파일 확장자 검증
        String originalFileName = multipartFile.getOriginalFilename();
        if (originalFileName != null && !validateImageFileExtension(originalFileName)) {
            throw new CommonException(ErrorCode.UNSUPPORTED_FILE_TYPE);
        }

        // 파일 변환
        File file = convertMultiPartToFile(multipartFile);

        // S3에 업로드
        String newFileName = "profiles/" + fileName + getExtension(originalFileName);
        String uploadImageUrl = uploadFileToS3(newFileName, file);

        // 로컬 파일 삭제
        removeNewFile(file);

        return uploadImageUrl;
    }

    private String uploadFileToS3(String fileName, File file) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일 삭제 성공");
        } else {
            log.info("파일 삭제 실패");
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertFile);

        fos.write(file.getBytes());
        fos.close();

        return convertFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    // 파일 확장자 체크 (.jpg, .png만 허용)
    private boolean validateImageFileExtension(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        return fileExtension.equals("jpg") || fileExtension.equals("png");
    }

    public List<String> getScentImages(List<String> scentNames) {
        List<String> imageUrls = new ArrayList<>();

        for (String scent : scentNames) {
            String imageUrl = amazonS3.getUrl(bucket, "perfumes/" + scent + ".jpg").toString();
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }
}