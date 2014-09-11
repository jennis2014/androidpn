/**
 * 
 */
package org.androidpn.server.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.androidpn.server.model.NotificationMO;
import org.androidpn.server.model.Page;
import org.androidpn.server.service.NotificationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * ֪ͨ����.
 * @author ʷ����
 */
public class NotificationServiceImpl implements NotificationService {
	protected final Log log = LogFactory.getLog(getClass());

	@Resource
	private HibernateTemplate hibernateTemplate; 
	 
	@Transactional
	public void deleteNotification(Long id) {
		log.info(" delete notification:id =" + id);
		hibernateTemplate.delete(queryNotificationById(id));
	}


	public NotificationMO queryNotificationById(Long id) {
		log.info(" query notification:id =" + id);
		NotificationMO notificationMO = (NotificationMO) hibernateTemplate
				.get(NotificationMO.class, id);
		return notificationMO;
	}

	@Transactional
	public void saveNotification(NotificationMO notificationMO) {
		log.info(" create a new notification:username = " + notificationMO.getUsername());
		hibernateTemplate.save(notificationMO);
	}

	@Transactional
	public void updateNotification(NotificationMO notificationMO) {
		log.info(" update notification : stauts = " +notificationMO.getStatus());
		hibernateTemplate.update(notificationMO);
	}
	@Transactional
	public void createNotifications(List<NotificationMO> notificationMOs) {
		for (NotificationMO notificationMO : notificationMOs) {
			saveNotification(notificationMO);
		}
	}
	
	@SuppressWarnings("unchecked")
	public NotificationMO queryNotificationByUserName(String userName,String messageId){
		Object[] params = new Object[] {userName, messageId};
		List<NotificationMO> list = hibernateTemplate
				.find(
						"from NotificationMO n where n.username=? and n.messageId=? order by n.createTime desc",
						params); 
		return list.isEmpty() ? null : list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<NotificationMO> queryNotification(NotificationMO mo) { 
		List<NotificationMO> list = hibernateTemplate.findByExample(mo);
		if(list.isEmpty()){
			log.info(" query notifications : list is null");
		}else{
			log.info(" query notifications : size = " + list.size());
		}
		return list;
	}
 
	@SuppressWarnings("unchecked")
	public List<NotificationMO> queryNotification(String username) { 
		List<NotificationMO> list =hibernateTemplate.find("from NotificationMO n where n.username=? and " +
				"(n.status=0 or n.status=1) order by n.createTime desc",new Object[] {username});
		if(list.isEmpty()){
			log.info(" ��ѯ����֪ͨ : list is null");
		}else{
			log.info(" ����֪ͨ : size = " + list.size());
		}
		return list;
	} 
	
	public Page<NotificationMO> queryNotification( final Page<NotificationMO> page) {
        //
        
            long totalCount = fetchPageCount();
            page.setTotalCount(totalCount);
            
            if(totalCount <= page.getStartIndex()){
            	page.setPageNo((int)((totalCount+page.getPageSize()-1)/page.getPageSize()));
            }
        //
        
        List<NotificationMO> results =hibernateTemplate.executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery("from NotificationMO n order by n.createTime desc");  
                //设置每页显示多少个，设置多大结果。  
                query.setMaxResults(page.getPageSize());  
                //设置起点  
                query.setFirstResult(page.getStartIndex());  
                return query.list();  
			}
			
		});
        int sizi=results.size();
        System.out.println(sizi);
        //
        return page.setResults(results);
    }
    
    private long fetchPageCount() {
    	List<NotificationMO> list =hibernateTemplate.find("from NotificationMO n order by n.createTime desc",null);
		
		return list.size();
    	
    }
	
}
