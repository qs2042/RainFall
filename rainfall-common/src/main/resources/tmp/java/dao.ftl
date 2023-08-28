package ${packagePath}.dao;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

<#list importList as import>
//${import}
</#list>

import ${packagePath}.entity.${className}Entity;

/**
* ${comments}
*
* @author ${author}
* @email ${email}
* @date ${datetime}
*/
public interface ${className}Dao extends JpaRepository<${className}Entity, Integer>, Serializable {

}
