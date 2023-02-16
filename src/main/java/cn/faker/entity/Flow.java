package cn.faker.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("fk_flow")
public class Flow {
    /**
     * 流水ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 金额（单位：分）
     */
    private Long amount;
    /**
     * 交易时间
     */
    private Date tradeTime;

    /**
     * 交易结果
     */
    private boolean succeed;

    /**
     * 凭证
     */
    private String ticket;
}
