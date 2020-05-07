package com.wiserun.goods.service;

import com.github.pagehelper.PageInfo;
import com.wiserun.goods.pojo.Brand;

import java.util.List;

public interface BrandService {
    /*查询所有品跑*/
    public List<Brand> findAll();

    /*根据id查询品牌*/
    public Brand findById(Integer id);

    /*新增品牌*/
    public Integer addBrand(Brand brand);

    /*修改品牌*/
    public Integer updateBrand(Brand brand);

    /*删除品牌*/
    public Integer deleteBrand(Integer id);

    /*按照条件查询*/
    public List<Brand> getByAny(Brand brand);

    /*品牌列表分页查询*/
    public PageInfo<Brand> getPage(Integer page,Integer num);

    /*品牌列表条件+分页查询*/
    public  PageInfo<Brand> getPageByAny(Brand brand,Integer page,Integer num);
}
