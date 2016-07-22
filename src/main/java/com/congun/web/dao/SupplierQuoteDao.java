package com.congun.web.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.congun.web.model.AddEquipment;
import com.congun.web.model.ContractorRequirement;
import com.congun.web.model.SupplierQuote;
import com.congun.web.util.ApplicationUtil;
import com.congun.web.util.ResponseConstants;

@Repository
public class SupplierQuoteDao {

	
@Autowired
private SessionFactory sessionFactory;
public ContractorRequirementQuoteDAO requirementDAO;

@Transactional
protected Session getSession(){
	return sessionFactory.getCurrentSession();
}

@Transactional
public String saveQuote(SupplierQuote supplierQuote){
	try{
		
		Date date = new Date();
		Timestamp currTime = new Timestamp(date.getTime());
		supplierQuote.setCreatedTime(currTime);
		supplierQuote.setUpdatedTime(currTime);
		supplierQuote.setActiveFlag(1);
		requirementDAO.updateNoOfQuotes(supplierQuote.getRequirementId());
		getSession().saveOrUpdate(supplierQuote);
		
		
		return ResponseConstants.SUCCESS_CODE;
	}catch(Exception e){
		e.printStackTrace();
			return ResponseConstants.EXCEPTION_CODE;
	}
}

@Transactional
public String updateQuote(SupplierQuote supplierQuote)
{
	try{
		Date date = new Date();
		Timestamp currTime = new Timestamp(date.getTime());
		supplierQuote.setUpdatedTime(currTime);
		getSession().saveOrUpdate(supplierQuote);
		
		return ResponseConstants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return ResponseConstants.EXCEPTION_CODE;
	}	
}

@Transactional
@SuppressWarnings("unchecked")
public List<SupplierQuote> getQuotesbySupplier(long supplierId)
{
	System.out.println("Getting Quotes by Supplier Id from DB : "+supplierId);
	try{
		List<SupplierQuote>  supplierQuotationList = (ArrayList<SupplierQuote>) getSession().createCriteria(SupplierQuote.class).add(Restrictions.eq("quotePostedById", supplierId)).list();
		System.out.println("No of Quotes Returned : "+supplierQuotationList.size());
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
	System.out.println("Getting Quotation from DB :" + Id);
	try{
		SupplierQuote supplierQuotation = (SupplierQuote)getSession().createCriteria(SupplierQuote.class).add(Restrictions.eq("quoteId",Id)).list().get(0);
		
		if(supplierQuotation != null){
			System.out.println("Got a record");
		}else
			System.out.println("Supplier quote is null");
		return supplierQuotation;
		}catch(Exception e){
			e.printStackTrace();
			return null;
	}
}

	public String addEquipment(AddEquipment equipment) {
		try {
			getSession().saveOrUpdate(equipment);
			return ResponseConstants.SUCCESS_CODE;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseConstants.EXCEPTION_CODE;
		}
		
	}

	public String getEquipmentDetails(long supplierId) {
		System.out.println("Getting Equipment details from DB :" + supplierId);
		try{
			List<AddEquipment> addEquipment = getSession().createCriteria(AddEquipment.class).add(Restrictions.eq("supplierId",supplierId)).list();
			
			if(addEquipment != null){
				System.out.println("Got a record");
			}else
				System.out.println("Supplier quote is null");
			return ApplicationUtil.getJsonResponse(addEquipment);
			}catch(Exception e){
				e.printStackTrace();
				return null;
		}
	}

	public List<AddEquipment> getAllEquipments(Long supplierId) {
		System.out.println("Getting Equipment details from DB :" + supplierId);
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

	public String deleteEquipmentById(int equipmentId) {
	System.out.println("Deleting Equipment by ID");
	try{
	Criteria criteria = getSession().createCriteria(AddEquipment.class);
	criteria.add(Restrictions.eq("equipmentId", equipmentId));
	AddEquipment equipment = (AddEquipment) criteria.uniqueResult();
	getSession().delete(equipment);
	return ResponseConstants.SUCCESS_CODE;
	}catch(Exception e){
		e.printStackTrace();
		return ResponseConstants.EXCEPTION_CODE;
	}
	
		
	}

}