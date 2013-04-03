package com.fm.admin.pages;

import com.fm.admin.api.AdminApiMappings;
import com.fm.admin.cmp.menu.AdminMenuPanel;
import com.fm.core.cmp.authorization.LogoutLink;
import com.fm.core.cmp.breadcrumb.BreadCrumb;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Barbara
 * Date: 28.03.13
 * Time: 18:08
 * To change this template use File | Settings | File Templates.
 */
public abstract class AdminAbstractPage extends WebPage implements AdminApiMappings, Serializable
{
   protected WebMarkupContainer webpage;

   public AdminAbstractPage()
   {
      add(new AdminMenuPanel("menu"));
      add(new LogoutLink("logout", LeagueListPage.class));
      add(new Label("header", new ResourceModel(provideHeaderKey())));
      webpage = new WebMarkupContainer("main");
      webpage.setOutputMarkupId(true);
      add(webpage);
      add(provideBreadcrumb("breadcrumb"));
   }

   public MarkupContainer addToPage(Component... childs)
   {
      return webpage.add(childs);
   }

   protected abstract String provideHeaderKey();

   protected abstract BreadCrumb provideBreadcrumb(String id);
}
