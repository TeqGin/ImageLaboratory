package com.teqgin.image_laboratory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teqgin.image_laboratory.domain.Img;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ImgMapper extends BaseMapper<Img> {
    List<Img> getSharedImages();

    int makePublic(String id);

    List<Img> searchPublic(String keyword);

    List<Img> getAllPublicDesc();

    List<Img> getAllPublicAsc();

    int isPublic(String id);

    int insertPublic(Img img);

    Img selectPublicById(String imageId);

    void deletePublicImageById(String id);

    int buildRelation(String id,String imgId, String publicImageId);

    int changePublic(String publicImageId);

    int deleteRelation(String publicImageId);

    void changePublicImageName(String name, String id, String path);
}
