/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.console.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.androidpn.server.model.NotificationMO;
import org.androidpn.server.model.Page;
import org.androidpn.server.model.SessionVO;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.Session;
import org.androidpn.server.xmpp.session.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.xmpp.packet.Presence;

/** 
 * A controller class to process the session related requests.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class HistoryController extends MultiActionController {

    //private UserService userService;

    public HistoryController() {
        //userService = ServiceLocator.getUserService();
    }

    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	 String currentPage = request.getParameter("currentPage");
    	int pageSize = 5;
    	Page<NotificationMO> historyPageList = new Page<NotificationMO>(pageSize);//7为每页的显示条数
		if(currentPage == null || Integer.parseInt(currentPage) < 0){
			currentPage = "1";
		}  
		historyPageList.setPageNo(Integer.parseInt(currentPage));//1为当前页
		ServiceLocator.getNotificationService().queryNotification(historyPageList);
		historyPageList.setHtmlPage(historyPageList, "/Androidapn/history.do?");
    	ModelAndView mv = new ModelAndView();
    	mv.addObject("historyPageList", historyPageList);
    	
    	 List<NotificationMO> historyList = new ArrayList<NotificationMO>();
    	if (historyPageList.getResults() != null) {
			for (NotificationMO notificationMO : historyPageList.getResults()) {
				historyList.add(notificationMO);
			}
		}
    	mv.addObject("historyList", historyList);
    	mv.setViewName("history/list");
    	return mv;
    }

    
}
