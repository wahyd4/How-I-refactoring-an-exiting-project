package com.messpush.model.dao;

import java.util.List;

import com.messpush.model.bean.Authority;

/**
 * 定义权限列表的操作接口
 * @author Junv
 *
 */
public interface AuthorityDao {
	
	/**
	 * 获取所有顶级权限<br>
	 * 由于顶级权限的parent键为0 ，由此可以获取
	 * @return List<Authority>
	 */
	public List<Authority> getTopAuthoritys();
	
	/**
	 * 获取某个以及权限的所有子权限<br>
	 * 通过子权限的parent属性获取
	 * @param authorityId 顶级权限的ID
	 * @return  List<Authority> list对象
	 */
	public  List<Authority> getSonAuthoritysById(Integer authorityId);
	
	/**
	 * 通过权限ID获取某个权限<br>
	 * 同样也可以用于判断某个权限是否存在
	 * @param authorityId
	 * @return
	 */
    public Authority  getAuthorityById(Integer authorityId);
    
    /**
     * 通过权限名称获取某个权限
     * @param authorityName
     * @return
     */
    public Authority getAuthorityByName(String authorityName);
    
    /**
     * 获取系统所有的权限
     * @return List<Authority> 对象
     */
    public List<Authority> getAllAuthoritys();
}
