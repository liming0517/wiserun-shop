package com.wiserun.goods.controller;

import com.github.pagehelper.PageInfo;
import com.wiserun.entity.Page;
import com.wiserun.entity.Result;
import com.wiserun.entity.StatusCode;
import com.wiserun.goods.pojo.Brand;
import com.wiserun.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @RequestMapping(value="/list",method = RequestMethod.GET)
    public Result findAll() {
        List<Brand> brands = brandService.findAll();
        return new Result<List<Brand>>(true, StatusCode.OK, "查询成功",brands);
    }

    @RequestMapping(value = "/get/{id}")
    public Result<Brand> getById(@PathVariable("id") Integer id){
        Brand brand=brandService.findById(id);
        return new Result<Brand>(true,StatusCode.OK,"查询成功",brand);
    }

    @RequestMapping(value="/addBrand",method = RequestMethod.POST)
    public Result addBrand(@RequestBody Brand brand ){
        brandService.addBrand(brand);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    @RequestMapping(value ="/updateBrand",method = RequestMethod.PUT)
    public Result updateBrand(@RequestBody Brand brand){
        brandService.updateBrand(brand);
        return  new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(value = "/deleteBrand" ,method = RequestMethod.DELETE)
    public Result deleteBrand(@RequestParam("id") Integer id){
        brandService.deleteBrand(id);
        return  new Result(true,StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Result<List<Brand>> getByAny(@RequestBody(required = false) Brand brand){
        List<Brand> brands=brandService.getByAny(brand);
        return new Result<>(true,StatusCode.OK,"按照某个条件查询成功",brands);
    }

    @RequestMapping(value = "/search/{page}/{num}",method = RequestMethod.GET)
    public Result<PageInfo<Brand>> getPage(@PathVariable("page")  Integer page,@PathVariable("num") Integer num){
        Integer curPage=(page-1)*num;
        PageInfo<Brand> pageInfo=brandService.getPage(curPage,num);
        return new Result<>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    @RequestMapping(value="/serach/{page}/{num}/list",method = RequestMethod.GET)
    public Result<PageInfo<Brand>> getPageByAny(@RequestBody Brand brand, @PathVariable("page") Integer page, @PathVariable("num") Integer num){
        System.out.println("接收到的brand数值为："+brand);
        PageInfo<Brand> brandPageInfo=brandService.getPageByAny(brand,page,num);
        return new Result<>(true,StatusCode.OK,"查询成功",brandPageInfo);
    }
}
