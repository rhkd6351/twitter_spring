package com.yemin.twitter.service.member;

import com.yemin.twitter.domain.*;
import com.yemin.twitter.dto.member.PageMemberDTO;
import com.yemin.twitter.repository.MemberImageRepository;
import com.yemin.twitter.repository.PostImageRepository;
import com.yemin.twitter.util.FileUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberImageService {
    private static final String UPLOADPATH = "/img";

    MemberImageRepository memberImageRepository;


    FileUtil fileUtil;
    int maxFileSize;

    public MemberImageService(MemberImageRepository memberImageRepository, FileUtil fileUtil, @Value("${static.max-file-size}") int maxFileSize) {
        this.memberImageRepository = memberImageRepository;
        this.fileUtil = fileUtil;
        this.maxFileSize = maxFileSize;
    }

    @Transactional
    public MemberImageVO save(MultipartFile mf, MemberVO member) throws NotSupportedException, IOException {

        String extension =  "." + Objects.requireNonNull(mf.getContentType()).split("/")[1];

        if(!(extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".png") || extension.equals(".bmp")))
            throw new NotSupportedException("not supported extension : " + extension);

        if (mf.getSize() > maxFileSize) //10메가 용량제한
            throw new NotSupportedException("file size exceed");

        UUID saveName = UUID.randomUUID();
        fileUtil.saveFile(mf, saveName + extension, UPLOADPATH);

        MemberImageVO vo = MemberImageVO.builder()
                .name(saveName.toString())
                .fileInfo(FileInfo.builder()
                        .originalName(mf.getOriginalFilename())
                        .saveName(saveName + extension)
                        .size(mf.getSize())
                        .uploadPath(UPLOADPATH)
                        .extension(extension)
                        .build())
                .build();

        vo.setMember(member);

        return memberImageRepository.save(vo);
    }

    @Transactional(readOnly = true)
    public byte[] getByteByName(String name) throws NotFoundException, IOException {

        MemberImageVO img = this.getByName(name);

        return fileUtil.getFile(img.getFileInfo());

    }

    @Transactional(readOnly = true)
    public MemberImageVO getByName(String name) throws NotFoundException {

        Optional<MemberImageVO> optional = memberImageRepository.findByName(name);

        if(optional.isEmpty())
        {throw new NotFoundException("invalid image name");
        }

        return optional.get();
    }


}
