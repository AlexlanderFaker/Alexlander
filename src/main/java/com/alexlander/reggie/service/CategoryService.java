package com.alexlander.reggie.service;

import com.alexlander.reggie.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
