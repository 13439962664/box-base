package ${package.Entity};

import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.box.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
* <p>
* ${table.comment!} 实体类
* </p>
*
* @author ${author}
* @since ${date}
*/
@Data
@Accessors(chain = true)
@TableName("${table.name}")
public class ${table.entityName} extends ${superEntityClass} implements java.io.Serializable{

	private static final long serialVersionUID = 1L;


    <#list table.fields as field>
    /**
     * ${field.comment!}
     */
        <#if field.keyFlag><#--生成主键排在第一位-->
    @TableId
        </#if>
        <#if !field.keyFlag><#--生成普通字段 -->
    @TableField("${field.name}")
        </#if>
    private ${field.propertyType} ${field.propertyName};
    </#list>

}