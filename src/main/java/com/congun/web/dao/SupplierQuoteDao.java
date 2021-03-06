package com.congun.web.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.congun.web.model.AddEquipment;
import com.congun.web.model.ContractorRequirement;
import com.congun.web.model.MappingObject;
import com.congun.web.model.SupplierQuote;
import com.congun.web.model.User;
import com.congun.web.util.ApplicationUtil;
import com.congun.web.util.ResponseConstants;

@Repository
public class SupplierQuoteDao {

private static Logger logger = Logger.getLogger(SupplierQuoteDao.class);
@Autowired
private SessionFactory sessionFactory;

@Autowired
public ContractorRequirementQuoteDAO requirementDAO;

@Autowired
public MappingObject mappingobject;

@Transactional
protected Session getSession(){
	return sessionFactory.getCurrentSession();
}

@Transactional
public String saveQuote(SupplierQuote supplierQuote){
	logger.info("Entered into SupplierQuoteDao.saveQuote method ");
	try{
		
		Date date = new Date();
		Timestamp currTime = new Timestamp(date.getTime());
		supplierQuote.setCreatedTime(currTime);
		supplierQuote.setUpdatedTime(currTime);
		supplierQuote.setActiveFlag(1);
		requirementDAO.updateNoOfQuotes(supplierQuote.getRequirementId());
		getSession().saveOrUpdate(supplierQuote);
		
		
		return ResponseConstants.SUPPLIER_SUCCESS_CODE;
	}catch(Exception e){
		e.printStackTrace();
			return ResponseConstants.SUPPLIER_EXCEPTION_CODE;
	}
}

@Transactional
public String updateQuote(SupplierQuote supplierQuote)
{
	logger.info("Entered into SupplierQuoteDao.updateQuote method ");
	try{
		//SupplierQuote existingQuote = (SupplierQuote) getSession().createCriteria(SupplierQuote.class).add(Restrictions.eq("quoteId", supplierQuote.getQuoteId())).list().get(0);
		Date date = new Date();
		Timestamp currTime = new Timestamp(date.getTime());
		//supplierQuote.setCreatedTime(existingQuote.getCreatedTime());
		supplierQuote.setUpdatedTime(currTime);
		getSession().saveOrUpdate(supplierQuote);
		
		return ResponseConstants.SUPPLIER_SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return ResponseConstants.SUPPLIER_EXCEPTION_CODE;
	}	
}

@Transactional
@SuppressWarnings("unchecked")
public List<SupplierQuote> getQuotesbySupplier(long supplierId)
{
	logger.info("Entered into SupplierQuoteDao.getQuotesbySupplier method supplierId:"+supplierId);
	try{
		List<SupplierQuote>  supplierQuotationList = (ArrayList<SupplierQuote>) getSession().createCriteria(SupplierQuote.class).add(Restrictions.eq("quotePostedById", supplierId)).list();
		return supplierQuotationList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
	}
}

@Transactional
@SuppressWarnings("unchecked")
public List<SupplierQuote> getQuotesbyRequirement(long requirementId)
{
	logger.info("Entered into SupplierQuoteDao.getQuotesbyRequirement method requirementId:"+requirementId);
	try{
		List<SupplierQuote>  supplierQuotationList = (ArrayList<SupplierQuote>) getSession().createCriteria(SupplierQuote.class).add(Restrictions.eq("requirementId", requirementId)).list();		
		return supplierQuotationList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
	}
}

@Transactional
@SuppressWarnings("unchecked")
public int getNoOfQuotesbyRequirement(long requirementId)
{
	logger.info("Entered into SupplierQuoteDao.getNoOfQuotesbyRequirement method requirementId:"+requirementId);
	try{
		List<SupplierQuote>  supplierQuotationList = (ArrayList<SupplierQuote>) getSession().createCriteria(SupplierQuote.class).add(Restrictions.eq("requirementId", requirementId)).list();		
		return supplierQuotationList.size();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
	}
}

@Transactional
public SupplierQuote getQuotesbyId(long Id)
{
	logger.info("Entered into SupplierQuoteDao.getNoOfQuotesbyRequirement method Id:"+Id);
	try{
		SupplierQuote supplierQuotation = (SupplierQuote)getSession().createCriteria(SupplierQuote.class).add(Restrictions.eq("quoteId",Id)).list().get(0);
		
		if(supplierQuotation != null){
			logger.info("Got a record");
		}else
			logger.info("Supplier quote is null");
		return supplierQuotation;
		}catch(Exception e){
			e.printStackTrace();
			return null;
	}
}

@Transactional
	public String addEquipment(AddEquipment equipment) {
	logger.info("Entered into SupplierQuoteDao.addEquipment method");
		try {
			getSession().saveOrUpdate(equipment);
			return ResponseConstants.SUPPLIER_SUCCESS_CODE;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseConstants.SUPPLIER_EXCEPTION_CODE;
		}
		
	}


@Transactional
public String updateEquipment(AddEquipment equipment)
{
	logger.info("Entered into SupplierQuoteDao.updateEquipment method");
	try{ 
		logger.info("Entered DAO to update Equipment :"+equipment.getEquipmentId());
		getSession().saveOrUpdate(equipment);
		
		return ResponseConstants.EQUIPMENT_SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return ResponseConstants.EQUIPMENT_EXCEPTION_CODE;
	}	
}


@Transactional
	public String getEquipmentDetails(long supplierId) {
	logger.info("Entered into SupplierQuoteDao.getEquipmentDetails method  supplierId:"+supplierId);
		logger.info("Getting Equipment details from DB :" + supplierId);
		try{
			List<AddEquipment> addEquipment = getSession().createCriteria(AddEquipment.class).add(Restrictions.eq("supplierId",supplierId)).list();
			return ApplicationUtil.getJsonResponse(addEquipment);
			}catch(Exception e){
				e.printStackTrace();
				return null;
		}
	}

@Transactional
     public String getEquipmentById(int equipmentId){
	logger.info("Entered into SupplierQuoteDao.getEquipmentById method  equipmentId:"+equipmentId);
	logger.info("Getting Equipment Details from DB :"+equipmentId);
	try{
		
		AddEquipment equipment = (AddEquipment) getSession().createCriteria(AddEquipment.class).add(Restrictions.eq("equipmentId", equipmentId)).list().get(0);
		
		if(equipment != null){
			return ApplicationUtil.getJsonResponse(equipment);
		} else
			return null;
	}catch(Exception e){
		e.printStackTrace();
		return null;
	}
}

@Transactional
	public List<AddEquipment> getAllEquipments(Long supplierId) {
	logger.info("Entered into SupplierQuoteDao.getAllEquipments method  supplierId:"+supplierId);
		try{
			Criteria criteria = getSession().createCriteria(AddEquipment.class);
			criteria.add(Restrictions.eq("supplierId", supplierId));
			List<AddEquipment> reqList = criteria.list();
			return reqList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
	}
		
	}

@Transactional
	public String deleteEquipmentById(int equipmentId) {
	logger.info("Entered into SupplierQuoteDao.deleteEquipmentById method  equipmentId:"+equipmentId);
	try{
	Criteria criteria = getSession().createCriteria(AddEquipment.class);
	criteria.add(Restrictions.eq("equipmentId", equipmentId));
	AddEquipment equipment = (AddEquipment) criteria.uniqueResult();
	getSession().delete(equipment);
	return ResponseConstants.SUPPLIER_SUCCESS_CODE;
	}catch(Exception e){
		e.printStackTrace();
		return ResponseConstants.SUPPLIER_EXCEPTION_CODE;
	}
	
		
	}

@Transactional
	public List<AddEquipment> getEquipmentByCategory(ContractorRequirement requirement){
	logger.info("Entered into SupplierQuoteDao.getEquipmentByCategory method");
		try{
			Criteria criteria = getSession().createCriteria(AddEquipment.class);
			
			List<AddEquipment> machinesList = (ArrayList<AddEquipment>) criteria
					.add(Restrictions.eq("equipmentCategory",
							requirement.getEquipmentCategory()))
					.add(Restrictions.eq("equipment",
							requirement.getEquipmentName())).list();
			
			return machinesList;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

@Transactional
public List filterSupplierIds(Set mappedSuppliers,long requirementId){
	logger.info("Entere the filter Mappers method");
	try{
		String SupplierList=null;
		Criteria criteria = getSession().createCriteria(MappingObject.class);
		//List existingMappedIds = (ArrayList<Long>)criteria.setProjection(Projections.distinct(Projections.property("supplierId"))).list();
		List existingMappedIds= criteria.add(Restrictions.eq("requirementId", requirementId)).list();
		if(existingMappedIds.size() > 0){
			return existingMappedIds;
		}
		return existingMappedIds;
		//return existingMappedIds;
		
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
}

@Transactional
public void updateMappingObjects(Set<Long> mapperSuppliers,long requirementId){
	logger.info("Entered into SupplierQuoteDao.updateMappingObjects method");
	String suppId="";
	try{
	if(mapperSuppliers.size() > 0){
		Criteria criteria = getSession().createCriteria(MappingObject.class);
	for(Long Id:mapperSuppliers){
		//suppId = suppId+Id.longValue();
		//suppId = suppId+",";
		MappingObject mappedobject = new MappingObject();
		mappedobject.setRequirementId(requirementId);
		mappedobject.setSupplierId(Id);
	    getSession().save(mappedobject);
	}
	
	//MappingObject mappingobject = new MappingObject();
	//List existingObjList = criteria.add(Restrictions.eq("requirementId", requirementId)).list();
	/*MappingObject existingObj=null;
	if(existingObjList.size() > 0){
	existingObj = (MappingObject)criteria.add(Restrictions.eq("requirementId", requirementId)).list().get(0);
	existingObj.setSupplierList(existingObj.getSupplierList()+suppId);
	getSession().saveOrUpdate(existingObj);
	}else{
	    mappingobject.setRequirementId(requirementId);
	    mappingobject.setSupplierId(suppId);
		getSession().saveOrUpdate(mappingobject);	
	}
	*/}else
		logger.info("Received Empty Mapped Suppliers for Requirement :"+requirementId);
	}catch(Exception e){
		e.printStackTrace();
		logger.info("�xception Occured while updating the Mapped Objects for Requirement Id: "+requirementId );
	}
}

}
