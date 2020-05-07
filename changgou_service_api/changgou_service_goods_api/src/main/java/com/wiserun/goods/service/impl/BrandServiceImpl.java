package com.wiserun.goods.service.impl;

import com.github.pagehelper.PageInfo;
import com.wiserun.goods.dao.BrandMapper;
import com.wiserun.goods.pojo.Brand;
import com.wiserun.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        List<Brand> brands = brandMapper.selectAll();
        return brands;
    }

    @Override
    public Brand findById(Integer id) {
        Brand brand = brandMapper.selectByid(id);
        return brand;
    }

    @Override
    public Integer addBrand(Brand brand) {
        Integer num = brandMapper.insertBrand(brand);
        return num;
    }

    @Override
    public Integer updateBrand(Brand brand) {
        Integer num=brandMapper.updateBrand(brand);
        return num;
    }

    @Override
    public Integer deleteBrand(Integer id) {
        Integer num=brandMapper.deleteBrand(id);
        return num;
    }

    @Override
    public List<Brand> getByAny(Brand brand) {
        List<Brand> brands=brandMapper.selectByAny(brand);
        return brands;
    }

    @Override
    public PageInfo<Brand> getPage(Integer page, Integer num) {
        List<Brand> brands=brandMapper.selectPage(page,num);
        return new PageInfo<Brand>(brands);
    }

    @Override
    public PageInfo<Brand> getPageByAny(Brand brand, Integer page, Integer num) {
        Integer curpage=(page-1)*num;
        List<Brand> brands=brandMapper.selectPageByAny(brand,curpage,num);
        return new PageInfo<>(brands);
    }
}
