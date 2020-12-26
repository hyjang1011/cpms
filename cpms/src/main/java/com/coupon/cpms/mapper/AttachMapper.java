/**
 * 
 */
package com.coupon.cpms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coupon.cpms.domain.AttachDTO;

/**
 * @author admin-PC
 *
 */

@Mapper
public interface AttachMapper {

	public int insertAttach(List<AttachDTO> attachList);

	public AttachDTO selectAttachDetail(Long attachUno);

	public int deleteAttach(Long bbsUno);

	public List<AttachDTO> selectAttachList(Long bbsUno);

	public int selectAttachTotalCount(Long bbsUno);
	
	public int undeleteAttach(List<Long> attachUnos);
}
