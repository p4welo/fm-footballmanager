package com.football.manager.admin.pages;

import com.football.manager.admin.api.AdminApiMappings;
import com.football.manager.admin.pages.cmp.tabbedPanel.LeagueDetailsTabPanel;
import com.football.manager.admin.pages.cmp.tabbedPanel.SeasonDetailsTabPanel;
import com.football.manager.domain.League;
import com.football.manager.service.ILeagueService;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.ArrayList;
import java.util.List;

/**
 * User: pawel
 * Date: 27.01.13
 * Time: 00:05
 */
@MountPath(AdminApiMappings.LEAGUE_DETAILS_PAGE)
public class LeagueDetailsPage extends AbstractPage
{
   @SpringBean
   private ILeagueService leagueService;

   private League selectedLeague;

   private final WebMarkupContainer mainContainer;

   public LeagueDetailsPage(final PageParameters parameters)
   {
      super();
      mainContainer = new WebMarkupContainer("mainContainer");
      mainContainer.setOutputMarkupId(true);

      handleIncomingParameters(parameters);
      initView();
   }

   private void handleIncomingParameters(PageParameters parameters)
   {
      String idString = parameters.get(SELECTED_LEAGUE_ID_KEY).toString();
      if (StringUtils.isNotBlank(idString))
      {
         Long id = Long.valueOf(idString);
         selectedLeague = leagueService.getById(id);
         if (selectedLeague == null)
         {
            throw new RestartResponseAtInterceptPageException(LeagueListPage.class);
         }
      }
      else
      {
         throw new RestartResponseAtInterceptPageException(LeagueListPage.class);
      }
   }

   private void initView()
   {
      createTabbedPanel(mainContainer);
      add(mainContainer);
   }

   private void createTabbedPanel(WebMarkupContainer container)
   {
      List<ITab> tabs = new ArrayList<ITab>();
      tabs.add(new AbstractTab(new ResourceModel("season.details.tab.header"))
      {
         @Override
         public Panel getPanel(String panelId)
         {
            return new SeasonDetailsTabPanel(panelId);
         }
      });

      tabs.add(new AbstractTab(new ResourceModel("league.details.tab.header"))
      {
         @Override
         public Panel getPanel(String panelId)
         {
            return new LeagueDetailsTabPanel(panelId, LeagueDetailsPage.this);
         }
      });
      container.add(new AjaxTabbedPanel("tabs", tabs));
   }

   public League getSelectedLeague()
   {
      return selectedLeague;
   }

   public void setSelectedLeague(League selectedLeague)
   {
      this.selectedLeague = selectedLeague;
   }
}
