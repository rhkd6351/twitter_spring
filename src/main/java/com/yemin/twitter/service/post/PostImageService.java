package com.yemin.twitter.service.post;

import com.yemin.twitter.domain.FileInfo;
import com.yemin.twitter.domain.PostImageVO;
import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.repository.PostImageRepository;
import com.yemin.twitter.util.FileUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostImageService {
    private static final String UPLOADPATH = "/img";

    PostImageRepository postImageRepository;

    FileUtil fileUtil;
    int maxFileSize;


    public PostImageService(PostImageRepository postImageRepository, FileUtil fileUtil, @Value("${static.max-file-size}") int maxFileSize) {
        this.postImageRepository = postImageRepository;
        this.fileUtil = fileUtil;
        this.maxFileSize = maxFileSize;
    }

    @Transactional
    public PostImageVO save(MultipartFile mf, PostVO post) throws NotSupportedException, IOException {

        String extension =  "." + Objects.requireNonNull(mf.getContentType()).split("/")[1];

        if(!(extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".png") || extension.equals(".bmp")))
            throw new NotSupportedException("not supported extension : " + extension);

        if (mf.getSize() > maxFileSize) //10메가 용량제한
            throw new NotSupportedException("file size exceed");

        UUID saveName = UUID.randomUUID();
        fileUtil.saveFile(mf, saveName + extension, UPLOADPATH);

        PostImageVO vo = PostImageVO.builder()
                .name(saveName.toString())
                .fileInfo(FileInfo.builder()
                        .originalName(mf.getOriginalFilename())
                        .saveName(saveName + extension)
                        .size(mf.getSize())
                        .uploadPath(UPLOADPATH)
                        .extension(extension)
                        .build())
                .build();

        vo.setPost(post);

        return postImageRepository.save(vo);
    }

    @Transactional(readOnly = true)
    public byte[] getByteByName(String name) throws NotFoundException, IOException {

        PostImageVO img = this.getByName(name);

        return fileUtil.getFile(img.getFileInfo());

    }

    @Transactional(readOnly = true)
    public PostImageVO getByName(String name) throws NotFoundException {

        Optional<PostImageVO> optional = postImageRepository.findByName(name);

        if(optional.isEmpty())
            throw new NotFoundException("invalid image name");

        return optional.get();
    }



}
