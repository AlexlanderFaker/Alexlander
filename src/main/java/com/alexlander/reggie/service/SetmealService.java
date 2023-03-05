package com.alexlander.reggie.service;

import com.alexlander.reggie.dto.SetmealDto;
import com.alexlander.reggie.entity.Setmeal;
import com.alexlander.reggie.entity.SetmealDish;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(Long[] ids);
    //public void updateWithDish(SetmealDto setmealDto);
}
