package com.football.manager.admin;

import com.football.manager.admin.pages.LeagueListPage;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IRequestCycleSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

/**
 * User: pawel.radomski
 * Date: 25.01.13
 * Time: 16:55
 */
public class AdminApplication extends WebApplication
{
   @Override
   public Class<? extends Page> getHomePage()
   {
      return LeagueListPage.class;
   }

   @Override
   protected void init()
   {
      super.init();
      getComponentInstantiationListeners().add(new SpringComponentInjector(this));
      new AnnotatedMountScanner().scanPackage(AdminApplication.class.getPackage().getName()).mount(this);
      getDebugSettings().setAjaxDebugModeEnabled(false);
      getRequestCycleSettings().setRenderStrategy(IRequestCycleSettings.RenderStrategy.ONE_PASS_RENDER);
   }
}
