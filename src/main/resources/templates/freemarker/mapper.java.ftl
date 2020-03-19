package ${package.Mapper};


import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${package.Entity}.${entity};

@Mapper
public interface ${table.mapperName} extends BaseMapper<${entity}>{

}