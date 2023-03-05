package com.alexlander.reggie.dto;

import com.alexlander.reggie.entity.Setmeal;
import com.alexlander.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
