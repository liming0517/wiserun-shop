package com.wiserun.goods.dao;


import com.wiserun.goods.pojo.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface BrandMapper {
    //查询所有品牌
    public List<Brand> selectAll();

    //根据id查询品牌
    public Brand selectByid(Integer id);

    //新增品牌
    public Integer insertBrand(Brand brand);

    //修改品牌
    public Integer updateBrand(@Param("brand") Brand brand);

    //删除品牌
    public Integer deleteBrand(@Param("id") Integer id);

    //按照条件查询
    public List<Brand> selectByAny(@Param("brand") Brand brand);

    /*品牌列表分页查询*/
    public List<Brand> selectPage(@Param("curPage") Integer curPage, @Param("num") Integer num);

    /*品牌列表条件+分页查询*/
    public List<Brand> selectPageByAny(@Param("brand") Brand brand,@Param("page")Integer page,@Param("num") Integer num);
}
